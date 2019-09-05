package login.fragment;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import patient.list.*;
import usb.*;

@SuppressWarnings("SpellCheckingInspection")


public class PatientMenuTrackFragment extends Fragment {

    private LoginActivityListner loginActivityListner;
    private RelativeLayout archivedRec,realTimeEcgMeasurement,importRecord;
    private  Dialog mDialog;
    String hexstring="0x50 0x00 0x08 0x13 0x00 0xFE 0x68 0x61";
    UsbService usbService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view= inflater.inflate(R.layout.pat_menu_track,container,false);
        archivedRec=view.findViewById(R.id.archivedRec);
        realTimeEcgMeasurement=view.findViewById(R.id.realTimeEcgMeasurement);
        importRecord=view.findViewById(R.id.importRec);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        archivedRec.setOnClickListener(
                view -> onPatientList());


        realTimeEcgMeasurement.setOnClickListener(view -> ExistingorNewPatient(getActivity()));
        importRecord.setOnClickListener(view -> {

            byte [] writeBuffer= HexData.stringTobytes(hexstring);
            if (usbService != null) {
                usbService.write(writeBuffer);

            }

        });

    }




    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;

        }
    };




    private void onPatientList()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientListFragment patientListFragment = new PatientListFragment();
       // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        Bundle bundle=new Bundle();
        bundle.putString(Constants.SORT_BY,"");
        patientListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,patientListFragment,ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }







    private void onPatientProfile()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientProfileFragment patientProfileFragment = new PatientProfileFragment();
        // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        fragmentTransaction.replace(R.id.fragmentContainer,patientProfileFragment,
                ClassConstants.PATIENT_PROFILE_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_PROFILE_FRAGMENT);
        fragmentTransaction.commit();
    }


    private void onPatientListq()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity().getSupportFragmentManager());
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientListFragment patientListFragment = new PatientListFragment();
        // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        Bundle bundle=new Bundle();
        bundle.putString(Constants.SORT_BY,"");
        patientListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,patientListFragment,ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }

    private void onExistingPatient()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ExistingPatientFragment existingPatientFragment = new ExistingPatientFragment();
        // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        fragmentTransaction.replace(R.id.fragmentContainer,existingPatientFragment,
                ClassConstants.EXISTING_PATIENT_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.EXISTING_PATIENT_FRAGMENT);
        fragmentTransaction.commit();
    }


    private void ExistingorNewPatient(final Context context)
    {

        final List<String>   userList = new ArrayList<>();


        userList.add(getString(R.string.new_user));
        userList.add(getString(R.string.existing_user));



        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, userList);
        if (mDialog == null) {
            mDialog = new Dialog(context);
        }


        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.custom_dialog_list);

        final ListView content = mDialog.findViewById(R.id.list_content);
        final TextView header = mDialog.findViewById(R.id.header);

        content.setAdapter(adapter);
        header.setText(getResources().getString(R.string.selection));
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals(getString(R.string.new_user)))  //
                {
                    onPatientProfile();
                    }

                    else{
                    onExistingPatient();
                    }

                    mDialog.dismiss();
            }

        });

        mDialog.show();
    }

}
