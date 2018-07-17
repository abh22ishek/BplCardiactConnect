package login.fragment;

import android.arch.lifecycle.Lifecycle;
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

/**
 * Created by abhishekraj on 13/07/18.
 */

public class SignAsNewUserFragment extends Fragment {



    private TextView signUpFresh;

    private EditText userId;

    LoginActivityListner loginActivityListner;

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

        userId=view.findViewById(R.id.userId);
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





}
