package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.*;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

import java.io.*;
import java.util.*;

import application.*;
import constants.*;
import custom.view.*;
import ecg.*;
import gennx.model.*;
import hospital.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.schedulers.*;
import logger.*;
import login.fragment.*;
import maintenance.*;
import model.*;
import patient.list.*;
import store.credentials.*;
import usb.*;
import utility.*;


public class BaseLoginActivityClass extends AppCompatActivity implements LoginActivityListner,ListR {



//    private TextView appName;
    private String TAG = BaseLoginActivityClass.class.getSimpleName();

    BaseApplicationClass globalVariable;
    ViewFlipper viewFlipper;
    List<DoctorModel>  DocsList;

    NavigationView nv;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    DrawerLayout drawerLayout;
    LoginActivityListner loginActivityListner;

   // RelativeLayout baseLayout;
    ImageView navHeaderIcon;


    List<EcgLEadModel> EcgLeads;

    Observable<List<EcgLEadModel>> mObservable;
    Menu mMenu;
    private ListR listR;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        globalVariable = (BaseApplicationClass) getApplicationContext();
        viewFlipper = findViewById(R.id.linearParams);

            doctorSets=new LinkedHashSet<>();


        drawerLayout = findViewById(R.id.drawerLayout);



       /* UserIcon = findViewById(R.id.hospitalIcon1);
        UserIcon.setVisibility(View.GONE);
*/
       // appName = findViewById(R.id.appName);


        nv = findViewById(R.id.nv);


        View header = nv.getHeaderView(0);
        navHeaderIcon = header.findViewById(R.id.navHeaderIcon);

      //  baseLayout = findViewById(R.id.relativeParams);

        loginActivityListner = this;
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        listR=BaseLoginActivityClass.this;

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        //----


/*

        appName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.showNext();
                getCurrentViewFlipperID(BaseLoginActivityClass.this);


            }
        });
*/

        nv.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.hospitalProfile:
                    drawerLayout.closeDrawer(Gravity.START);

                    callFragments(ClassConstants.HOSPITAL_PROFILE_FRAGMENT,
                            new HospitalProfileFragment(),ClassConstants.HOSPITAL_PROFILE_FRAGMENT,null);

                    getSupportActionBar().setTitle(getString(R.string.hosp_profile));
                  //  Toast.makeText(BaseLoginActivityClass.this, "Hosp Profile", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.customizeDisplay:
                    drawerLayout.closeDrawer(Gravity.START);
                    loadCustomizeDispalyFragments();
                    callFragments(ClassConstants.CUSTOMIZE_DISPLAY_FRAGMENT,
                            new CustomizeDisplay(),ClassConstants.CUSTOMIZE_DISPLAY_FRAGMENT,null);
                    getSupportActionBar().setTitle(getString(R.string.cust_display));


                  //  Toast.makeText(BaseLoginActivityClass.this, "Custom Display", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logOut:
                    drawerLayout.closeDrawer(Gravity.START);
                    logOutConfirmDialog(BaseLoginActivityClass.this);
                    break;

                case R.id.navHeaderIcon:
                  //  Toast.makeText(BaseLoginActivityClass.this, "Settings", Toast.LENGTH_SHORT).show();
                    break;


                case R.id.reportSettings:
                    drawerLayout.closeDrawer(Gravity.START);
                    callFragments(ClassConstants.REPORT_FRAGMENT,
                            new ReportFragment(),ClassConstants.REPORT_FRAGMENT,null);
                    getSupportActionBar().setTitle(getString(R.string.report_settings));
                    break;

                case R.id.configFtp:
                    drawerLayout.closeDrawer(Gravity.START);
                    callFragments(ClassConstants.CONFIGURE_FTP_SERVER_FRAGMENT,
                            new ConfigureFTPServer(),ClassConstants.CONFIGURE_FTP_SERVER_FRAGMENT,null);
                    getSupportActionBar().setTitle(getString(R.string.config_ftp));
                    break;


                case R.id.maintenanceChecks:
                    drawerLayout.closeDrawer(Gravity.START);

                    callFragments(ClassConstants.MAINTENANCE_CHECKS_FRAGMENT,new MaintenanceChecksFragments(),ClassConstants.MAINTENANCE_CHECKS_FRAGMENT,null);
                    getSupportActionBar().setTitle(getString(R.string.maintain_chks));
                    break;


                case R.id.managePatients:
                    drawerLayout.closeDrawer(Gravity.START);
                    getSupportActionBar().setTitle(getString(R.string.manage_pat));
                    onPatientList();
                    break;



                case R.id.about:
                    drawerLayout.closeDrawer(Gravity.START);
                   // reportSettingsFragment();
                  //  break;

                default:
                    return true;
            }


            return true;
        });


        if (IsUserLoggedIn()) {
            welcomeUserFrag();
        } else {
            loginUserFrag();
        }


    /*    UserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptions(BaseLoginActivityClass.this);
            }
        });
*/
        DocsList=new ArrayList<>();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() |   android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_usb_black_24dp);
        android.support.v7.app.ActionBar.LayoutParams layoutParams = new   android.support.v7.app.ActionBar.LayoutParams(
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.END
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 20;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFragments(ClassConstants.USB_CONNECTION_FARGMENT,
                        new UsbConnectionFragment(),ClassConstants.USB_CONNECTION_FARGMENT,null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        mMenu=menu;
            inflater.inflate(R.menu.sortby, menu);
        MenuItem item1 = mMenu.findItem(R.id.age);
        MenuItem item2 = mMenu.findItem(R.id.name);
        MenuItem item3 = mMenu.findItem(R.id.Id);

        MenuItem item4 = mMenu.findItem(R.id.AddDoctor);
        MenuItem item5 = mMenu.findItem(R.id.SelectAll);
        MenuItem item6 = mMenu.findItem(R.id.DeleteMarked);


            SpannableString s = new SpannableString(getString(R.string.sort_age));
            s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
            item1.setTitle(s);

        SpannableString s1 = new SpannableString(getString(R.string.sort_name));
        s1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s1.length(), 0);
        item2.setTitle(s1);


        SpannableString s3 = new SpannableString(getString(R.string.sort_id));
        s3.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s3.length(), 0);
        item3.setTitle(s3);

        SpannableString s4 = new SpannableString(getString(R.string.add_doctor));
        s4.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s4.length(), 0);
        item4.setTitle(s4);
        SpannableString s5 = new SpannableString(getString(R.string.select_all));
        s5.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s5.length(), 0);
        item5.setTitle(s5);
        SpannableString s6 = new SpannableString(getString(R.string.delete_marked));
        s6.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s6.length(), 0);
        item6.setTitle(s6);





        if (item1 != null){
            item1.setVisible(true);
        }

        if (item2 != null){
            item2.setVisible(true);
        }
        if (item3 != null){
            item3.setVisible(true);
        }

        if (item4 != null){
            item4.setVisible(false);
        }
        if (item5 != null){
            item5.setVisible(false);
        } if (item6 != null){
            item6.setVisible(false);
        }


        return true;
    }


    public void loadAnimations1(ViewFlipper viewFlipper) {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.age:
                reloadPatientListFragment(Constants.SORT_BY_AGE);
                break;

            case R.id.name:
                reloadPatientListFragment(Constants.SORT_BY_NAME);

                break;


            case R.id.Id:
                reloadPatientListFragment(Constants.SORT_BY_ID);

                break;


            case R.id.AddDoctor:

                addDoctors(this,"Add Doctor Name");
                break;

            case R.id.SelectAll:

                reloadHospitalDocsFragemnt(DocsList,true);

                break;


            case R.id.DeleteMarked:
                Utility.clearHospitalDocs(BaseLoginActivityClass.this);
                reloadHospitalDocsFragemnt(DocsList,true);
                break;
            default:
                break;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);


    }


    private void callEcg() {
        // EcgLeads= Utility.readfile("Cardiart","Ecg.txt");

        mObservable = Observable.just(Utility.readfile("Cardiart", "Ecg.txt")).
                subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        mObservable.subscribe(mObserver);


        //  EcgLeads= Utility.readfile("","");
        // reloadEcgDisplayFragment(EcgLeads);
    }




    Observer<List<EcgLEadModel>> mObserver = new Observer<List<EcgLEadModel>>() {
        @Override
        public void onSubscribe(Disposable d) {

            Logger.log(Level.DEBUG, TAG, "-On Subscribe-()");
        }

        @Override
        public void onNext(List<EcgLEadModel> ecgLEadModels) {
            Logger.log(Level.DEBUG, TAG, "-On Next-()");

            EcgLeads = ecgLEadModels;
            reloadEcgDisplayFragment(EcgLeads);
        }

        @Override
        public void onError(Throwable e) {
            Logger.log(Level.DEBUG, TAG, "-On Error-()");

        }

        @Override
        public void onComplete() {

            Logger.log(Level.DEBUG, TAG, "-On Complete-()");


        }
    };


    @Override
    public void onDataPass(String data) {


        if (data.equalsIgnoreCase(ClassConstants.SIGNUP_FRAGMENT) ||
                data.equalsIgnoreCase(ClassConstants.WELCOME_USER_FRAGMENT)) {
          //  UserIcon.setVisibility(View.VISIBLE);
         //   appName.setVisibility(View.GONE);

        } else {
          //  UserIcon.setVisibility(View.GONE);
          //  appName.setVisibility(View.VISIBLE);
        }

       // appName.setVisibility(View.VISIBLE);
       // UserIcon.setImageDrawable(ContextCompat.getDrawable(BaseLoginActivityClass.this, R.drawable.user_icon));


        if (data.equals(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT)) {
          //  UserIcon.setVisibility(View.GONE);
            viewFlipper.setVisibility(View.VISIBLE);


            ShowViewFlipper(viewFlipper, BaseLoginActivityClass.this);


        }
        //

        if (data.equals(ClassConstants.PATIENT_LIST_FRAGMENT)) {
            //baseLayout.setVisibility(View.GONE);
            getSupportActionBar().show();
            return;
        }

        if (data.equals(ClassConstants.ECG_DISPALY_FRAGMENT)) {
          //  baseLayout.setVisibility(View.GONE);
            // getSupportActionBar().hide();
            return;
        }


        if (data.equals(Constants.SHOW_ECG_DATA)) {
            InsertData task = new InsertData();
            task.execute();
            return;
        }

        if (data.equals(ClassConstants.LOGIN_FRAGMENT)) {
           // baseLayout.setVisibility(View.GONE);
            return;
        }


        if(data.equalsIgnoreCase(ClassConstants.ECG_GRAPH_FRAGMENT))
        {
           // baseLayout.setVisibility(View.GONE);
            return;
        }

        if (data.equals("back")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate();

        }

        if(data.equalsIgnoreCase("patient_ecg")){


            realtimeEcgFrag();
            return;
        }


        if(data.equalsIgnoreCase(ClassConstants.HOSPITAL_PROFILE_FRAGMENT))
        {
          //  baseLayout.setVisibility(View.GONE);
            Bundle bundle=new Bundle();

            bundle.putParcelableArrayList(Constants.USER_NAME, (ArrayList<? extends Parcelable>) DocsList);
            bundle.putBoolean("checked",false);
            callFragments(ClassConstants.HOSPITAL_PROFILE_FRAGMENT, new AddHospitalDoctors(),
                    ClassConstants.HOSPITAL_PROFILE_FRAGMENT,bundle);
            return;
        }


        if(data.equalsIgnoreCase(ClassConstants.ADD_HOSPITAL_DOCTORS_FRAGMENT))
        {
          //  baseLayout.setVisibility(View.GONE);
            getSupportActionBar().show();
            MenuItem item1 = mMenu.findItem(R.id.age);
            MenuItem item2 = mMenu.findItem(R.id.name);
            MenuItem item3 = mMenu.findItem(R.id.Id);

            MenuItem item4 = mMenu.findItem(R.id.AddDoctor);
            MenuItem item5 = mMenu.findItem(R.id.SelectAll);
            MenuItem item6 = mMenu.findItem(R.id.DeleteMarked);

            if (item1 != null){
                item1.setVisible(false);
            }

            if (item2 != null){
                item2.setVisible(false);
            }
            if (item3 != null){
                item3.setVisible(false);
            }

            if (item4 != null){
                item4.setVisible(true);
            }
            if (item5 != null){
                item5.setVisible(true);
            } if (item6 != null){
            item6.setVisible(true);
        }


            return;
        }
        //baseLayout.setVisibility(View.VISIBLE);

    }

    Fragment currentFragment = null;

    private void realtimeEcgFrag() {


        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        EcgGraphViewFragment ecgGraphViewFragment = new EcgGraphViewFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, ecgGraphViewFragment, ClassConstants.ECG_GRAPH_VIEW_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.ECG_GRAPH_VIEW_FRAGMENT);

        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    @Override
    public void OnCurrentFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Always get Current fragment
        currentFragment = fragmentManager.findFragmentByTag(tag);
      //  Logger.log(Level.DEBUG, TAG, "--Navigation Image URI--" + NavigationUserIconUri);
        int count = fragmentManager.getBackStackEntryCount();
        Logger.log(Level.DEBUG, TAG, "Back stack frag count in On curreent frag()=" + count);

        if (currentFragment.getClass().getName().equals(ClassConstants.SIGNUP_FRAGMENT)) {
         //   baseLayout.setVisibility(View.VISIBLE);
            return;

        }
        else if (currentFragment.getClass().getName().equals(ClassConstants.WELCOME_USER_FRAGMENT)) {
           getSupportActionBar().hide();
           return;
        }

        else if (currentFragment.getClass().getName().equals(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT)) {
           // baseLayout.setVisibility(View.VISIBLE);
            getSupportActionBar().hide();
            return;
        }


        else if (currentFragment.getClass().getName().equals(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           /* if (NavigationUserIconUri != null) {
                loadImageWithGlide(NavigationUserIconUri.toString(), navHeaderIcon);

            }*/


        } else if (currentFragment.getClass().getName().equals(ClassConstants.PATIENT_LIST_FRAGMENT)) {
            //baseLayout.setVisibility(View.GONE);
            return;
        } else if (currentFragment.getClass().getName().equals(ClassConstants.WELCOME_USER_FRAGMENT)) {

            //this will clear the back stack and displays no animation on the screen

            Logger.log(Level.DEBUG, TAG, "---Back stack entry count after POP BACK STACK IMMEDIATE---" + count);
        } else if (currentFragment.getClass().getName().equals(ClassConstants.ECG_DISPALY_FRAGMENT)) {


        } else if (currentFragment.getClass().getName().equals(ClassConstants.PATIENT_PROFILE_FRAGMENT)) {

           // baseLayout.setVisibility(View.GONE);
            return;
        } else if (currentFragment.getClass().getName().equals(ClassConstants.EXISTING_PATIENT_FRAGMENT)) {
          //  baseLayout.setVisibility(View.GONE);
            getSupportActionBar().show();
            return;
        }


        else if (currentFragment.getClass().getName().equals(ClassConstants.ECG_GRAPH_VIEW_FRAGMENT)) {
          //  baseLayout.setVisibility(View.GONE);
         getSupportActionBar().hide();
            return;
        }




        Logger.log(Level.DEBUG, TAG, "Get Current Fragment=" + currentFragment.getClass().getName());
        getSupportActionBar().show();
       // baseLayout.setVisibility(View.GONE);
    }


    @Override
    public void navigateToFragment(String tag) {


        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ECGDisplayFragment ecgDisplayFragment = new ECGDisplayFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, ecgDisplayFragment);
        fragmentTransaction.addToBackStack(ClassConstants.ECG_DISPALY_FRAGMENT);
        fragmentTransaction.commit();

    }


    // this method will only gets called during signUp or login
    @Override
    public void setUserName(String userName, String tag) {
        // Set the global user Name
        globalVariable.setUsername(userName);

        if (TAG.equals(ClassConstants.LOGIN_FRAGMENT)) {
            return;
        }

        Constants.Logged_User_ID = globalVariable.getUsername();


    }

    @Override
    public void displayImage(Uri uri) {
        if (uri != null) {
          //  loadImageWithGlide(uri.toString(), UserIcon);
          //  NavigationUserIconUri = uri;
        } else{

        }
            //UserIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.user_icon));
    }

    @Override
    public void passSortedList(List<PatientModel> patientModelList) {

    }

    @Override
    public boolean isImaggeIconVisible(boolean isVisible) {

        if (!isVisible){

        }

          //  UserIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.user_icon));
        return isVisible;
    }

    boolean[] booleansArr;




    @Override
    public void getSelectedUser(String data, boolean[] arrays) {
        booleansArr = new boolean[arrays.length];
        //copying one array to another
        booleansArr=Arrays.copyOf(arrays,arrays.length);

    }

    @Override
    public void OnhandlePermissions() {
       // selectOptions(BaseLoginActivityClass.this);
    }


    boolean mExit;
    private final int TIME_ELAPSE = 5000;

    @Override
    public void onBackPressed() {


        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        Logger.log(Level.DEBUG, TAG, "((--count no. fo Fragments-----))=" + count);
        Logger.log(Level.DEBUG, TAG, "(((Get Current Fragment in back key))=" + currentFragment.getClass().getName());

        String frag = currentFragment.getClass().getName();

        if (frag.equals(ClassConstants.SIGNUP_FRAGMENT) ||
                frag.equals(ClassConstants.LOGIN_FRAGMENT) || frag.equals(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT)
                || frag.equals(ClassConstants.WELCOME_USER_FRAGMENT)) {
            finish();
            return;
        }


        if (frag.equals(ClassConstants.PATIENT_LIST_FRAGMENT)) {
        /*    final Strname=ClassConstants.PATIENT_MENU_TRACK_FRAGMENT;
            fragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
            for (int i = 2; i < count; ++i) {
                fragmentManager.popBackStackImmediate();
            }
            return;
        }


        if (frag.equals(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT)) {


            if (mExit) {
                // super.onBackPressed();
                finish();
                return;

            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                mExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExit = false;
                    }
                }, TIME_ELAPSE);
            }


        } else {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                fragmentManager.popBackStack();
            } else
                //super.onBackPressed();
                finish();
        }


    }


    //  hoy, estoy muy ocupado
// mi computadora esta muy  lento

    File file;



    String mUsername;

    private boolean IsUserLoggedIn() {
        boolean b;

        SharedPreferences login_credentials;
        login_credentials = this.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

        mUsername = login_credentials.getString(Constants.USER_NAME, null);
        String mPwd = login_credentials.getString(Constants.PASSWORD, null);

        b = mUsername != null;

        Logger.log(Level.DEBUG, TAG, "get value stored in a shared preference s file **User ID**" + mUsername);

        return b;

    }


    public void loggedOut(Context context) {
        SharedPreferences signup_credentials;
        //=SignUpActivity.this.getPreferences(Context.MODE_PRIVATE) ;
        signup_credentials = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = signup_credentials.edit();
        editor.putString(Constants.USER_NAME, null);
        editor.putString(Constants.PASSWORD, null);
        editor.apply();
        Logger.log(Level.DEBUG, context.getClass().getSimpleName(), "shared preference s file all values is set to null");
    }

    android.support.v7.app.AlertDialog alertDialog;


    private void logOutConfirmDialog(final Context context) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(R.string.log_out_confirm)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        loggedOut(context);
                        loginUserFrag();
                        alertDialog.dismiss();
                    }
                });


        alertDialog = builder.create();
        //  alert.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        Logger.log(Level.DEBUG, TAG, "Alert dialog box gets called");
        alertDialog.show();

    }



    private void callFragments(String addToBackStack,Fragment fragment,String TAG,Bundle bundle)
    {
        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(bundle!=null)
        {
            fragment.setArguments(bundle);
        }
       // MaintenanceChecksFragments configureFTPServer = new MaintenanceChecksFragments();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment,TAG );
        fragmentTransaction.addToBackStack(addToBackStack);
        fragmentTransaction.commit();

    }







    private void loadCustomizeDispalyFragments()
    {
        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        CustomizeDisplay customizeDisplay = new CustomizeDisplay();
        fragmentTransaction.replace(R.id.fragmentContainer, customizeDisplay, ClassConstants.CUSTOMIZE_DISPLAY_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.CUSTOMIZE_DISPLAY_FRAGMENT);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    private void loginUserFrag() {
        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, loginFragment, ClassConstants.LOGIN_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.LOGIN_FRAGMENT);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    private void welcomeUserFrag() {
        android.support.v4.app.FragmentManager fragmentManager;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        WelcomeUserFragment welcomeUserFragment = new WelcomeUserFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_NAME, mUsername);
        welcomeUserFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainer, welcomeUserFragment, ClassConstants.WELCOME_USER_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.WELCOME_USER_FRAGMENT);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }







    private List<String> mapUserIDICon(Context context) {


        List<String> userList = new ArrayList<>();
        List<String> userIconsUri = new ArrayList<>();
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);


        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            userList.add(entry.getKey());
            userIconsUri.add(entry.getValue().toString());


        }


        return userIconsUri;

    }


    RoundedImageView roundedImageView;

    private void ShowViewFlipper(ViewFlipper viewFlipper, Context context) {
        List<String> UriList = mapUserIDICon(BaseLoginActivityClass.this);
        Uri uri;

        for (int i = 0; i < UriList.size(); i++) {
            roundedImageView = new RoundedImageView(context);
            uri = Uri.parse(UriList.get(i));
            //loadImageWithGlide(uri.toString(), roundedImageView);
            roundedImageView.setId(i);
            viewFlipper.addView(roundedImageView);

        }


    }


    public void getCurrentViewFlipperID(Context context) {

        int index = -1;
        List<String> UriList = mapUserIDICon(BaseLoginActivityClass.this);


        for (int i = 0; i < UriList.size(); i++) {

            if (i == viewFlipper.getCurrentView().getId()) {
                index = i;
            }
        }

        if (index == -1) {
            // failed to determine the right index
            Log.w("imagen1", "Could not determine the right index!");
            return;
        }

        Uri uri = Uri.parse(UriList.get(index));
        Logger.log(Level.DEBUG, TAG, "--GET URI from current View Flipper---" + uri);


        getCurrentUserIdFromCurrentViewFlipper(uri.toString(), context);

    }


    private String getCurrentUserIdFromCurrentViewFlipper(String uriAsValue, Context context) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);

        String currentUserId = "";

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            if (uriAsValue.equals(entry.getValue())) {
                currentUserId = entry.getKey();
                break;
            }


        }
        Logger.log(Level.DEBUG, TAG, "--Get current UserID mapped from Current Icons display--=" + currentUserId);
        reloadSignAsNewUserFragment(currentUserId);
        return currentUserId;
    }


    private void reloadSignAsNewUserFragment(String userId) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SignAsNewUserFragment signUpFragment = new SignAsNewUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_NAME, userId);
        signUpFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainer, signUpFragment, ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



    }







    private void reloadEcgDisplayFragment(List<EcgLEadModel> EcgLeads) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ECGDisplayFragment ecgDisplayFragment = new ECGDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CUSTOM_DATA, (Serializable) EcgLeads);
        ecgDisplayFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainer, ecgDisplayFragment, ClassConstants.ECG_DISPALY_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.ECG_DISPALY_FRAGMENT);
        fragmentTransaction.commit();



    }


    private void reloadPatientListFragment(String SortBy) {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(BaseLoginActivityClass.this).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PatientListFragment patientListFragment = new PatientListFragment();


        Bundle bundle = new Bundle();
        bundle.putString(Constants.SORT_BY, SortBy);
        patientListFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainer, patientListFragment, ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    private void reloadHospitalDocsFragemnt(List<DoctorModel> Doc,boolean mChecked) {

        android.support.v4.app.FragmentManager fragmentManager =

                Objects.requireNonNull(BaseLoginActivityClass.this).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddHospitalDoctors addHospitalDoctors = new AddHospitalDoctors();


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.USER_NAME, (ArrayList<? extends Parcelable>) Doc);
        bundle.putBoolean("checked",mChecked);
        addHospitalDoctors.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentContainer, addHospitalDoctors, ClassConstants.ADD_HOSPITAL_DOCTORS_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Logger.log(Level.INFO, TAG, "LANDSCAPE ORIENTATION");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Logger.log(Level.INFO, TAG, "PORTRAIT ORIENTATION");

        } else {
            Logger.log(Level.INFO, TAG, "UNSPECIFIED ORIENTATION");

        }
    }

    @Override
    public void getSelectedUserDoctors(String data, boolean[] arrays) {

    }


    @SuppressLint("StaticFieldLeak")
    class InsertData extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        String isloaded = "false";


        @Override
        protected String doInBackground(String... strings) {


            try {

                callEcg();
                isloaded = "true";
            } catch (Exception e) {
                isloaded = "false";
                e.printStackTrace();
            }


            return isloaded;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(BaseLoginActivityClass.this);
                progressDialog.setMessage("Please wait ......");

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

            }


        }


        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (isloaded.equalsIgnoreCase("true")) {
                    Toast.makeText(BaseLoginActivityClass.this,
                            "ECG", Toast.LENGTH_SHORT).show();


                }

            }
        }
    }




    Dialog dialog;
    private void addDoctors(final Context context,String headerText) {

        if (dialog == null) {
            dialog = new Dialog(context);
        }

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.new_custom_dialog);
        dialog.setCancelable(true);

        final EditText content = dialog.findViewById(R.id.textEdit);
        final TextView header = dialog.findViewById(R.id.header);
        final TextView textview = dialog.findViewById(R.id.textview);
        textview.setVisibility(View.GONE);
        final Button OK=dialog.findViewById(R.id.btnOk);
        header.setText(headerText);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                DoctorModel d=new DoctorModel(content.getText().toString().trim(),"");
                DocsList.add(d);
                doctorSets.add(content.getText().toString().trim());
                Utility.storeHospitalDocs(BaseLoginActivityClass.this,doctorSets);
                reloadHospitalDocsFragemnt(DocsList,false);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Set<String> doctorSets;
    private void onPatientList()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getSupportFragmentManager());
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientListFragment patientListFragment = new PatientListFragment();
        // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        Bundle bundle=new Bundle();
        bundle.putString(Constants.SORT_BY,"");
        patientListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,patientListFragment,ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }




}