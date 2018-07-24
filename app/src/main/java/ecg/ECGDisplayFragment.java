package ecg;

import android.content.*;
import android.content.res.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import custom.view.*;
import gennx.model.*;
import login.fragment.*;

public class ECGDisplayFragment extends Fragment {

    EcgGraphView ecgGraphView;
    LoginActivityListner loginActivityListner;

    List<EcgLEadModel> ecgLEadModelList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.view_ecg,container,false);
        ecgGraphView=view.findViewById(R.id.ecgGraphView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass(ClassConstants.ECG_DISPALY_FRAGMENT);

       ecgLEadModelList= (List<EcgLEadModel>) getArguments().getSerializable(Constants.CUSTOM_DATA);
       ecgGraphView.setList(ecgLEadModelList.get(0).getEcgLead1(),ecgLEadModelList.get(0).getEcgLead2(),
               ecgLEadModelList.get(0).getEcgLeadV1(),ecgLEadModelList.get(0).getEcgLeadV2(),ecgLEadModelList.get(0).getEcgLeadV3(),
               ecgLEadModelList.get(0).getEcgLeadV4(),ecgLEadModelList.get(0).getEcgLeadV5(),ecgLEadModelList.get(0).getEcgLeadV6());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }






}
