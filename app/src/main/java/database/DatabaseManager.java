package database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import java.util.*;

import logger.*;
import model.*;

import static database.FeedReaderDbHelper.PASSWORD;
import static database.FeedReaderDbHelper.PATIENT_AGE;
import static database.FeedReaderDbHelper.PATIENT_CLINICAL_DIAGNOSIS;
import static database.FeedReaderDbHelper.PATIENT_CONSULTATTION_DOC;
import static database.FeedReaderDbHelper.PATIENT_DIABOLIC_DATA;
import static database.FeedReaderDbHelper.PATIENT_DRUG1;
import static database.FeedReaderDbHelper.PATIENT_DRUG2;
import static database.FeedReaderDbHelper.PATIENT_GENDER;
import static database.FeedReaderDbHelper.PATIENT_HEIGHT;
import static database.FeedReaderDbHelper.PATIENT_ID;
import static database.FeedReaderDbHelper.PATIENT_NAME;
import static database.FeedReaderDbHelper.PATIENT_PACEMAKER;
import static database.FeedReaderDbHelper.PATIENT_RACE;
import static database.FeedReaderDbHelper.PATIENT_REF_DOC;
import static database.FeedReaderDbHelper.PATIENT_SYSTOLIC_DATA;
import static database.FeedReaderDbHelper.PATIENT_TEST_TIME;
import static database.FeedReaderDbHelper.PATIENT_WEIGHT;
import static database.FeedReaderDbHelper.SECURITY_Q_1;
import static database.FeedReaderDbHelper.SECURITY_Q_2;
import static database.FeedReaderDbHelper.SECURITY_Q_3;
import static database.FeedReaderDbHelper.TABLE_NAME_PATIENT_PROFILE;
import static database.FeedReaderDbHelper.TABLE_NAME_USERS;
import static database.FeedReaderDbHelper.USER_NAME;

public class DatabaseManager {

    private final String TAG="DatabaseManager";


    private int mOpenCounter;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private static DatabaseManager instance;

    // create  a safe singelton

    private static volatile DatabaseManager databaseManagerInstance;




    //private constructor.
    private DatabaseManager(){

        //Prevent form the reflection api.
        if (databaseManagerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }



    public static DatabaseManager getInstance() {
        //Double check locking pattern
        if (databaseManagerInstance == null) { //Check for the first time

            synchronized (DatabaseManager.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (databaseManagerInstance == null)
                    databaseManagerInstance = new DatabaseManager();
            }
        }

        return databaseManagerInstance;
    }




    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();

         //   mDatabaseHelper.onUpgrade(mDatabase,1,2); // upgrade database
            // mDatabaseHelper.onDowngrade(mDatabase,1,2);
        }

        Logger.log(Level.DEBUG,TAG,"--GET Current Database Version--"+mDatabase.getVersion());
        return mDatabase;
    }



    public boolean IsUsernameexists(String username)
    {
        boolean b=false;

        Cursor cursor=null;
        try{
             cursor=mDatabase.query(FeedReaderDbHelper.TABLE_NAME_USERS,
                    new String[]{USER_NAME,PASSWORD,SECURITY_Q_1,SECURITY_Q_2,SECURITY_Q_3},
                    USER_NAME+"=?", new String[]{username}, null, null, null);

            Logger.log(Level.INFO, "Get count of username exists=", "" +  cursor.getCount());
            if(cursor.getCount()>=1)
            {
                b=true;
                Logger.log(Level.INFO,TAG,"Username already exists inside table");

                }

            return b;
        }

        catch (Exception e){
            e.printStackTrace();
        }

        finally {
            try {
                if(cursor!=null)
                cursor.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }





        Logger.log(Level.INFO,TAG,"Username is new  inside table");
        return b;



    }


    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }




    // Login into app
    public boolean Login(String username,String password) throws SQLException
    {

        Logger.log(Level.INFO, "DatabaseManager", "username " +username);
        Logger.log(Level.INFO, "DatabaseManager", "password " +password);


        Cursor cursor=mDatabase.query(TABLE_NAME_USERS, new String[]{USER_NAME,PASSWORD},
                USER_NAME + "=?"+" "+"AND"+" "+PASSWORD+"=?" , new String[]{username,password}, null, null, null);

        Logger.log(Level.INFO, "DatabaseManager", "Get  username and password count=" + cursor.getCount());

        if(cursor.moveToNext())
        {
            Logger.log(Level.INFO, "DatabaseManager",
                    "Get  username value=" + cursor.getString(cursor.getColumnIndex("user_name")));
        }

        if(cursor.getCount() > 0)
        {
            return true;
        }


        try {
            if(cursor!=null)
                cursor.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }






    public boolean IssecurityQvalid(String username,String sq1,String sq2,String sq3)
    {
        boolean b=false;

        List<String> list=new ArrayList<>();

        Cursor cursor=null;

        try
        {
            cursor=mDatabase.query(TABLE_NAME_USERS,
                    new String[]{SECURITY_Q_1,SECURITY_Q_2,SECURITY_Q_3},USER_NAME+"=?",new String[] {username},null,null,null);

            Logger.log(Level.INFO,TAG, "Get count of username exists for all 3 security questions= "+ cursor.getCount());
            if(cursor.moveToNext())
            {
                list.add(cursor.getString(cursor.getColumnIndex(SECURITY_Q_1)));
                list.add(cursor.getString(cursor.getColumnIndex(SECURITY_Q_2)));
                list.add(cursor.getString(cursor.getColumnIndex(SECURITY_Q_3)));

                Logger.log(Level.INFO, "DataBaseManager get security q1", cursor.getString(cursor.getColumnIndex(SECURITY_Q_1)));
                Logger.log(Level.INFO,"DataBaseManager get security q2",cursor.getString(cursor.getColumnIndex(SECURITY_Q_2)));
                Logger.log(Level.INFO,"DataBaseManager get security q3",cursor.getString(cursor.getColumnIndex(SECURITY_Q_3)));

            }


            if(list.get(0).equals(sq1) && list.get(1).equals(sq2) && list.get(2).equals(sq3))
            {
                b=true;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                if(cursor!=null)
               cursor.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return b;


    }


    public boolean isPatientIdExists(String patientId)
    {
        boolean b=false;

        Cursor cursor=null;
        try{
            cursor=mDatabase.query(FeedReaderDbHelper.TABLE_NAME_PATIENT_PROFILE, null,
                    PATIENT_ID+"=?", new String[]{patientId}, null,
                    null, null);

            Logger.log(Level.INFO, "Get count of username exists=", "" +  cursor.getCount());
            if(cursor.getCount()>=1)
            {
                b=true;
                Logger.log(Level.INFO,TAG,"Patient Id already exists inside table");

            }

            return b;
        }

        catch (Exception e){
            e.printStackTrace();
        }

        finally {
            try {
                if(cursor!=null)
                    cursor.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }





        Logger.log(Level.INFO,TAG,"Patient Id  is new  inside table");
        return b;


    }

    // to update password into the database

    public void update_password(String username,String new_password)
    {
        ContentValues cv=new ContentValues();
        cv.put(PASSWORD, new_password);
        mDatabase.update(TABLE_NAME_USERS, cv, USER_NAME + "=?", new String[]{username});
    }





    public List<PatientModel> getAllPatientsFromDB()
    {
        List<PatientModel> patientModelList=new ArrayList<>();

        Cursor cursor;

           // cursor=mDatabase.query(TABLE_NAME_PATIENT_PROFILE,
               //     null,USER_NAME_+"=?",new String[]{username},null,null,null);

          cursor = mDatabase.rawQuery("select * from " + TABLE_NAME_PATIENT_PROFILE, null);

        Logger.log(Level.DEBUG, TAG, TABLE_NAME_PATIENT_PROFILE+"(((Get profile count=)))" + cursor.getCount()+"--column count" +
                +cursor.getColumnCount()+"");


        while (cursor.moveToNext())
        {
            PatientModel m=new PatientModel();
            m.setPatName(cursor.getString(cursor.getColumnIndex(PATIENT_NAME)));
           // m.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME_)));
            m.setPatId(cursor.getInt(cursor.getColumnIndex(PATIENT_ID)));
            m.setTime(cursor.getString(cursor.getColumnIndex(PATIENT_TEST_TIME)));
            m.setGender(cursor.getString(cursor.getColumnIndex(PATIENT_GENDER)));
            m.setPatAge(cursor.getInt(cursor.getColumnIndex(PATIENT_AGE)));

            m.setHeight(cursor.getString(cursor.getColumnIndex(PATIENT_HEIGHT)));
            m.setWeight(cursor.getString(cursor.getColumnIndex(PATIENT_WEIGHT)));



            m.setPatRace(cursor.getString(cursor.getColumnIndex(PATIENT_RACE)));
            m.setPacemaker(cursor.getString(cursor.getColumnIndex(PATIENT_PACEMAKER)));

            m.setDrug1(cursor.getString(cursor.getColumnIndex(PATIENT_DRUG1)));
            m.setDrug2(cursor.getString(cursor.getColumnIndex(PATIENT_DRUG2)));

            m.setClinicDiagnosis(cursor.getString(cursor.getColumnIndex(PATIENT_CLINICAL_DIAGNOSIS)));
            m.setSystolic(cursor.getString(cursor.getColumnIndex(PATIENT_SYSTOLIC_DATA)));

            m.setDiabolic(cursor.getString(cursor.getColumnIndex(PATIENT_DIABOLIC_DATA)));
            m.setConsultName(cursor.getString(cursor.getColumnIndex(PATIENT_CONSULTATTION_DOC)));

            m.setPatRefDoc(cursor.getString(cursor.getColumnIndex(PATIENT_REF_DOC)));





            patientModelList.add(m);
        }

        cursor.close();


        Logger.log(Level.DEBUG, TAG, "patientModelList length=" + patientModelList.size());

        return patientModelList;
    }
}
