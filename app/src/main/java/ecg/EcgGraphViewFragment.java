package ecg;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import logger.*;
import login.fragment.*;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class EcgGraphViewFragment extends Fragment {


    LoginActivityListner loginActivityListner;

    RealTimeEcgView realTimeEcgView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginActivityListner = (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ecg_graph_view, container, false);
        realTimeEcgView = view.findViewById(R.id.realTimeEcgView);
        final RealTimeEcgView v = new RealTimeEcgView(getActivity());
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MATCH_PARENT,
                MATCH_PARENT);
        v.setLayoutParams(lp);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.OnCurrentFragment(ClassConstants.ECG_GRAPH_VIEW_FRAGMENT);





        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(3000);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.log(Level.DEBUG,"---Thread---","Run Method()");

                                for(int i=0;i<LeadData.LEADArray.length;i++){
                                    realTimeEcgView.drawpoints(LeadData.LEADArray[i]);

                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };


        t.start();


    }


}

