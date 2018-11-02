package real.ecg.frag;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import java.io.*;
import java.lang.ref.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import custom.ecg.lead.realdata.*;
import data.*;
import logger.*;
import login.fragment.*;
import usb.*;

public class SixByTwoFragment  extends Fragment {

    Lead1GraphView ecggraph1;
    Lead2GraphView ecggraph2;
    Lead3GraphView ecggraph3;
    LeadaVRGraphView ecggraphavR;
    LeadaVLGraphView ecggraphavL;
    LeadaVFGraphView ecggraphaVF;

    LeadV1GraphView ecggraphV1;
    LeadV2GraphView ecggraphV2;
    LeadV3GraphView ecggraphV3;
    LeadV4GraphView ecggraphV4;
    LeadV5GraphView ecggraphV5;
    LeadV6GraphView ecggraphV6;

    boolean mFlag;
    RealTimeThreads realTimeThreads;
    MyHandler sixBy2Handler;
    int index=0;
    LoginActivityListner loginActivityListner;

    UsbService usbService;
    String hexstring="0x50 0x00 0x08 0x13 0x00 0xFE 0x68 0x61";


    Button start;


    ImageView battery;

    private  TextView Time;
    LinearLayout errorBar;
    private void setFilters() {


        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        getActivity().registerReceiver(mUsbReceiver, filter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.real_ecg_ui, container,
                false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ecggraph1=view.findViewById(R.id.RealTimeEcg1);
        ecggraph2=view.findViewById(R.id.RealTimeEcg2);

        ecggraph3=view.findViewById(R.id.RealTimeEcg3);
        ecggraphavR=view.findViewById(R.id.RealTimeEcgaVR);

        ecggraphavL=view.findViewById(R.id.RealTimeEcgaVL);

        ecggraphaVF=view.findViewById(R.id.RealTimeEcgaVF);

        ecggraphV1=view.findViewById(R.id.RealTimeEcgV1);

        ecggraphV2=view.findViewById(R.id.RealTimeEcgV2);

        ecggraphV3=view.findViewById(R.id.RealTimeEcgV3);

        ecggraphV4=view.findViewById(R.id.RealTimeEcgV4);

        ecggraphV5=view.findViewById(R.id.RealTimeEcgV5);

        ecggraphV6=view.findViewById(R.id.RealTimeEcgV6);

        start=view.findViewById(R.id.startTest);

        errorBar=view.findViewById(R.id.errorBar);

        Time=view.findViewById(R.id.Time);

        battery=view.findViewById(R.id.battery);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIX_BY_TWO_FARGMENT);
        loginActivityListner.onDataPass(ClassConstants.SIX_BY_TWO_FARGMENT);

        mFlag=true;
        sixBy2Handler=new MyHandler(this);



        Time.setText((DateTime.getDateTimeAMPM()));

        start.setOnClickListener(view -> {

            byte [] writeBuffer= HexData.stringTobytes(hexstring);

            if (usbService != null) {

                usbService.write(writeBuffer);
                startTimer();
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setFilters();
       startService(UsbService.class, usbConnection, null);
     sixBy2Handler.postDelayed(() -> startThread(), 3000);

    }



    public void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {

        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(getActivity(), service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            getActivity().startService(startService);
        }

        Intent bindingIntent = new Intent(getActivity(), service);
        getActivity().bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }




    private void startThread() {

        if(realTimeThreads==null){
            realTimeThreads=new RealTimeThreads();
            realTimeThreads.start();
        }
    }


    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();

    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable(){
                    public void run(){
                        //TODO
                        byte [] writeBuffer= HexData.stringTobytes(hexstring);
                        if (usbService != null) { // if UsbService was correctly binded, Send data
                            usbService.write(writeBuffer);
                            Log.d("Hello", "World MYYY");
                        }
                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 3000, 3000);
    }

    class RealTimeThreads extends Thread{

        @Override
        public void run() {
            super.run();


            while (mFlag){


                try {
                    sleep(1000); // 15 ms stable / delete rec t at 1800 ms
                    sixBy2Handler.sendEmptyMessage(2000);

                    } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mFlag=false;
        stopthreads();
        getActivity().unregisterReceiver(mUsbReceiver);
        getActivity().unbindService(usbConnection);

    }


    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(sixBy2Handler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
            stopTimer();
        }
    };



    private static class MyHandler extends Handler {

        /** The m activity. */
        private final WeakReference<SixByTwoFragment> mActivity;

        int mxcounter=0;
        /**
         * Constructor for MyHandler.
         *
         * @param mActivity
         *            AppBaseActivity
         */
        StringBuilder sb;
        private MyHandler(SixByTwoFragment mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
            sb=new StringBuilder();
        }

        /**
         * Method handleMessage.
         *
         * @param msg
         *            android.os.Message
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final SixByTwoFragment parent = mActivity.get();

 if(msg.what==UsbService.MESSAGE_FROM_SERIAL_PORT){


                byte[] bytesArray= (byte[]) msg.obj;

                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(HexData.hexToString(bytesArray));


                Log.i("Response length Bytes",""+bytesArray.length);
                Log.i("Get Response",stringBuilder.toString());

                if(bytesArray.length==25) {
                    if (bytesArray[0] == 0x50 && bytesArray[24] == 0x61) {

                        Log.i("First & Last byte=", "OK For ECG");



                        int ecgsampke1 = mActivity.get().combineMsbLsb(bytesArray[6], bytesArray[7]);
                        int ecgsampke2 = mActivity.get().combineMsbLsb(bytesArray[8], bytesArray[9]);

                        int ecgsampke3 = mActivity.get().Lead3Data(ecgsampke1, ecgsampke2);

                        int avrData = mActivity.get().LeadaVRData(ecgsampke1-2048, ecgsampke2-2048);

                        int avLData = mActivity.get().LeadaVLData(ecgsampke1-2048, ecgsampke2-2048);

                        int aVFData = mActivity.get().LeadaVFData(ecgsampke1-2048, ecgsampke2-2048);

                        int ecgsampleV1 = mActivity.get().combineMsbLsb(bytesArray[10], bytesArray[11]);
                        int ecgsampleV2 = mActivity.get().combineMsbLsb(bytesArray[12], bytesArray[13]);

                        int ecgsampleV3 = mActivity.get().combineMsbLsb(bytesArray[14], bytesArray[15]);
                        int ecgsampleV4 = mActivity.get().combineMsbLsb(bytesArray[16], bytesArray[17]);

                        int ecgsampleV5 = mActivity.get().combineMsbLsb(bytesArray[18], bytesArray[19]);
                        int ecgsampleV6 = mActivity.get().combineMsbLsb(bytesArray[20], bytesArray[21]);

                      /*  Logger.log(Level.DEBUG,"---Lead I-----}}",""+ecgsampke1);
                        Logger.log(Level.DEBUG,"---Lead II-----}}",""+ecgsampke2);
                        Logger.log(Level.DEBUG,"---Lead III-----}}",""+ecgsampke3);


                        Logger.log(Level.DEBUG,"---avR-----}}",""+avrData);
                        Logger.log(Level.DEBUG,"---avL-----}}",""+avLData);
                        Logger.log(Level.DEBUG,"---avF-----}}",""+aVFData);*/

                 /*       Logger.log(Level.DEBUG,"---Lead I-----}}",""+ecgsampke1);
                        Logger.log(Level.DEBUG,"---Lead II-----}}",""+ecgsampke2);
                        Logger.log(Level.DEBUG,"---Lead III-----}}",""+ecgsampke3);


                        Logger.log(Level.DEBUG,"---avR-----}}",""+avrData);
                        Logger.log(Level.DEBUG,"---avL-----}}",""+avLData);
                        Logger.log(Level.DEBUG,"---avF-----}}",""+aVFData);


                        Logger.log(Level.DEBUG,"---V1-----}}",""+ecgsampleV1);
                        Logger.log(Level.DEBUG,"---V2-----}}",""+ecgsampleV2);
                        Logger.log(Level.DEBUG,"---V3-----}}",""+ecgsampleV3);

                        Logger.log(Level.DEBUG,"---V4-----}}",""+ecgsampleV4);
                        Logger.log(Level.DEBUG,"---V5-----}}",""+ecgsampleV5);
                        Logger.log(Level.DEBUG,"---V6-----}}",""+ecgsampleV6);
*/


                        mxcounter++;
                       if(mxcounter==10){
                        parent.ecggraph1.drawpoints(ecgsampke1-2048);
                        parent.ecggraph2.drawpoints(ecgsampke2-2048);
                          /* sb.append("\n"+ecgsampke1+"    "+ecgsampke2+"     "+ecgsampke3+ "     "+avrData

                           +"     "+avLData+"     "+aVFData+"     "+ecgsampleV1+"     "+ecgsampleV2+"     "+ecgsampleV3
                           +"     "+ecgsampleV4+"     "+ecgsampleV5+"     "+ecgsampleV6);
                           writeDatatoFile(sb.toString(),"RealTimeECg_60bpm.txt");
*/
                              parent.ecggraph3.drawpoints(ecgsampke3);
                              parent.ecggraphavR.drawpoints(avrData);
                               parent.ecggraphavL.drawpoints(avLData);
                               parent.ecggraphaVF.drawpoints(aVFData);


                           parent.ecggraphV1.drawpoints(ecgsampleV1-2048);
                           parent.ecggraphV2.drawpoints(ecgsampleV2-2048);

                           parent.ecggraphV3.drawpoints(ecgsampleV3-2048);
                           parent.ecggraphV4.drawpoints(ecgsampleV4-2048);
                           parent.ecggraphV5.drawpoints(ecgsampleV5-2048);
                           parent.ecggraphV6.drawpoints(ecgsampleV6-2048);

                          int battery =mActivity.get().combineMsbLsb(bytesArray[5],bytesArray[4]);
                            parent.batteryRange(battery/10,parent.getActivity());

                            mxcounter=0;
                       }


                    }

                }
            }

            else if(msg.what==2000){
                parent.Time.setText(DateTime.getDateTimeAMPM().toUpperCase());
            }

        }
    }





    private void stopthreads(){
        if(ecggraph1!=null){
            ecggraph1.stopRepeatingTask();

        }
        if(ecggraph2!=null){
            ecggraph2.stopRepeatingTask();

        }

        if(ecggraph3!=null){
            ecggraph3.stopRepeatingTask();

        }

        if(ecggraphavR!=null){
            ecggraphavR.stopRepeatingTask();

        }
        if(ecggraphavL!=null){
            ecggraphavL.stopRepeatingTask();

        }
        if(ecggraphaVF!=null){
            ecggraphaVF.stopRepeatingTask();

        }

        if(ecggraphV1!=null){
            ecggraphV1.stopRepeatingTask();

        }
        if(ecggraphV2!=null){
            ecggraphV2.stopRepeatingTask();

        }

        if(ecggraphV3!=null){
            ecggraphV3.stopRepeatingTask();

        }
        if(ecggraphV4!=null){
            ecggraphV4.stopRepeatingTask();

        }
        if(ecggraphV5!=null){
            ecggraphV5.stopRepeatingTask();

        }
        if(ecggraphV6!=null){
            ecggraphV6.stopRepeatingTask();

        }


    }




    private  int combineMsbLsb(int a, int b)// a= lsb
    {

        int msb =(b << 8);
        int lsb =(a & 0x00FF);

        int result =(msb | lsb);

        return result;
    }


    private   int Lead3Data(int lead1Data, int lead2Data){

        int lead3Data=lead2Data-lead1Data;

        return lead3Data;
    }


    private  int LeadaVRData(int lead1Data, int lead2Data )
    {
        int LeadaVRData=0;
        LeadaVRData = (-1)* ((lead1Data + lead2Data)/2);
        return LeadaVRData;



    }



    private  int LeadaVLData(int lead1Data, int lead2Data)
    {


        int  LeadaVLData =  lead1Data - (lead2Data/2);
        return LeadaVLData;



    }


    //aVR = - (Lead I + Lead II)/2
    //aVL = (Lead I - Lead III)/2 = Lead I - ((Lead II)/2)
    //aVF = (Lead II + Lead III)/2 = Lead II - ((Lead I)/2)

    private static int LeadaVFData(int lead1Data, int lead2Data)
    {
        int LeadaVFData=lead2Data - (lead1Data/2);
        return LeadaVFData;


    }





    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    stopTimer();
                    mFlag=false;
                    break;
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public static void writeDatatoFile(String sb,String filename)
    {
        String filenamedir="CheckRealTimeECGSerialData";

        File file =new File(Environment.getExternalStorageDirectory(),filenamedir);

        if(!file.exists())
        {
            file.mkdir();

        }



        File geenX3File=new File(file,filename);
        try {
            FileWriter filewriter=new FileWriter(geenX3File,false);
            filewriter.append(sb);
            filewriter.flush();
            Log.i("FilePath", "saving data into file");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    private void batteryRange(int range,Context context)
    {
        if(range>10 && range<=20)
        {
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_20_black));
        }else if(range >10 && range <=30)
        {
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_30_black_));

        }else if(range >40 && range <=50)
        {
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_60_black_));

        }else if(range>50 && range <=80){
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_80_black_));

        }else if(range >80 && range <=90)
        {
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_90_black_));

        }else {
            battery.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_battery_full_black_));

        }
    }
}
