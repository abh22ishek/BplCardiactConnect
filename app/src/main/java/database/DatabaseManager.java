package database;

import android.database.*;
import android.database.sqlite.*;

import logger.*;

import static database.FeedReaderDbHelper.PASSWORD;
import static database.FeedReaderDbHelper.SECURITY_Q_1;
import static database.FeedReaderDbHelper.SECURITY_Q_2;
import static database.FeedReaderDbHelper.SECURITY_Q_3;
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
            //  mDatabaseHelper.onUpgrade(mDatabase,1,2); // upgrade database
            // mDatabaseHelper.onDowngrade(mDatabase,1,2);
        }

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
            cursor.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }


}
