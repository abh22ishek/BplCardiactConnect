package application;

import android.app.*;
import android.content.res.*;
import android.os.*;

import database.*;
import logger.*;

public class BaseApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.log(Level.INFO,"==","Application CLass On Create()");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        DatabaseManager.initializeInstance(new FeedReaderDbHelper(getApplicationContext()));
    }


    private String username;

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
