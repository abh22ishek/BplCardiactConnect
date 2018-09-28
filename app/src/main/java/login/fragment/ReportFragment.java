package login.fragment;

import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;

public class ReportFragment extends Fragment {

    LoginActivityListner loginActivityListner;
    private ArrayAdapter<CharSequence> gridColorA,gridTypeA,reportDetailA,ecgFormatA,
            ecgTraceagainA,ecgTraceFilterA,traceSequenceA,
            noOfRhyTracesA,rhyLead1A,rhyLead2A,traceDarknessA;

    Spinner gridColor,gridType,reportDetail,ecgFormat,ecgTraceagain,
            ecgTraceFilter,traceSequence,noOfRhyTraces,rhyLead1,rhyLead2,
            traceDarkness;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.report_frag,container,false);

        gridColor=view.findViewById(R.id.gridColor);
        gridType=view.findViewById(R.id.gridTypeSpinner);
        reportDetail=view.findViewById(R.id.reportDetailSpinner);
        ecgFormat=view.findViewById(R.id.ecgFormatSpinner);
        ecgTraceagain=view.findViewById(R.id.ecgTraceGainSpinner);
        ecgTraceFilter=view.findViewById(R.id.ecgTraceFilterSpinner);
        traceSequence=view.findViewById(R.id.ecgTraceSeqSpinner);
        noOfRhyTraces=view.findViewById(R.id.noRhyTracesSpinner);
        rhyLead1=view.findViewById(R.id.rhyLead1);
        rhyLead2=view.findViewById(R.id.rhyLead2Spinner);
        traceDarkness=view.findViewById(R.id.traceDarknessSpinner);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.REPORT_FRAGMENT);

        gridColorA = ArrayAdapter.createFromResource(getActivity(),
                R.array.color_picker, android.R.layout.simple_spinner_item);

        gridColorA.setDropDownViewResource(R.layout.spinner_item);
        gridColor.setAdapter(gridColorA);



        gridColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                ClassConstants.GRID_COLOR= (String) adapterView.getItemAtPosition(i);

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridTypeA = ArrayAdapter.createFromResource(getActivity(),
                R.array.grid_type, android.R.layout.simple_spinner_item);



        gridTypeA.setDropDownViewResource(R.layout.spinner_item);
        gridType.setAdapter(gridTypeA);
        gridType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                ClassConstants.GRID_TYPE= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reportDetailA = ArrayAdapter.createFromResource(getActivity(),
                R.array.report_details, android.R.layout.simple_spinner_item);
        reportDetailA.setDropDownViewResource(R.layout.spinner_item);

        reportDetail.setAdapter(reportDetailA);


        ecgFormatA = ArrayAdapter.createFromResource(getActivity(),
                R.array.ecg_format, android.R.layout.simple_spinner_item);
        ecgFormatA.setDropDownViewResource(R.layout.spinner_item);

        ecgFormat.setAdapter(ecgFormatA);
        ecgFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                ClassConstants.ECG_FORMAT= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ecgTraceagainA = ArrayAdapter.createFromResource(getActivity(),
                R.array.ecg_trace_gain, android.R.layout.simple_spinner_item);
        ecgTraceagainA.setDropDownViewResource(R.layout.spinner_item);
        ecgTraceagain.setAdapter(ecgTraceagainA);



        ecgTraceFilterA = ArrayAdapter.createFromResource(getActivity(),
                R.array.ecg_trace_filter, android.R.layout.simple_spinner_item);
        ecgTraceFilterA.setDropDownViewResource(R.layout.spinner_item);
        ecgTraceFilter.setAdapter(ecgTraceFilterA);



        traceSequenceA = ArrayAdapter.createFromResource(getActivity(),
                R.array.trace_seq, android.R.layout.simple_spinner_item);
        traceSequenceA.setDropDownViewResource(R.layout.spinner_item);
        traceSequence.setAdapter(traceSequenceA);


        noOfRhyTracesA = ArrayAdapter.createFromResource(getActivity(),
                R.array.no_of_rhy, android.R.layout.simple_spinner_item);
        noOfRhyTracesA.setDropDownViewResource(R.layout.spinner_item);
        noOfRhyTraces.setAdapter(noOfRhyTracesA);


        rhyLead2A = ArrayAdapter.createFromResource(getActivity(),
                R.array.rhy_lead_1, android.R.layout.simple_spinner_item);
        rhyLead2A.setDropDownViewResource(R.layout.spinner_item);
        rhyLead2.setAdapter(rhyLead2A);


        rhyLead1A = ArrayAdapter.createFromResource(getActivity(),
                R.array.rhy_lead_1, android.R.layout.simple_spinner_item);
        rhyLead1A.setDropDownViewResource(R.layout.spinner_item);
        rhyLead1.setAdapter(rhyLead1A);

        traceDarknessA = ArrayAdapter.createFromResource(getActivity(),
                R.array.trace_darkness, android.R.layout.simple_spinner_item);
        traceDarknessA.setDropDownViewResource(R.layout.spinner_item);
        traceDarkness.setAdapter(traceDarknessA);



    }
}
