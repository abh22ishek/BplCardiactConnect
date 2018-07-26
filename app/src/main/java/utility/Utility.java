package utility;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.util.*;

import java.io.*;
import java.util.*;

import database.*;
import gennx.model.*;
import logger.*;

public class Utility {

    private final String TAG=Utility.class.getSimpleName();

    public static synchronized    List<EcgLEadModel> readfile(String filedir, String filename)

    {

        String ecgData="";

        List<List<String>> ecgList = new ArrayList<>();

        List<EcgLEadModel> EcgmodelList=new ArrayList<>();


        EcgLEadModel e=new EcgLEadModel();
        File file = new File(Environment.getExternalStorageDirectory(), filedir);
        StringBuffer sb = null;

        List<String> EcgLead1String=new ArrayList<>();
        List<String> EcgLead2String=new ArrayList<>();


        List<String> EcgLeadV1=new ArrayList<>();
        List<String> EcgLeadV2=new ArrayList<>();

        List<String> EcgLeadV3=new ArrayList<>();

        List<String> EcgLeadV4=new ArrayList<>();
        List<String> EcgLeadV5=new ArrayList<>();
        List<String> EcgLeadV6=new ArrayList<>();

        try {
            File[] dirFiles = file.listFiles();
            sb = new StringBuffer();
            if (dirFiles.length != 0) {
                // loops through the array of files, outputing the name to console
                for (File dirFile : dirFiles) {
                    if (dirFile.getName().equalsIgnoreCase(filename)) ;
                    {
                        if (file.exists()) {
                            try {

                                BufferedReader buf = new BufferedReader(new FileReader(dirFile));
                                String line;
                                while ((line = buf.readLine()) != null) {

                                    sb.append(line);
                                    sb.append("\n");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } else {

                            Log.i("File Info", "File doesn't exists");
                        }
                    }

                }

            }


            populateDatabaseWithPatients(sb.toString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }


        Logger.log(Level.DEBUG,"File length=", "" + sb.length());
        Logger.log(Level.DEBUG,"Read File Inside in String=", "" + sb.toString());

        String arr1[]=sb.toString().split(",");

        for (int i=0;i<arr1.length;i=i+8)
            {



                 EcgLead1String.add(arr1[i].replace("\n",""));
             }

        for (int i=1;i<arr1.length;i=i+8)
        {

            EcgLead2String.add(arr1[i].replace("\n",""));
        }


        for (int i=2;i<arr1.length;i=i+8)
        {

            EcgLeadV1.add(arr1[i].replace("\n",""));
        }


        for (int i=3;i<arr1.length;i=i+8)
        {

            EcgLeadV2.add(arr1[i].replace("\n",""));
        }

        for (int i=4;i<arr1.length;i=i+8)
        {

            EcgLeadV3.add(arr1[i].replace("\n",""));
        }
        for (int i=5;i<arr1.length;i=i+8)
        {

            EcgLeadV4.add(arr1[i].replace("\n",""));
        }
        for (int i=6;i<arr1.length;i=i+8)
        {

            EcgLeadV5.add(arr1[i].replace("\n",""));
        }
        for (int i=7;i<arr1.length;i=i+8)
        {

            EcgLeadV6.add(arr1[i].replace("\n",""));
        }





        Logger.log(Level.DEBUG,"EcgLead1String length=", "" + EcgLead1String.size()+ " 10th itemzs" +Integer.parseInt(EcgLead1String.get(10)));

        Logger.log(Level.DEBUG,"EcgLead2String length=", "" + EcgLead2String.size()+ " 10th itemzs" +Integer.parseInt(EcgLead2String.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV1 length=", "" + EcgLeadV1.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV1.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV2 length=", "" + EcgLeadV2.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV2.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV3 length=", "" + EcgLeadV3.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV3.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV4 length=", "" + EcgLeadV4.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV4.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV5 length=", "" + EcgLeadV5.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV5.get(10)));

        Logger.log(Level.DEBUG,"EcgLeadV6 length=", "" + EcgLeadV6.size()+ " 10th itemzs" +Integer.parseInt(EcgLeadV6.get(10)));


         e.setEcgLead1(EcgLead1String);
        e.setEcgLead2(EcgLead2String);
        e.setEcgLeadV1(EcgLeadV1);


        e.setEcgLeadV2(EcgLeadV2);
        e.setEcgLeadV3(EcgLeadV3);
        e.setEcgLeadV4(EcgLeadV4);

        e.setEcgLeadV5(EcgLeadV5);
        e.setEcgLeadV6(EcgLeadV6);



        ecgList.add(EcgLead1String);
        ecgList.add(EcgLead2String);



        ecgList.add(EcgLeadV1);
        ecgList.add(EcgLeadV2);
        ecgList.add(EcgLeadV3);
        ecgList.add(EcgLeadV4);

        ecgList.add(EcgLeadV5);
        ecgList.add(EcgLeadV6);


        EcgmodelList.add(e);

        return EcgmodelList;
    }






    private  static void populateDatabaseWithPatients(String ecgData)
    {
        try {
            final SQLiteDatabase database= DatabaseManager.getInstance().openDatabase();

            for(int i=0;i<200;i++){
                database.insert(FeedReaderDbHelper.TABLE_NAME_PATIENT, null,
                        addPatientData(

                                "Patient 2", "222222","","Male",ecgData));
            }




            Logger.log(Log.DEBUG,"--","Patient Record inserted");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static ContentValues addPatientData(String patname, String patId, String test_time,
                                   String gender, String ecg)
    {
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.PATIENT_NAME, patname);
        values.put(FeedReaderDbHelper.PATIENT_ID, patId);
        values.put(FeedReaderDbHelper.PATIENT_TEST_TIME, test_time);
        values.put(FeedReaderDbHelper.PATIENT_GENDER, gender);
        values.put(FeedReaderDbHelper.PATIENT_ECG_DATA, ecg);

        return values;

    }





}
