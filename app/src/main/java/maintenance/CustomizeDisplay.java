package maintenance;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;

public class CustomizeDisplay extends Fragment {

    Spinner displayFormat,showGrid,ecgTraceSpeed,ecgTraceGain,ecgTraceFilter,traceSequence;
    private ArrayAdapter<CharSequence>  displayFormatA,showGridA,ecgTraceSpeedA,ecgTraceGainA,ecgTraceFilterA,
            traceSequenceA;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.customize_display,container,false);
        displayFormat=view.findViewById(R.id.displayFormat);
        showGrid=view.findViewById(R.id.showGrid);
        ecgTraceSpeed=view.findViewById(R.id.ecgTraceSpeed);
        ecgTraceGain=view.findViewById(R.id.ecgTraceGain);
        ecgTraceFilter=view.findViewById(R.id.ecgTraceFilterSpinner);
        traceSequence=view.findViewById(R.id.traceSequenceSpinner);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displayFormatA = ArrayAdapter.createFromResource(getActivity(), R.array.display_format, android.R.layout.simple_spinner_item);

        displayFormatA.setDropDownViewResource(R.layout.spinner_item);
        displayFormat.setAdapter(displayFormatA);


        showGridA = ArrayAdapter.createFromResource(getActivity(), R.array.grid_type, android.R.layout.simple_spinner_item);

        showGridA.setDropDownViewResource(R.layout.spinner_item);
        showGrid.setAdapter(showGridA);


        ecgTraceSpeedA = ArrayAdapter.createFromResource(getActivity(), R.array.ecg_trace_speed, android.R.layout.simple_spinner_item);

        ecgTraceSpeedA.setDropDownViewResource(R.layout.spinner_item);
        ecgTraceSpeed.setAdapter(ecgTraceSpeedA);


        ecgTraceGainA = ArrayAdapter.createFromResource(getActivity(), R.array.ecg_trace_gain, android.R.layout.simple_spinner_item);

        ecgTraceGainA.setDropDownViewResource(R.layout.spinner_item);
        ecgTraceGain.setAdapter(ecgTraceGainA);

        ecgTraceFilterA = ArrayAdapter.createFromResource(getActivity(), R.array.ecg_trace_filter, android.R.layout.simple_spinner_item);

        ecgTraceFilterA.setDropDownViewResource(R.layout.spinner_item);
        ecgTraceFilter.setAdapter(ecgTraceFilterA);


        traceSequenceA = ArrayAdapter.createFromResource(getActivity(), R.array.trace_seq, android.R.layout.simple_spinner_item);

        traceSequenceA.setDropDownViewResource(R.layout.spinner_item);
        traceSequence.setAdapter(displayFormatA);





    }






}
