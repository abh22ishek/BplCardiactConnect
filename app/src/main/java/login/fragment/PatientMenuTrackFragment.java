package login.fragment;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;

public class PatientMenuTrackFragment extends Fragment {

    LoginActivityListner loginActivityListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pat_menu_track,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);


    }





}
