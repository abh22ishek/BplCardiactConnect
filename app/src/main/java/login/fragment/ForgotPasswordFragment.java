package login.fragment;


import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;

public class ForgotPasswordFragment extends Fragment {

   private TextView userId;

   private Button verify,submit;
    private EditText security_question1,security_question2,security_question3;


   private LoginActivityListner loginActivityListner;

    private LinearLayout login,loginLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.forgot_passw,container,false);
       try {
           verify=view.findViewById(R.id.verify);
           submit=view.findViewById(R.id.submit);
           security_question1=  view.findViewById(R.id.securityques1);
           security_question2= view.findViewById(R.id.securityques2);
           security_question3=  view.findViewById(R.id.securityques3);
           login=view.findViewById(R.id.login);
           loginLayout=view.findViewById(R.id.loginLayout);
           userId=view.findViewById(R.id.userId);

       }catch (Exception e){
           e.printStackTrace();
       }

        return view;


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginLayout.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);


        if(loginActivityListner!=null)
        loginActivityListner.onDataPass(ClassConstants.FORGOT_PASSWORD_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.FORGOT_PASSWORD_FRAGMENT);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Please"+getString(R.string.hint_ent_user_id),Toast.LENGTH_SHORT).show();
                    return;
                }


                DatabaseManager.getInstance().openDatabase();

                if(DatabaseManager.getInstance().IsUsernameexists(userId.getText().toString().trim()))
                {
                   loginLayout.setVisibility(View.VISIBLE);
                   login.setVisibility(View.GONE);
                }else
                    userId.setError(getString(R.string.email_n_reg));


            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // match the user Id with all 3 security questions

                if(isValidSecurityQuestion()){
                    navigateFragment(userId.getText().toString().trim());
                }else
                {
                    Toast.makeText(getActivity(),"Sorry,wrong answer",Toast.LENGTH_LONG).show();

                }

            }
        });
    }




    // validate security questions

    private boolean isValidSecurityQuestion()
    {
        boolean b=false;
        if(security_question1.getText().toString().trim().equals(""))
        {
            security_question1.setError(getResources().getString(R.string.field_empty));
            return b;
        }

        if(security_question1.getText().toString().trim().equals(""))
        {
            security_question2.setError(getResources().getString(R.string.field_empty));
            return b;
        }
        if(security_question1.getText().toString().trim().equals(""))
        {
            security_question3.setError(getResources().getString(R.string.field_empty));
            return b;
        }

        if(DatabaseManager.getInstance().IssecurityQvalid(userId.getText().toString().trim(),security_question1.getText().toString().
                        toLowerCase().trim(),security_question2.getText().toString().toLowerCase().trim(),
                security_question3.getText().toString().toLowerCase().trim()))
        {
            b=true;

        }


        return b;
    }


    private void navigateFragment(String username)
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constants.USER_NAME,username);
        resetPasswordFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,resetPasswordFragment);
        fragmentTransaction.addToBackStack(ClassConstants.RESET_PASSWORD_FRAGMENT);
        fragmentTransaction.commit();
    }
}
