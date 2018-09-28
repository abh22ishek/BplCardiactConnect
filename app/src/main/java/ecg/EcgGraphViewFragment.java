package ecg;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import constants.*;
import logger.*;
import login.fragment.*;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class EcgGraphViewFragment extends Fragment {


    LoginActivityListner loginActivityListner;
    GraphView grapView;

    int mCount=0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginActivityListner = (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ecg_graph_view, container,
                false);
        grapView = view.findViewById(R.id.realTimeEcgView);
        GridLabelRenderer glr = grapView.getGridLabelRenderer();
        glr.setPadding(32); //
      /*  final RealTimeEcgView v = new RealTimeEcgView(getActivity());
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MATCH_PARENT,
                MATCH_PARENT);

        v.setLayoutParams(lp);*/
        return view;

    }

    List<Float> floatList;
    boolean b;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.ECG_GRAPH_VIEW_FRAGMENT);
        b=true;

        plot_graph();



    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.log(Level.DEBUG,"----","On Pause ()---");
      //  b=false;
    }


    @Override
    public void onStop() {
        super.onStop();

        Logger.log(Level.DEBUG,"----","On Stop ()---");
    //    b=false;

    }
    double y=0;
    @Override
    public void onResume() {
        super.onResume();
        Logger.log(Level.DEBUG,"----","On Resume ()---");

        b=true;
        floatList=new ArrayList<>();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (b) {

                        sleep(50);

                        if(getActivity()==null)
                        {
                            Logger.log(Level.DEBUG,"---","Get Activity returns null");
                            return;
                        }



                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.log(Level.DEBUG,"---Thread---","Run Method()");
                            /*    mSeries2.appendData(new DataPoint(graph2LastXValue,getRandom()),
                                        true, 500);*/
                                mSeries2.appendData(new DataPoint(graph2LastXValue,getRandom()),
                                        false, 800);

                                graph2LastXValue++;
                               // grapView.getViewport().scrollToEnd();
                               /* for(int i=0;i<LeadData.LEADArray.length;i++){
                                            mCount++;

                                            floatList.add(Float.valueOf(LeadData.LEADArray[i]));


                                            if(floatList.size()==100){
                                                // realTimeEcgView.drawpoints(floatList);

                                                synchronized (this) {
                                                    for (int j = 0; j < floatList.size(); j++) {



                                                    }
                                                }

                                                Logger.log(Level.DEBUG,"--Count--",""+mCount+"--double value--"+floatList.get(i));
                                        floatList.clear();
                                        break;

                                      //  realTimeEcgView.erasePartOfCanvas();


                                    }

                                }
*/
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        t.start();
    }

    private double graph2LastXValue = 0;
    private LineGraphSeries<DataPoint> mSeries2;



    public void plot_graph() {
        mSeries2 = new LineGraphSeries<>();

        mSeries2.setColor(Color.WHITE);
        mSeries2.setThickness(2);
        //graph2.setTitle("Spo2 chart");
        grapView.addSeries(mSeries2);
        grapView.getViewport().setXAxisBoundsManual(true);


      //  mSeries2.setDrawBackground(true);
        // mSeries2.setBackgroundColor(R.color.white);

        //  grapView.getViewport().setBackgroundColor(hite));
        grapView.getViewport().setMinX(0);
        grapView.getViewport().setMaxX(800);


        grapView.getViewport().setYAxisBoundsManual(true);
       grapView.getViewport().setMinY(-80);  // set the min value of the viewport of y axis
        grapView.getViewport().setMaxY(120);    // set the max value of the viewport of y-axis

        grapView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        grapView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        grapView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //  graph2.getGridLabelRenderer().setNumHorizontalLabels(300);
        //grapView.getGridLabelRenderer().setNumVerticalLabels(2);
        grapView.getViewport().setScrollable(true);


    }

    double mLastRandom = 2;
    Random mRand = new Random();
    int pxCount=0;


    private double getRandom() {

        mLastRandom=LeadData.LEADArray[pxCount];
        pxCount++;
        if(pxCount==10000)
        {
            pxCount=0;
        }
        return mLastRandom;
    }

}

