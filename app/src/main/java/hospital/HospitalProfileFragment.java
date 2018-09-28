package hospital;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import login.fragment.*;

public class HospitalProfileFragment extends Fragment {


    LinearLayout linerarEditInitilasHospName,linerarupdateCons,linerarEditHospName,lineareditDepartmentName;
    RadioButton r1,r2,r3,r4;
    Button edit;
    String  headerText;
    String TAG="";

    LoginActivityListner loginActivityListner;
    TextView hospName;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.hosp_profile,container,false);
        linerarEditInitilasHospName=view.findViewById(R.id.linerarEditInitilasHospName);
        linerarupdateCons=view.findViewById(R.id.linerarupdateCons);
        linerarEditHospName=view.findViewById(R.id.linerarEditHospName);
        lineareditDepartmentName=view.findViewById(R.id.lineareditDepartmentName);
        hospName=view.findViewById(R.id.hospName);

        r1=view.findViewById(R.id.r1);
        r2=view.findViewById(R.id.r2);
        r3=view.findViewById(R.id.r3);
        r4=view.findViewById(R.id.r4);

        edit=view.findViewById(R.id.btnEdit);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        r1.setChecked(true);
        headerText="Enter Hospital Initials";
        linerarEditInitilasHospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(true);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);

                headerText="Enter Hospital Initials";
                TAG="";

            }
        });



        linerarupdateCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(true);
                r3.setChecked(false);
                r4.setChecked(false);

                headerText="Enter Consulting Doctor";
                TAG="";

                loginActivityListner.onDataPass(ClassConstants.HOSPITAL_PROFILE_FRAGMENT);



            }
        });


        linerarEditHospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(true);
                r4.setChecked(false);

                headerText="Enter Hospital Name";
                TAG="HOSPITAL_NAME";

            }
        });


        lineareditDepartmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(true);

                headerText="Enter Department Name";
                TAG="";


            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectOptions(getActivity(),headerText,TAG);
            }
        });


        }




        Dialog dialog;

    private void selectOptions(final Context context,String headerText,String Tag) {

        if (dialog == null) {
            dialog = new Dialog(context);
        }


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.new_custom_dialog);
        dialog.setCancelable(true);

        final EditText content = dialog.findViewById(R.id.textEdit);
        final TextView header = dialog.findViewById(R.id.header);
        final TextView textview = dialog.findViewById(R.id.textview);
        textview.setVisibility(View.GONE);
        final Button OK=dialog.findViewById(R.id.btnOk);
        header.setText(headerText);


        content.setText(hospName.getText().toString().trim());
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TAG.equalsIgnoreCase("Hospital_name"))
                {
                    hospName.setText(content.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
