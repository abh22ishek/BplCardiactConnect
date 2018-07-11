package login.fragment;

import android.net.*;

public interface LoginActivityListner {

    void onDataPass(String data);
    void OnCurrentFragment(String tag);
    void navigateFragment(String tag);
    void setUserName(String userName,String  tag);

    void displayImage(Uri uri);
}
