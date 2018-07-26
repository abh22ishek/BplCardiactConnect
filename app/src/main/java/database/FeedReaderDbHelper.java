package database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import logger.*;

public class FeedReaderDbHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String TAG="FeedReaderDbHelper";

    // Database Name
    public static final String DATABASE_NAME = "UserDatabase.db";

    // Table Names
    public static final String TABLE_NAME_USERS = "Users";

    public static final String TABLE_NAME_PATIENT = "Patients";

    public static final String TABLE_NAME_RECORDS = "Records";






    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_ID_RECORDS = "records_no";

    public static final String USER_NAME ="user_name";
    public static final String PASSWORD="password";

    public static final String SECURITY_Q_1="security_question_1";
    public static final String SECURITY_Q_2="security_question_2";
    public static final String SECURITY_Q_3="security_question_3";



    public static final String PATIENT_NAME="patient_name";

    public static final String PATIENT_ID="patient_id";

    public static final String PATIENT_TEST_TIME="patient_test_time";

    public static final String PATIENT_GENDER="patient_gender";

    public static final String PATIENT_ECG_DATA="patient_ecg_data";




    public static final String CREATE_TABLE="CREATE TABLE " +TABLE_NAME_USERS+"("+KEY_ID+" integer primary key autoincrement"+","
            +USER_NAME+" "+"text"+","+PASSWORD+" "+"text"+","+SECURITY_Q_1+" "+"text"+","
            +SECURITY_Q_2+" "+"text"+","+SECURITY_Q_3+" "+"text"+")";


    public static final String CREATE_TABLE_PATIENTS="CREATE TABLE " +TABLE_NAME_PATIENT+"("+KEY_ID+" integer primary key autoincrement"+","
            +PATIENT_NAME+" "+"text"+","+PATIENT_ID+" "+"text"+","+PATIENT_TEST_TIME+" "+"text"+","
            +PATIENT_GENDER+" "+"text"+","+PATIENT_ECG_DATA+" "+"text"+")";


    public FeedReaderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public FeedReaderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_PATIENTS);



        Logger.log(Level.INFO, TAG, CREATE_TABLE);
        Logger.log(Level.INFO, TAG, CREATE_TABLE_PATIENTS);

        Logger.log(Level.DEBUG, TAG, "Table " +TABLE_NAME_USERS + " got created");
        Logger.log(Level.DEBUG, TAG, "Table " +TABLE_NAME_PATIENT+ " got created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        Logger.log(Level.DEBUG,TAG,"On Downgrade ()" +
                " is called with new Version="+newVersion +"and Old version="+oldVersion);
    }
}
