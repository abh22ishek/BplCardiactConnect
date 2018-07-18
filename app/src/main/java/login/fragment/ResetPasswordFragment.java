package login.fragment;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;

public class ResetPasswordFragment extends Fragment {

    private TextView newPassword, confirmNewPassword;
    private Button resetPassword;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.reset_password,container,false);
        initializeViews(view);
        return view;

    }

    private void initializeViews(View view){
        try{
            newPassword=view.findViewById(R.id.newPassword);
            confirmNewPassword=view.findViewById(R.id.confirmPassword);
            resetPassword=view.findViewById(R.id.verify);
            coordinatorLayout=view.findViewById(R.id.coordinateLayout);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

String username="";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(null!= Objects.requireNonNull(getArguments()).getString(Constants.USER_NAME)) {

            username=getArguments().getString(Constants.USER_NAME);


        }

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Logger.log(Level.INFO, ClassConstants.RESET_PASSWORD_FRAGMENT,"Username ="+username);

                if(newPassword.getText().toString().trim().equals(confirmNewPassword.getText().toString().trim()))
                {
                    // Here update the password for current user name int the database
                   DatabaseManager.getInstance().update_password(username,newPassword.getText().toString().trim());

                   Toast.makeText(getActivity(),"Password has been reset successfully",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    showAlertMessage();
                    Toast.makeText(getActivity(),"Password didn't match",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private  void showAlertMessage()
    {
        Snackbar snackbar=Snackbar.make(coordinatorLayout,getString(R.string.forget_password),Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.reset), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        newPassword.setText("");
                        confirmNewPassword.setText("");
                    }
                });


        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
