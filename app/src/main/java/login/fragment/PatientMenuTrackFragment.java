package login.fragment;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import patient.list.*;

@SuppressWarnings("SpellCheckingInspection")
public class PatientMenuTrackFragment extends Fragment {

    private LoginActivityListner loginActivityListner;

    private LinearLayout archivedRec;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pat_menu_track,container,false);

        archivedRec=view.findViewById(R.id.archivedRec);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);


        archivedRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPatientList();
            }
        });


    }





    private void onPatientList()
    {
        android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientListFragment patientListFragment = new PatientListFragment();
       // fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        Bundle bundle=new Bundle();
        bundle.putString(Constants.SORT_BY,"");
        patientListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,patientListFragment);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }

}
