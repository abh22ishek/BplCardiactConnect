package login.fragment;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import patient.list.*;
import recyclerview.cardview.*;

public class AddHospitalProfileFragment extends Fragment {


    LoginActivityListner loginActivityListner;


    Button submit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.hosp_profile,container,false);
        submit=view.findViewById(R.id.btnSubmit);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass("hospital_icon");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                PatientListFragment patientListFragment = new PatientListFragment();
                fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

                fragmentTransaction.replace(R.id.fragmentContainer,patientListFragment);
                fragmentTransaction.addToBackStack(ClassConstants.PATIENT_LIST_FRAGMENT);
                fragmentTransaction.commit();
            }
        });
    }
}
