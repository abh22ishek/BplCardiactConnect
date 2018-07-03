package application;

import android.app.*;
import android.content.res.*;

import database.*;

public class BaseApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.initializeInstance(new FeedReaderDbHelper(getApplicationContext()));
    }


    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
