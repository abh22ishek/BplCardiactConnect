package login.fragment;

public interface LoginActivityListner {

    void onDataPass(String data);
    void handleMessage(int state);
    void navigateFragment(String tag);
    void setUserName(String userName);
}
