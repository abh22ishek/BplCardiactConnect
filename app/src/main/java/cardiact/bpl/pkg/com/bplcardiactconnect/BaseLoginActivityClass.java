package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.*;
import android.annotation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.io.*;
import java.util.*;

import application.*;
import constants.*;
import custom.view.*;
import logger.*;
import login.fragment.*;
import store.credentials.*;


public class BaseLoginActivityClass extends AppCompatActivity implements LoginActivityListner{


   private  RoundedImageView UserIcon;


   private TextView appName;
   private String TAG=BaseLoginActivityClass.class.getSimpleName();

    BaseApplicationClass globalVariable;

    private String userIconUri;



    NavigationView nv;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_login_activity_main);

        globalVariable = (BaseApplicationClass) getApplicationContext();

        drawerLayout=findViewById(R.id.drawerLayout);
        UserIcon=findViewById(R.id.hospitalIcon);
        UserIcon.setVisibility(View.GONE);

        appName=findViewById(R.id.appName);

        nv = findViewById(R.id.nv);


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);



        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.profile:
                        Toast.makeText(BaseLoginActivityClass.this, "My Profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.settings:
                        Toast.makeText(BaseLoginActivityClass.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logOut:
                        drawerLayout.closeDrawer(Gravity.LEFT);

                        logOutConfirmDialog(BaseLoginActivityClass.this);
                        break;

                    default:
                        return true;
                }


                return true;
            }
        });





        if(IsUserLoggedIn()){
            android.support.v4.app.FragmentManager fragmentManager ;
            android.support.v4.app.FragmentTransaction fragmentTransaction;
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            WelcomeUserFragment welcomeUserFragment = new WelcomeUserFragment();

            Bundle bundle=new Bundle();
            bundle.putString(Constants.USER_NAME,mUsername);
            welcomeUserFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragmentContainer,welcomeUserFragment,ClassConstants.WELCOME_USER_FRAGMENT);
            fragmentTransaction.addToBackStack(ClassConstants.WELCOME_USER_FRAGMENT);
            fragmentTransaction.commit();
        }else{
           loginUserFrag();
        }




        UserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectOptions(BaseLoginActivityClass.this);


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDataPass(String data) {


        if(data.equalsIgnoreCase(ClassConstants.SIGNUP_FRAGMENT)|| data.equalsIgnoreCase(ClassConstants.WELCOME_USER_FRAGMENT)){
            UserIcon.setVisibility(View.VISIBLE);
            appName.setVisibility(View.GONE);
        }else{
            UserIcon.setVisibility(View.GONE);
            appName.setVisibility(View.VISIBLE);
        }



        //


    }

    Fragment currentFragment=null;
    @Override
    public void OnCurrentFragment(String tag) {
        FragmentManager fragmentManager=getSupportFragmentManager();

        // Always get Current fragment
         currentFragment = fragmentManager.findFragmentByTag(tag);

        if (currentFragment.getClass().getName().equals(ClassConstants.LOGIN_FRAGMENT)) {


        }else if(currentFragment.getClass().getName().equals(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT))
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if (currentFragment.getClass().getName().equals(ClassConstants.PATIENT_LIST_FRAGMENT)) {


        }

        else if(currentFragment.getClass().getName().equals(ClassConstants.WELCOME_USER_FRAGMENT))
        {

            //this will clear the back stack and displays no animation on the screen
            fragmentManager.popBackStackImmediate(currentFragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        Logger.log(Level.DEBUG,TAG, "Get Current Fragment="+currentFragment.getClass().getName());
    }



    @Override
    public void navigateFragment(String tag) {
        android.support.v4.app.FragmentManager fragmentManager ;
        android.support.v4.app.FragmentTransaction fragmentTransaction;

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();
        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.commit();

    }


    // this method will only gets called during signUp or login
    @Override
    public void setUserName(String userName,String tag) {
        // Set the global user Name
        globalVariable.setUsername(userName);

        if(TAG.equals(ClassConstants.LOGIN_FRAGMENT)){
            return;
        }


        StoreCredentialsFile.storeSignUpCredentials(this,userName,TAG);
        StoreCredentialsFile.store_profile_image(this, userIconUri,TAG,
                globalVariable.getUsername());


    }

    @Override
    public void displayImage(Uri uri) {
        if(uri!=null)
        loadImageWithGlide(uri.toString());
        else
            UserIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.user_icon));
    }

    boolean mExit;
    private final int TIME_ELAPSE=3000;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fragmentManager=getSupportFragmentManager();
        int count=fragmentManager.getBackStackEntryCount();
        Logger.log(Level.DEBUG,TAG,"((--count no. fo Fragments-----))=" +count);
        Logger.log(Level.DEBUG,TAG,"(((Get Current Fragment in back key))="+currentFragment.getClass().getName());


      if( currentFragment.getClass().getName().equals(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT))
        {
            if (mExit) {
                super.onBackPressed();
                this.finish();

            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                mExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExit = false;
                    }
                },TIME_ELAPSE);
            }
        }else{
          if(fragmentManager.getBackStackEntryCount()>1) {
              fragmentManager.popBackStack();
          }else
              finish();
      }


      }


   //  hoy, estoy muy ocupado
// mi computadora esta muy  lento

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            userIconUri=uri.toString();

            if(uri!=null)
           loadImageWithGlide(uri.toString());

        }

        else if(requestCode==Constants.CAMERA_CODE && resultCode==RESULT_OK)
        {
            String uri;
            if(file.exists())
            {
                Log.i("**Absolute path**=", "" + file.getAbsolutePath());
                uri=file.getAbsolutePath();


                userIconUri="file://"+uri;
                loadImageWithGlide(uri);



            }else{
                userIconUri="no_image";
                UserIcon.setImageDrawable(this.getResources().getDrawable(R.drawable.user_icon));
            }


        }

    }


    private void loadImageWithGlide(String uri){

        //noinspection SpellCheckingInspection
        Glide
                .with(BaseLoginActivityClass.this)
                .load(uri)
                .override(100, 100)
                .centerCrop()// resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(UserIcon) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);

                        Logger.log(Level.DEBUG,TAG,"Glide loaded the image successfully");
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);

                        Logger.log(Level.ERROR,TAG,e.toString());
                    }
                });

    }


    List<String> options;
    Dialog dialog;
    File file;
    private void selectOptions(final Context context) {


        if (options == null) {
            options = new ArrayList<>();
            options.add(getString(R.string.def));
            options.add(getString(R.string.camera));
            options.add(getString(R.string.gallery));

        }



        ArrayAdapter<String> adapter=new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,options);
        if(dialog==null)
        {
            dialog = new Dialog(context);
        }


        dialog.getWindow().getAttributes().windowAnimations =R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_list);
        final ListView content= dialog.findViewById(R.id.list_content);
        final TextView header=  dialog.findViewById(R.id.header);


        content.setAdapter(adapter);
        header.setText(getResources().getString(R.string.ch_opt));
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getItemAtPosition(i).equals(getString(R.string.def)))
                {
                    UserIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_icon));


                }else if(adapterView.getItemAtPosition(i).equals(getString(R.string.camera)))
                {
                    selectCameraOptions();
                }else{
                    selectPicsFromGallery();

                }
                dialog.dismiss();
            }

        });

        dialog.show();
    }




    private void selectPicsFromGallery(){
        Intent intent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                Constants.SELECT_PICTURE);
    }


    private void selectCameraOptions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ActivityCompat.requestPermissions(BaseLoginActivityClass.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.CAMERA_REQUEST_CODE);


        }
        else
        {
            file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "test.jpg"+System.currentTimeMillis());
            Uri tempUri=Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,tempUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,Constants.CAMERA_CODE);
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        // Log.i("TAG", "**Grant Results**=" + grantResults[0]+" "+grantResults[1]+" "+grantRes + "request code=" + requestCode);

        for (int grantResult : grantResults) {
            Logger.log(Level.DEBUG, TAG, "grant results[]=" + grantResult);

        }
        if(requestCode==Constants.CAMERA_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED
                    ) {
                file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test.jpg"+System.currentTimeMillis());
                Uri uri=Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, Constants.CAMERA_CODE);
            }

            else if(grantResults[0]==PackageManager.PERMISSION_DENIED ||grantResults[1]==PackageManager.PERMISSION_DENIED)

            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(BaseLoginActivityClass.this,Manifest.permission.CAMERA)
                        || ActivityCompat.shouldShowRequestPermissionRationale(BaseLoginActivityClass.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Toast.makeText(BaseLoginActivityClass.this,getString(R.string.per_nec),Toast.LENGTH_SHORT).show();
                    showDialogOK("Camera and Write External Storage Permission required for this app for User Icon",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            ActivityCompat.requestPermissions(BaseLoginActivityClass.this,
                                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    Constants.CAMERA_REQUEST_CODE);
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            UserIcon.setImageDrawable(ContextCompat.getDrawable(BaseLoginActivityClass.this, R.drawable.user_icon));
                                            break;
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(this, "Go to settings And enable permissions", Toast.LENGTH_LONG)
                            .show();

                }



            }


        }




    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), okListener)
                .create()
                .show();
    }


    String mUsername;

    private boolean IsUserLoggedIn()
    {
        boolean b;

        SharedPreferences login_credentials;
        login_credentials = this.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

        mUsername=login_credentials.getString(Constants.USER_NAME, null);
        String mPwd= login_credentials.getString(Constants.PASSWORD, null);


        if(mUsername!=null)
            b=true;
        else
            b=false;

        Logger.log(Level.DEBUG, TAG, "get value stored in a shared preference s file **User ID**" + mUsername);


        return b;

    }


    public static void loggedOut(Context context)
    {
        SharedPreferences signup_credentials;
        //=SignUpActivity.this.getPreferences(Context.MODE_PRIVATE) ;
        signup_credentials=context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = signup_credentials.edit();
        editor.putString(Constants.USER_NAME,null);
        editor.putString(Constants.PASSWORD,null);
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


    private void loginUserFrag()
    {
        android.support.v4.app.FragmentManager fragmentManager ;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.fragmentContainer,loginFragment,ClassConstants.LOGIN_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.LOGIN_FRAGMENT);
        fragmentTransaction.commit();
    }


}
