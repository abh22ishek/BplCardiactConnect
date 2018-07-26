package login.fragment;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;


import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;
import logger.*;

public class SignUpFragment extends Fragment {



    Button btnSignUp;
    LoginActivityListner loginActivityListner;
    private EditText email_id,password,confirmPassword;

    private EditText security_question1,security_question2,security_question3;
    SQLiteDatabase database;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On Create() of Fragment");
        loginActivityListner.onDataPass(ClassConstants.SIGNUP_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIGNUP_FRAGMENT);
        loginActivityListner.isImaggeIconVisible(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.signup,container,false);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On CreateView() of Fragment");



        email_id=  view.findViewById(R.id.email);
        password= view.findViewById(R.id.password);


        confirmPassword=view.findViewById(R.id.confirmPassword);
        security_question1=  view.findViewById(R.id.securityques1);
        security_question2=  view.findViewById(R.id.securityques2);
        security_question3=  view.findViewById(R.id.securityques3);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On onActivityCreated() of Fragment");


        loginActivityListner= (LoginActivityListner) getActivity();

        email_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    database = DatabaseManager.getInstance().openDatabase();
                    if(DatabaseManager.getInstance().IsUsernameexists(email_id.getText().toString()))
                    {
                        email_id.setError("Email id has already taken");
                        disableFields();
                    }else{
                        enableFields();
                    }
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValidFields())
                {
                    userRegistration(getActivity());
                    navigateFragment();
                  //  destroyCurrentFragment();

                    // unable to destroy current fragment android


                }

            }
        });
    }


    private void disableFields()
    {
        password.setEnabled(false);
        confirmPassword.setEnabled(false);
        security_question1.setEnabled(false);
        security_question2.setEnabled(false);
        security_question3.setEnabled(false);
        btnSignUp.setEnabled(false);
    }


    private void enableFields()
    {
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        security_question1.setEnabled(true);
        security_question2.setEnabled(true);
        security_question3.setEnabled(true);
        btnSignUp.setEnabled(true);
    }


    private void userRegistration(Context context) {

       database= DatabaseManager.getInstance().openDatabase();
        if (!DatabaseManager.getInstance().IsUsernameexists(email_id.getText().toString().trim())) {

            database.insert(FeedReaderDbHelper.TABLE_NAME_USERS, null,
                    addUsers(email_id.getText().toString().trim(), password.getText().toString().trim(),
                            security_question1.getText().toString().toLowerCase().trim()
                            , security_question2.getText().toString().toLowerCase().trim(),
                            security_question3.getText().toString().toLowerCase().trim()));
            // database.close(); Don't close it directly!

            loginActivityListner.setUserName(email_id.getText().toString().trim(),ClassConstants.SIGNUP_FRAGMENT);

            // globalVariable.setUsername(email_id.getText().toString().trim());


            Toast.makeText(context,getString(R.string.success_regist), Toast.LENGTH_SHORT).show();
        }

    }


    private void navigateFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment,ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);

        fragmentTransaction.commit();

    }




    private boolean isValidFields()
    {
        boolean b=false;
        if(email_id.getText().toString().trim().equals("")||password.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(),"Please enter the fields",Toast.LENGTH_SHORT).show();
            return b;

        }else if (!isValidEmail(email_id.getText().toString().trim()))
        {
            Toast.makeText(getActivity(),"Email id format is not correct",Toast.LENGTH_SHORT).show();
            return b;

        }


        else if(password.getText().toString().trim().length()<6)
        {
            password.setError("Password should be minimum 6 characters");
            return b;
        }
        else if(security_question1.getText().toString().trim().equals(""))
        {
            security_question1.setError("Security Question 1 cannot be empty");
            return b;
        }

        else if(security_question2.getText().toString().trim().equals(""))
        {
            security_question2.setError("Security Question 2 cannot be empty");
            return b;
        }

        else if(security_question3.getText().toString().trim().equals(""))
        {
            security_question3.setError("Security Question 3 cannot be empty");
            return b;
        }

        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {

            Toast.makeText(getActivity(),"Password should match Confirm Password",Toast.LENGTH_SHORT).show();
            return b;
        }

        b=true;
        return b;
    }


    private  final  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ContentValues addUsers(String username, String password, String security_ques1,
                                   String security_ques2, String security_ques3)
    {
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.USER_NAME, username);
        values.put(FeedReaderDbHelper.PASSWORD, password);
        values.put(FeedReaderDbHelper.SECURITY_Q_1, security_ques1);
        values.put(FeedReaderDbHelper.SECURITY_Q_2, security_ques2);
        values.put(FeedReaderDbHelper.SECURITY_Q_3, security_ques3);

        return values;

    }




}
