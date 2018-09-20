package login.fragment;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;

public class ReportFragment extends Fragment {

    LoginActivityListner loginActivityListner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.report_frag,container,false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.REPORT_FRAGMENT);
    }
}
