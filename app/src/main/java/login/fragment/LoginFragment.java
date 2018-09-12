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
import database.*;
import logger.*;

public class LoginFragment extends Fragment {


    LoginActivityListner loginActivityListner;
    TextView forgotPassword,signUp,user_guide;
    private String TAG=LoginFragment.class.getSimpleName();

    private TextView userId,password;


    Button login;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.LOGIN_FRAGMENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.login,container,false);
       initializeViews(view);
        return view;
    }


    private void initializeViews(View view)
    {
        try{
            forgotPassword=view.findViewById(R.id.forgotPassword);
            signUp=view.findViewById(R.id.SignUp);
            user_guide=view.findViewById(R.id.user_guide);
            login=view.findViewById(R.id.btnLogin);

            userId=view.findViewById(R.id.emailId);
            password=view.findViewById(R.id.password);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.LOGIN_FRAGMENT);


        user_guide.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {

               if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                   android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                   android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                   TermsAndConditionFragment termsAndConditionFragment = new TermsAndConditionFragment();
                   fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                   fragmentTransaction.replace(R.id.fragmentContainer,termsAndConditionFragment,ClassConstants.T_AND_C_FRAGMENT);
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

                    fragmentTransaction.replace(R.id.fragmentContainer,forgotPasswordFragment,ClassConstants.FORGOT_PASSWORD_FRAGMENT);
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

                    fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment,ClassConstants.SIGNUP_FRAGMENT);
                    fragmentTransaction.addToBackStack(ClassConstants.SIGNUP_FRAGMENT);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (userId.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.email_n_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.pwd_n_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userId.getText().toString() != "" && password.getText().toString() != "") {



                    // call the database query
                    DatabaseManager.getInstance().openDatabase();
                    if (DatabaseManager.getInstance().Login(userId.getText().toString().trim(),
                            password.getText().toString().trim())) {

                        loginActivityListner.setUserName(userId.getText().toString().trim(),ClassConstants.LOGIN_FRAGMENT);
                        navigateFragment();
                    } else {
                        Toast.makeText(getActivity(), "Invalid Password and UserId", Toast.LENGTH_SHORT).show();
                    }




                }

            }
        });

    }


    private void navigateFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment,ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.commit();


    }
}
