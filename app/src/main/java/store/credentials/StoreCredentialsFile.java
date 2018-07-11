package store.credentials;

import android.content.*;

import constants.*;
import logger.*;

public class StoreCredentialsFile {


    public static SharedPreferences storeSignUpCredentials(Context context, String user_name, String TAG)
    {

        SharedPreferences storeSignUpCredentials;
        storeSignUpCredentials=context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = storeSignUpCredentials.edit();

        editor.putString(Constants.USER_NAME,user_name);

        editor.apply();

        Logger.log(Level.DEBUG, TAG, "shared preference s login credentials  gets stored");

        return  storeSignUpCredentials;
    }



    public static SharedPreferences store_profile_image(Context context,String image,String TAG,String key_username)
    {

        SharedPreferences profile_image;
        profile_image=context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = profile_image.edit();
        editor.putString(key_username,image);
        editor.apply();


        Logger.log(Level.DEBUG, TAG, "shared preference s image  gets stored"+image);
        return  profile_image;
    }
}
