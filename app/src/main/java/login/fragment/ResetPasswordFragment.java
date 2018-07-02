package login.fragment;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;

public class ResetPasswordFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.reset_password,container,false);
        return view;

    }




}
