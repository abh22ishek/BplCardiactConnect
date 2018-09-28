package maintenance;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;

public class MaintenanceChecksFragments extends Fragment {
    RadioButton r1,r2,r3,r4,r5;
    LinearLayout l1,l2,l3,l4,l5;
    String headerText="";
    Button startTest;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.maintenance_checks,container,false);
        r1=view.findViewById(R.id.r1);
        r2=view.findViewById(R.id.r2);
        r3=view.findViewById(R.id.r3);
        r4=view.findViewById(R.id.r4);
        r5=view.findViewById(R.id.r5);

        startTest=view.findViewById(R.id.startTest);

        l1=view.findViewById(R.id.linearFTPServerLink);
        l2=view.findViewById(R.id.linearWifi);
        l3=view.findViewById(R.id.linearBT);
        l4=view.findViewById(R.id.linearGenX);
        l5=view.findViewById(R.id.linearReset);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        r1.setChecked(true);
        headerText="Testing FTP Server Link ...";
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                r1.setChecked(true);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                r5.setChecked(false);

                headerText="Testing FTP Server Link ...";



            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(true);
                r3.setChecked(false);
                r4.setChecked(false);
                r5.setChecked(false);

                headerText="Testing Wifi Server Link ...";

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(true);
                r4.setChecked(false);
                r5.setChecked(false);

                headerText="Testing BT Server Link ...";


            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(true);
                r5.setChecked(false);
                headerText="Testing GenX ...";


            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                r5.setChecked(true);

                headerText="Reset Auto Id ...";


            }
        });


        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(getActivity(),headerText);

            }
        });

    }




    Dialog dialog;

    private void showDialog(final Context context,String headerText) {

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
        textview.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        final Button OK=dialog.findViewById(R.id.btnOk);

        header.setText(headerText);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
