package usb;

import android.app.*;
import android.content.*;
import android.hardware.usb.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.felhr.usbserial.*;
import com.google.android.things.pio.*;

import java.io.*;
import java.util.*;

import constants.*;
import logger.*;

public class UsbConnectionFragment extends Fragment {
    private UartDevice mDevice;

    boolean isUSBConnected;
    private static final String ACTION_USB_PERMISSION  = "com.blecentral.USB_PERMISSION";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUSBActionBroadCast();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    UsbDeviceConnection connection;
    UsbManager usbManager;
    UsbDevice device;
    void getUartDeviceList() {

         usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        // Get the list of attached devices
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();


        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Logger.log(Level.DEBUG,"(Name And version )",
                            device.getDeviceName()+" "+device.getVersion());
                }
                int deviceVID = device.getVendorId();
                int devicePID = device.getProductId();

                Logger.log(Level.DEBUG,"(Device Version Id )",""+deviceVID);

                Logger.log(Level.DEBUG,"(Device Product  Id )",""+devicePID);


                if (deviceVID ==4292|| (devicePID == 60000 )) {
                    // We are supposing here there is only one device connected and it is our serial device
                    if (!usbManager.hasPermission(device)) {
                        PendingIntent mPermissionIntent = PendingIntent.
                                getBroadcast(getActivity(), 0, new Intent(ACTION_USB_PERMISSION), 0);
                        usbManager.requestPermission(device, mPermissionIntent);
                        return;
                    }


                } else {
                    connection = null;
                    device = null;
                }

                if (!keep)
                    break;
            }

        }


    }




    public void createUsbSerialDevice(UsbDevice device,UsbDeviceConnection  usbConnection) {

        UsbSerialDevice serial = UsbSerialDevice.createUsbSerialDevice(device, usbConnection);
        Toast.makeText(getActivity(), "Creating USB Serial Device", Toast.LENGTH_SHORT).show();
        // open the device

        byte[] writeBuffer = new byte[]{0x50, 0x00, 0x08, 0x13, 0x00, (byte) 0xFE, 0x68, 0x61};
        if (serial != null) {
            serial.open();
            serial.setBaudRate(256000);
            serial.setDataBits(UsbSerialInterface.DATA_BITS_8);
            serial.setParity(UsbSerialInterface.PARITY_NONE);
            serial.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);


            serial.read(mpCallback);

            serial.write(writeBuffer);


        }


    }
    UsbSerialInterface.UsbReadCallback mpCallback= bytes -> {


        byte[] buffer = new byte[bytes.length];
     //   byteArrayInputStream=new ByteArrayInputStream(buffer);
     //   mHandler.obtainMessage(Constants.RECEIVED_DATA,bytes).sendToTarget();
        Log.i("bytes length=",""+bytes.length);

    };


    public void createUSBActionBroadCast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_USB_ATTACHED);
        filter.addAction(Constants.ACTION_USB_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);

        try {
            getActivity().registerReceiver(usbAttachedReceiver,filter);
            Logger.log(Level.DEBUG,"--","Receiver registered");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    BroadcastReceiver usbAttachedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.ACTION_USB_ATTACHED)){
               isUSBConnected=true;
               Logger.log(Level.DEBUG,"---","USB is Attached !!!!!!!");
               if(isUSBConnected){
                   getUartDeviceList();
               }
            }else if(intent.getAction().equals(Constants.ACTION_USB_DETACHED)){
               isUSBConnected=false;
                Logger.log(Level.WARNING,"---","USB is DEAttached !!!!!!!");

            }else if(intent.getAction().equals(ACTION_USB_PERMISSION))
            {
                connection = usbManager.openDevice(device);
                createUsbSerialDevice(device,connection);
            }
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(usbAttachedReceiver!=null){
           getActivity(). unregisterReceiver(usbAttachedReceiver);
        }

    }

}
