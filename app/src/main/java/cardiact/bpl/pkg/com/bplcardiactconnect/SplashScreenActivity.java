package cardiact.bpl.pkg.com.bplcardiactconnect;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

import logger.*;

public class SplashScreenActivity extends Activity {


    Handler mHandler;

    private final  String TAG=SplashScreenActivity.class.getSimpleName();

    private static final int splash_timeout=4000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splash_screen);

    }




    @Override
    protected void onResume() {
        super.onResume();


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


    }
}