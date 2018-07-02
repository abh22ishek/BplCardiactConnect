package application;

import android.app.*;

import database.*;

public class BaseApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.initializeInstance(new FeedReaderDbHelper(getApplicationContext()));
    }
}
