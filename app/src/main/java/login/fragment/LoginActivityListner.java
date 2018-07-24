package login.fragment;

import android.net.*;

import java.util.*;

import model.*;

public interface LoginActivityListner {

    void onDataPass(String data);
    void OnCurrentFragment(String tag);
    void navigateToFragment(String tag);
    void setUserName(String userName,String  tag);

    void displayImage(Uri uri);
    void passSortedList(List<PatientModel> patientModelList);

    boolean isImaggeIconVisible(boolean isVisible);
}
