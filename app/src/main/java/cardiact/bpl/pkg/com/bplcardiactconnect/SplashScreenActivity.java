package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import application.*;
import constants.*;
import logger.*;

public class SplashScreenActivity extends Activity {


    Handler mHandler;
    private final  String TAG=SplashScreenActivity.class.getSimpleName();
    private static final int splash_timeout=5000;
    private TextView splashText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashText=findViewById(R.id.text);
        View decorView = getWindow().getDecorView();


        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_splash_screen);
        globalVariable = (BaseApplicationClass) getApplicationContext();

    }




    @Override
    protected void onResume() {
        super.onResume();
        showSplashScreen(mHandler);


    }


    public Handler showSplashScreen(Handler mHandler){

        if(mHandler==null){
            mHandler=new Handler();
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Logger.log(Level.DEBUG,TAG,"***On Resume() Called***");
                Intent intent=new Intent(SplashScreenActivity.this,BaseLoginActivityClass.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


            }
        }, splash_timeout);

        return mHandler;
    }

    BaseApplicationClass globalVariable;
    String mUsername;

    // get username value of shared pref


}