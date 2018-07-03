package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.content.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import application.*;
import constants.*;
import custom.view.*;
import logger.*;
import login.fragment.*;


public class BaseLoginActivityClass extends FragmentActivity implements LoginActivityListner{


    RoundedImageView UserIcon;
    android.support.v4.app.FragmentManager fragmentManager ;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    TextView appName;

    private String TAG=BaseLoginActivityClass.class.getSimpleName();

    BaseApplicationClass globalVariable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_login_activity_main);

        globalVariable = (BaseApplicationClass) getApplicationContext();


        UserIcon=findViewById(R.id.hospitalIcon);
        UserIcon.setVisibility(View.GONE);

        appName=findViewById(R.id.appName);

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        LoginFragment signUpFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment);
        fragmentTransaction.addToBackStack(ClassConstants.LOGIN_FRAGMENT);
        fragmentTransaction.commit();


        UserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

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



    }

    @Override
    public void handleMessage(int state) {

    }

    @Override
    public void navigateFragment(String tag) {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();
        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.commit();

    }

    @Override
    public void setUserName(String userName) {
        // Set the global user Name
        globalVariable.setUsername(userName);
    }

    boolean mExit;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fm=getSupportFragmentManager();
        if(fm.getBackStackEntryCount()>1) {
            fm.popBackStack();
        }else{
            if (mExit) {
                super.onBackPressed();
                this.finish();
                // this.finishAffinity(); // removes the activity from same task

            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                mExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExit = false;
                    }
                },3*1000);
            }
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();

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

    }

}
