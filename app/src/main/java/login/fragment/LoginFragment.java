package login.fragment;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import logger.*;

public class LoginFragment extends Fragment {


    LoginActivityListner loginActivityListner;
    TextView forgotPassword,signUp,user_guide;
    private String TAG=LoginFragment.class.getSimpleName();

    Button login;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.login,container,false);
        forgotPassword=view.findViewById(R.id.forgotPassword);
        signUp=view.findViewById(R.id.SignUp);
        user_guide=view.findViewById(R.id.user_guide);
        login=view.findViewById(R.id.btnLogin);
        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass("");
       user_guide.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {

               if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                   android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                   android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                   TermsAndConditionFragment termsAndConditionFragment = new TermsAndConditionFragment();
                   fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                   fragmentTransaction.replace(R.id.fragmentContainer,termsAndConditionFragment);
                   fragmentTransaction.addToBackStack(ClassConstants.T_AND_C_FRAGMENT);
                   fragmentTransaction.commit();
               }
               return true;
           }
       });



        forgotPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Logger.log(Log.DEBUG,TAG,"On touch () gets called");

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){

                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
                    fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

                    fragmentTransaction.replace(R.id.fragmentContainer,forgotPasswordFragment);
                    fragmentTransaction.addToBackStack(ClassConstants.FORGOT_PASSWORD_FRAGMENT);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });


        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){

                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    SignUpFragment signUpFragment = new SignUpFragment();
                    fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

                    fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment);
                    fragmentTransaction.addToBackStack(ClassConstants.SIGNUP_FRAGMENT);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                WelcomeUserFragment welcomeUserFragment = new WelcomeUserFragment();
                fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                fragmentTransaction.replace(R.id.fragmentContainer,welcomeUserFragment);
                fragmentTransaction.addToBackStack(ClassConstants.WELCOME_USER_FRAGMENT);
                fragmentTransaction.commit();
            }
        });

    }
}
