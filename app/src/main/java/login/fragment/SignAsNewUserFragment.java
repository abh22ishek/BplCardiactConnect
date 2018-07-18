package login.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import constants.*;
import database.*;

/**
 * Created by abhishekraj on 13/07/18.
 */

public class SignAsNewUserFragment extends Fragment {



    private TextView signUpFresh;
    private EditText userId,password;

    LoginActivityListner loginActivityListner;
    ImageView proceed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.log_out ,container,false);
        signUpFresh=view.findViewById(R.id.signUpFresh);

        password=view.findViewById(R.id.password);
        userId=view.findViewById(R.id.userId);
        proceed=view.findViewById(R.id.proceed);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
        if(null!=getArguments().getString(Constants.USER_NAME)) {

           final String username=getArguments().getString(Constants.USER_NAME);
           userId.setText(username);


        }

        signUpFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigateToSignUpFreshFragment();
            }
        });



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginPass(userId,password);
            }
        });


    }


    private void navigateToSignUpFreshFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        SignUpFragment signUpFragment = new SignUpFragment();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment, ClassConstants.SIGNUP_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.SIGNUP_FRAGMENT);

        fragmentTransaction.commit();


    }




    private void loginPass(EditText userId, EditText password)
    {
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


                navigateFragment();
            } else {
                Toast.makeText(getActivity(), "Invalid Password and UserId", Toast.LENGTH_SHORT).show();
                return;
            }




        }
    }



    private void navigateFragment(){

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();

        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment, ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.commit();

    }

}
