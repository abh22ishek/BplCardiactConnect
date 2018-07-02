package login.fragment;

import android.app.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;

public class ForgotPasswordFragment extends Fragment {

    TextView ForgotPassword;

    Button verify;


    LoginActivityListner loginActivityListner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.forgot_passw,container,false);
        verify=view.findViewById(R.id.verify);
        return view;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner= (LoginActivityListner) getActivity();
        loginActivityListner.onDataPass("");

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                fragmentTransaction.replace(R.id.fragmentContainer,resetPasswordFragment);
                fragmentTransaction.addToBackStack(ClassConstants.RESET_PASSWORD_FRAGMENT);
                fragmentTransaction.commit();
            }
        });
    }
}
