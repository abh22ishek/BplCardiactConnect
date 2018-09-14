package ecg;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.internal.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.tom_roush.harmony.awt.*;
import com.tom_roush.pdfbox.pdmodel.*;
import com.tom_roush.pdfbox.pdmodel.common.*;
import com.tom_roush.pdfbox.pdmodel.font.*;
import com.tom_roush.pdfbox.pdmodel.graphics.image.*;
import com.tom_roush.pdfbox.util.*;

import java.io.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import custom.view.*;
import gennx.model.*;
import io.github.yavski.fabspeeddial.*;
import logger.*;
import login.fragment.*;

public class ECGDisplayFragment extends Fragment {

    EcgGraphView ecgGraphView;
    LoginActivityListner loginActivityListner;

    List<EcgLEadModel> ecgLEadModelList;
    ScrollView iv_scroll;
    FabSpeedDial fabSpeedDial;
    private ArrayList<Integer> LeadI,Lead4,Lead5,Lead6;
    private ArrayList<Integer> LeadX11,LeadII,LeadIII,LeadaVR,LeadV11,LeadVIII,LeadIX,LeadX,LeadXI;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.view_ecg,container,false);
        ecgGraphView=view.findViewById(R.id.ecgGraphView);
            iv_scroll=view.findViewById(R.id.iv_scroll);
            fabSpeedDial=view.findViewById(R.id.fabSpeedDial);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass(ClassConstants.ECG_DISPALY_FRAGMENT);

       ecgLEadModelList= (List<EcgLEadModel>) getArguments().getSerializable(Constants.CUSTOM_DATA);
        ecgGraphView.setList(ecgLEadModelList.get(0).getEcgLead1(),ecgLEadModelList.get(0).getEcgLead2(),
                ecgLEadModelList.get(0).getEcgLeadV1(),ecgLEadModelList.get(0).getEcgLeadV2(),ecgLEadModelList.get(0).getEcgLeadV3(),
                ecgLEadModelList.get(0).getEcgLeadV4(),ecgLEadModelList.get(0).getEcgLeadV5(),ecgLEadModelList.get(0).getEcgLeadV6());



        computeLead1ECG(ecgLEadModelList.get(0).getEcgLead1());

        //
        computeLead2ECG(ecgLEadModelList.get(0).getEcgLead2());
        computeLead7ECG(ecgLEadModelList.get(0).getEcgLeadV1());
       // computeLead7ECG(ecgLEadModelList.get(0).getEcgLeadV1());
        computeLead8ECG(ecgLEadModelList.get(0).getEcgLeadV2());
        computeLead9ECG(ecgLEadModelList.get(0).getEcgLeadV3());
        computeLead10ECG(ecgLEadModelList.get(0).getEcgLeadV4());
        computeLead11ECG(ecgLEadModelList.get(0).getEcgLeadV5());
        computeLead12ECG(ecgLEadModelList.get(0).getEcgLeadV6());




        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }
        });


        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                if(menuItem.getItemId()==R.id.savePng)
                {
                    captureScreen("Big boss ","small");
                  //  ecgGraphView.clearCanvas();

                }else if(menuItem.getItemId()==R.id.savePdf)
                {
                    EcgPdf task = new EcgPdf();
                    task.execute(new String[0]);
                }



                return true;
            }
        });



      /*  Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(2000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.log(Level.DEBUG,"---Thread---","Run Method()");
                                ecgGraphView.setList(ecgLEadModelList.get(0).getEcgLead1(),ecgLEadModelList.get(0).getEcgLead2(),
                                        ecgLEadModelList.get(0).getEcgLeadV1(),ecgLEadModelList.get(0).getEcgLeadV2(),ecgLEadModelList.get(0).getEcgLeadV3(),
                                        ecgLEadModelList.get(0).getEcgLeadV4(),ecgLEadModelList.get(0).getEcgLeadV5(),ecgLEadModelList.get(0).getEcgLeadV6());

                                ecgGraphView.invalidate();
                                ecgGraphView.clearCanvas();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();*/


    }





    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }




    private void captureScreen(String loginName,String userName) {

        //iv_scroll.getChildAt(0).measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bmp = Bitmap.createBitmap(iv_scroll.getChildAt(0).getMeasuredWidth(), iv_scroll.getChildAt(0).getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        //iv_scroll.getChildAt(0).layout(0, 0, iv_scroll.getChildAt(0).getMeasuredWidth(), iv_scroll.getChildAt(0).getMeasuredHeight());
        Drawable bgDrawable =iv_scroll.getChildAt(0).getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        iv_scroll.getChildAt(0).draw(canvas);

        String name=userName+"_"+System.currentTimeMillis()+"_ECG" + ".PNG";

        try {
            FileOutputStream fos = new FileOutputStream(saveScreenshot(name,loginName,userName));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(getActivity(), "Screenshot Captured", Toast.LENGTH_SHORT).show();
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  File saveScreenshot(String fileName,String loginFileName,String userDirName)
    {
        String fileNameDir="BplCardiartConnect";

        File file =new File(Environment.getExternalStorageDirectory(),fileNameDir);
        if(!file.exists())
        {
            file.mkdir();

        }

        File loginFile=new File(file,loginFileName);

        if(!loginFile.exists()){

            loginFile.mkdir();
        }



        File userDir=new File(loginFile,userDirName);

        if(!userDir.exists()){
            userDir.mkdir();
        }

        File screenShotFile=new File(userDir,fileName);

        try {
            FileWriter filewriter=new FileWriter(screenShotFile,false);
            filewriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return screenShotFile;
    }


    private void showGridsForMainEcg(PDPageContentStream contentStream) throws IOException {

        float unit_per_cm = 28.34f;
        float rect_width = unit_per_cm * 25f;
        float rect_height = unit_per_cm * 18f;


        float cursorX = 60f;
        float cursorY = 20f;

        contentStream.setLineWidth(1.5f);
        // vertical grids
        contentStream.setStrokingColor(AWTColor.PINK);

        for (int i = 0; i < 27; i++) {
            contentStream.moveTo(cursorX, cursorY);
            contentStream.lineTo(cursorX, rect_height + cursorY);
            contentStream.stroke();
            cursorX = cursorX + unit_per_cm;

        }


        // Horizontal grids

        cursorX = 60;
        cursorY = 20;

        for (int i = 0; i < 19; i++) {
            contentStream.moveTo(cursorX, cursorY);
            contentStream.lineTo(cursorX + rect_width + unit_per_cm, cursorY);
            contentStream.stroke();
            cursorY = cursorY + unit_per_cm;
        }


        cursorX = 60;
        cursorY = 20;
        contentStream.setLineWidth(0.2f);

        contentStream.setStrokingColor(AWTColor.PINK);
        // draw minor vertical grids
        for (int i = 0; i < 260; i++) {
            contentStream.moveTo(cursorX, cursorY);
            contentStream.lineTo(cursorX, rect_height + cursorY);

            contentStream.stroke();
            cursorX = cursorX + unit_per_cm / 10f;

        }


        // draw minor horizontal grids

        cursorX = 60;
        cursorY = 20;


        for (int i = 0; i < 180; i++) {
            contentStream.moveTo(cursorX, cursorY);
            contentStream.lineTo(cursorX + rect_width + unit_per_cm, cursorY);
            contentStream.stroke();
            cursorY = cursorY + unit_per_cm / 10f;
        }

    }

     @SuppressLint("StaticFieldLeak")
     class EcgPdf extends AsyncTask<String,String,String>{



         ProgressDialog progressDialog;

         String isloaded = "false";


         @Override
         protected String doInBackground(String... strings) {


             try {

                createPdf();
                 isloaded = "true";
             } catch (Exception e) {
                 isloaded = "false";
                 e.printStackTrace();
             }


             return isloaded;
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             if (progressDialog == null) {
                 progressDialog = new ProgressDialog(getActivity());
                 progressDialog.setMessage("Please wait ......");

                 progressDialog.setCancelable(false);
                 progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                 progressDialog.show();

             }


         }


         @Override
         protected void onCancelled(String s) {
             super.onCancelled(s);
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             if (progressDialog != null && progressDialog.isShowing()) {
                 progressDialog.dismiss();
                 if (isloaded.equalsIgnoreCase("true")) {
                     Toast.makeText(getActivity(),
                             "PDF  Successfully created", Toast.LENGTH_SHORT).show();


                 }

             }
         }
    }




    private   void createPdf(){


        PDDocument document = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        float widthScale = 0;
        float heightScale = 0;
        float graphHeight = 42.51f;


        String leadArr[]={"V6","V5","V4","V3","V2","V1","aVF","aVL","aVR","III","II","I"};


        float page_height = page.getMediaBox().getHeight();
        float page_width = page.getMediaBox().getWidth();


        PDFBoxResourceLoader.init(getActivity());

        String fileNameDirectory = "BPL_CARDIART";

        File file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), fileNameDirectory);

        if (!file.exists()) {
            file.mkdir();

        }


        // String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
        //        + "/Documents/"+userName+"_"+System.currentTimeMillis()+"_LPP_ACT.pdf";

        String path = "";
        try {
            path = file + "/" + "Cardiart" + "_" + "_LPP1_ECG.pdf";
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("***Error in File**", "Unable to get File path");
        }


        document.addPage(page);

        float cursorX = 60f;
        float cursorY = 20f;


        PDPageContentStream contentStream = null;


        float unit_per_cm = 28.34f;
        float rect_width = unit_per_cm * 25f;
        float rect_height = unit_per_cm * 18f;

        float marginLowerLine = rect_height + cursorY + 5;
        float marginUpperLine = rect_height + 2 * unit_per_cm;
        float rectangle_X = rect_width + 60;
        float rectangle_Y = marginUpperLine;

        try {
            contentStream = new PDPageContentStream(document, page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            //contentStream.setNonStrokingColor(200, 200, 200); //gray background

            contentStream.addRect(40f, 16f, rectangle_X, marginUpperLine);
            contentStream.setStrokingColor(AWTColor.BLACK);
            contentStream.setLineWidth(1.4f);

            contentStream.moveTo(40f, marginLowerLine);
            contentStream.lineTo(rectangle_X + 40, marginLowerLine);
            contentStream.stroke();

            //draw text
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 11);
            contentStream.newLineAtOffset(120f, marginUpperLine + 2);
            contentStream.showText("Patient Id : " + "123456" + "   " + "Patient Name : " + "Mr. Prembrooke" + "   " + "Age : " + "52" + "   " + "Gender : " +
                    "male" + "  " + "Clinic Name : " + "Apollo");

            contentStream.endText();

            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 11);
            contentStream.newLineAtOffset(120f, marginUpperLine - 12);
            contentStream.showText("Symptoms :" + "ChestPain,Diziness");

            contentStream.endText();

            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(120f, marginUpperLine - 24);
            contentStream.showText("Comments : " + "comments");
            contentStream.endText();


            // Add app version
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(page_width - 160, marginUpperLine + 2f);
            contentStream.showText("Date :" + "-------");
            contentStream.endText();


            // Add text
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 6);
            contentStream.newLineAtOffset(page_width - 330, marginUpperLine - 12);
            contentStream.showText("This report is intended to be read, only by a qualified medical professional."
                    + "( App Version :" + "1.0.0" + " )");
            contentStream.endText();


            // add image

            AssetManager assetManager = getActivity().getAssets();
            InputStream is = null;
            InputStream alpha;
            is = assetManager.open("bpl.png");
            //alpha =  assetManager.open("bpl.png");

            // Bitmap b= BitmapFactory.decodeStream(alpha);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            PDImageXObject ximage = LosslessFactory.createFromImage(document, bitmap);
            //  PDImageXObject yimage=LosslessFactory.createFromImage(document,b);


            contentStream.drawImage(ximage, 40 + 5f, marginLowerLine + 0.5f);
            //contentStream.drawImage(yimage,rect_width+cursorX-20,marginLowerLine);


            contentStream.moveTo(100f, marginLowerLine + 2f);
            contentStream.lineTo(100f, marginUpperLine + 15);
            contentStream.stroke();


            // remove second box


            /*contentStream.moveTo(rect_width+cursorX-20,marginLowerLine);
            contentStream.lineTo(rect_width+cursorX-20,marginUpperLine+15);
            contentStream.stroke();
*/


            showGridsForMainEcg(contentStream);



            // Find out the pixel density
            widthScale = unit_per_cm / (float) 400; // 100 ... 0.28
            // samples per cm
            heightScale = (float) 392/ unit_per_cm; // pixels  328/63=5.2

            //float mX=56.69f;
            //float mP1=0f;

          //  filterMedianLead1Data(328, MEdianData.Lead1MedianArray);

            //-------------


            float mP1 = 10f, mPp1 = 10f;

            float mP2 = 10f, mPp2 = 10f;

            float mP3 = 10f, mPp3 = 10f;

            float mP4 = 10f, mPp4 = 10f;

            float mP5 = 10f, mPp5 = 10f;

            float mP6 = 10f, mPp6 = 10f;

            float mP7 = 10f, mPp7 = 10f;

            float mP8 = 10f, mPp8 = 10f;

            float mP9 = 10f, mPp9 = 10f;

            float mP10 = 10f, mPp10 = 10f;

            float mP11 = 10f, mPp11 = 10f;

            float mP12 = 10f, mPp12 = 10f;

            float mX = 60f+unit_per_cm; // add 1 cm to  starting point

            mP1 = mP2 = mP3 = 0;
            mP4 = mP5 = mP6 = mP7 = mP8 = mP9 = mP10 = mP11 = mP12 = 0;


            float mPx1 = mX;
            mPp1 = 1 * graphHeight;   // 126
            float mPx2 = mX;
            mPp2 = 2 * graphHeight; // graphHeight * 2;   315
            float mPx3 = mX;
            mPp3 = 3 * graphHeight; // graphHeight * 3;   504
            float mPx4 = mX;
            mPp4 = 4 * graphHeight; // graphHeight * 3;  693
            float mPx5 = mX;
            mPp5 = 5 * graphHeight; // graphHeight * 3;
            float mPx6 = mX;
            mPp6 = 6 * graphHeight; // graphHeight * 3;
            float mPx7 = mX;
            mPp7 = 7 * graphHeight; // graphHeight * 3;
            float mPx8 = mX;
            mPp8 = 8 * graphHeight; // graphHeight * 3;
            float mPx9 = mX;
            mPp9 = 9 * graphHeight; // graphHeight * 3;
            float mPx10 = mX;
            mPp10 = 10 * graphHeight; // graphHeight * 3;
            float mPx11 = mX;
            mPp11 = 11 * graphHeight; // graphHeight * 3;


            float mPx12 = mX;
            mPp12 = 12 * graphHeight; // graphHeight * 3;

            int noOfLeads = 12;

            final int leadSampleCount = 2500;
            contentStream.setLineWidth(1.0f);


            int count = 0;

            for (int i = 0; i < 10000; i++) {


                mP1 = (1 * graphHeight) + (LeadI.get(i)/ heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx1, mPp1);
                    contentStream.lineTo(mX, mP1);
                    contentStream.stroke();

                    mPx1 = mX;
                    mPp1 = mP1;


                mP2 = (2 * graphHeight) + (LeadII.get(i) / heightScale);
                contentStream.setStrokingColor(AWTColor.black);
                contentStream.moveTo(mPx2, mPp2);
                contentStream.lineTo(mX, mP2);
                contentStream.stroke();

                mPx2 = mX;
                mPp2 = mP2;




                    mP3 = (3 * graphHeight) + (LeadI.get(i)/ heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx3, mPp3);
                    contentStream.lineTo(mX, mP3);
                    contentStream.stroke();

                    mPx3 = mX;
                    mPp3 = mP3;










                    mP4 = (4 * graphHeight) + (LeadI.get(i) / heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx4, mPp4);
                    contentStream.lineTo(mX, mP4);
                    contentStream.stroke();

                    mPx4 = mX;
                    mPp4 = mP4;




                    mP5 = (5 * graphHeight) + (LeadI.get(i) / heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx5, mPp5);
                    contentStream.lineTo(mX, mP5);
                    contentStream.stroke();

                    mPx5 = mX;
                    mPp5 = mP5;



                mP6 = (6 * graphHeight) + (LeadI.get(i) / heightScale);
                contentStream.setStrokingColor(AWTColor.black);
                contentStream.moveTo(mPx6, mPp6);
                contentStream.lineTo(mX, mP6);
                contentStream.stroke();

                mPx6 = mX;
                mPp6 = mP6;

                mP7 = (7 * graphHeight) + (LeadV11.get(i) / heightScale);
                contentStream.setStrokingColor(AWTColor.black);
                contentStream.moveTo(mPx7, mPp7);
                contentStream.lineTo(mX, mP7);
                contentStream.stroke();

                mPx7 = mX;
                mPp7 = mP7;




                    // lead 5

                mP8 = (8 * graphHeight) + (LeadVIII.get(i) / heightScale);
                contentStream.setStrokingColor(AWTColor.black);
                contentStream.moveTo(mPx8, mPp8);
                contentStream.lineTo(mX, mP8);
                contentStream.stroke();

                mPx8 = mX;
                mPp8 = mP8;



                // lead 4
                mP9 = (9 * graphHeight) +(LeadIX.get(i)/ heightScale);
                contentStream.setStrokingColor(AWTColor.black);
                contentStream.moveTo(mPx9, mPp9);
                contentStream.lineTo(mX, mP9);
                contentStream.stroke();

                mPx9 = mX;
                mPp9 = mP9;


                    mP10 = (10 * graphHeight) + (LeadX.get(i)/ heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx10, mPp10);
                    contentStream.lineTo(mX, mP10);
                    contentStream.stroke();

                    mPx10 = mX;
                    mPp10 = mP10;




                    mP11 = (11 * graphHeight) + (LeadXI.get(i)/ heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx11, mPp11);
                    contentStream.lineTo(mX, mP11);
                    contentStream.stroke();

                    mPx11 = mX;
                    mPp11 = mP11;




                    mP12 = (12 * graphHeight) + (LeadX11.get(i) / heightScale);
                    contentStream.setStrokingColor(AWTColor.black);
                    contentStream.moveTo(mPx12, mPp12);
                    contentStream.lineTo(mX, mP12);
                    contentStream.stroke();

                    mPx12 = mX;
                    mPp12 = mP12;



                mX = mX + widthScale;
                // System.out.println("count mx="+count++  +"mX value="+mX);


            }


            cursorX = 60;
            for (int x =0; x<leadArr.length; x++) {
                contentStream.setNonStrokingColor(0, 0, 0); //black text
                contentStream.beginText();
                contentStream.setFont(font, 10);
                if (x == 7) {
                    contentStream.moveTextPositionByAmount(cursorX, ((x+1) * graphHeight));
                }
                else
                {
                    contentStream.moveTextPositionByAmount(cursorX, ((x+1) * graphHeight) + 4f);
                }

                Logger.log(Level.DEBUG,"==",leadArr[x] +" Y pos="+(x+1)*graphHeight);
                contentStream.drawString(leadArr[x]);
                contentStream.endText();
            }


            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(2 * 60f, 20f + 5);
            contentStream.showText("10mm/mV    25mm/s");
            contentStream.endText();


            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(5 * 60f - 10, 20f + 5);
            contentStream.showText("0.5Hz  to  32Hz                     BPL CARDIART GenX3");
            contentStream.endText();


            // Add calibration in ECG Page
            //cursorX=42f;
            cursorX = 60f;
            cursorY = page_height / 2 + 10f;

            contentStream.moveTo(cursorX, cursorY);
            contentStream.lineTo(cursorX + (unit_per_cm / 10), cursorY);
            contentStream.stroke();


            contentStream.moveTo(cursorX + (unit_per_cm / 10), cursorY);
            contentStream.lineTo(cursorX + (unit_per_cm / 10), cursorY + unit_per_cm);
            contentStream.stroke();

            contentStream.moveTo(cursorX + (unit_per_cm / 10), cursorY + unit_per_cm);
            contentStream.lineTo((cursorX + unit_per_cm / 10) + (unit_per_cm / 10 * 3), cursorY + unit_per_cm);
            contentStream.stroke();

            contentStream.moveTo((cursorX + unit_per_cm / 10) + (unit_per_cm / 10 * 3), cursorY + unit_per_cm);
            contentStream.lineTo((cursorX + unit_per_cm / 10) + (unit_per_cm / 10 * 3), cursorY);
            contentStream.stroke();

            contentStream.moveTo((cursorX + unit_per_cm / 10) + (unit_per_cm / 10 * 3), cursorY);
            contentStream.lineTo((cursorX + unit_per_cm / 10 * 3) + 2 * (unit_per_cm / 10), cursorY);
            contentStream.stroke();


            contentStream.close();

            //---------------------

            // Add Second Page and 3rd page

            //    createSecondPage(document, rectangle_X, marginUpperLine, marginLowerLine);
            //  createThirdPage(document, rectangle_X, marginUpperLine, marginLowerLine, page_height);

            //  createfourthpage(context,document,page_width);

            document.save(path);
            document.close();


        } catch (IOException e) {
            e.printStackTrace();

        }


    }




    private List<Integer> computeLead1ECG(List<String> EcgLead) {
        LeadI = new ArrayList<>();
        int index = 0;
        int sample = 0;
        for (String str : EcgLead) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {
                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //   sample = sample / 2;

                    LeadI.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;


            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadI.size());
        return LeadI;
    }


    private List<Integer> computeLead2ECG(List<String> EcgLead2) {
        LeadII = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead2) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //  sample = sample / 2;

                    LeadII.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadII.size());
        return LeadII;
    }


    private List<Integer> computeLead3ECG(int EcgLead3[]) {
        LeadIII = new ArrayList<>();
        int index = 0,sample=0;
        for (int i=0;i<EcgLead3.length;i++) {

            try {

                sample=EcgLead3[i];
                sample = sample * 2;
                sample = sample / 3;
                LeadIII.add(sample);
            } catch (Exception e) {
                e.printStackTrace();

                Log.i("Print i=", "" + index);
            }


            if (index > 10001)
                break;
        }



        Log.i("Ecg lead length -2048 =", "" + LeadIII.size());
        return LeadIII;
    }


    private List<Integer> computeLeadaVRECG(List<String> EcgLeadaVR) {
        LeadaVR = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLeadaVR) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //   sample = sample / 2;

                    LeadaVR.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadaVR.size());
        return LeadaVR;
    }


    private List<Integer> computeLead7ECG(List<String> EcgLead7) {
        LeadV11 = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead7) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //  sample = sample / 2;


                    LeadV11.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadV11.size());
        return LeadV11;
    }


    private List<Integer> computeLead8ECG(List<String> EcgLead8) {
        LeadVIII = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead8) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //sample = sample / 2;


                    LeadVIII.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadVIII.size());
        return LeadVIII;
    }



    private List<Integer> computeLead9ECG(List<String> EcgLead9) {
        LeadIX = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead9) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //   sample = sample / 2;

                    LeadIX.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadIX.size());
        return LeadIX;
    }


    private List<Integer> computeLead10ECG(List<String> EcgLead10) {
        LeadX = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead10) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //  sample = sample / 2;


                    LeadX.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadX.size());
        return LeadX;
    }


    private List<Integer> computeLead11ECG(List<String> EcgLead11) {
        LeadXI = new ArrayList<>();
        int index = 0, sample = 0;

        for (String str : EcgLead11) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {
                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //  sample = sample / 2;


                    LeadXI.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadXI.size());
        return LeadXI;
    }


    private List<Integer> computeLead12ECG(List<String> EcgLead12) {
        LeadX11 = new ArrayList<>();
        int index = 0, sample = 0;
        for (String str : EcgLead12) {
            if (str != null && !str.isEmpty()) {
                index++;
                try {

                    sample = Integer.parseInt(str) - 2048;

                    sample = sample * 2;
                    sample = sample / 3;
                    //   sample = sample / 2;
                    LeadX11.add(sample);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("value ", "" + str);
                    Log.i("Print i=", "" + index);
                }


                if (index > 10001)
                    break;
            }
        }


        Log.i("Ecg lead length -2048 =", "" + LeadX11.size());
        return LeadX11;
    }
}
