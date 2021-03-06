package patient.list;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;

import java.net.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;
import logger.*;

import login.fragment.*;
import model.*;
import recyclerview.cardview.*;

public class PatientListFragment extends Fragment {

private RecyclerView recyclerView;
private LoginActivityListner loginActivityListner;
private List<PatientModel> patientListSortedByName;
   private List<PatientModel> patientListSortedByAGE;
    private List<PatientModel> patientListSortedByID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view= inflater.inflate(R.layout.pat_list_recy,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass(ClassConstants.PATIENT_LIST_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.PATIENT_LIST_FRAGMENT);

        if((Objects.requireNonNull(getArguments())).getString(Constants.SORT_BY)!=null){
            soretedVal= getArguments().getString(Constants.SORT_BY);
        }
        populateRecyclerView();

    }

    private String soretedVal="";


    private void populateRecyclerView(){
        PatientRecyclerView recyclerViewAdapter;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);



        switch (soretedVal) {
            case Constants.SORT_BY_AGE:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByAge(populatePatientList()),false);

                break;
            case Constants.SORT_BY_NAME:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByName(populatePatientList()),false);

                break;
            case Constants.SORT_BY_ID:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByID(populatePatientList()),false);

                break;
            default:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, populatePatientList(),false);
                break;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

        }



    private List<PatientModel> PatientModelList;
    private List<PatientModel> populatePatientList()
    {
       PatientModelList=new ArrayList<>();


            try {
                DatabaseManager.getInstance().openDatabase();
                PatientModelList= DatabaseManager.getInstance().getAllPatientsFromDB();

            }catch (Exception e){
                e.printStackTrace();
            }

            return PatientModelList;




    }




    private List<PatientModel> sortByName(List<PatientModel> patientModelList){

        patientListSortedByName=new ArrayList<>();
        Collections.sort(patientModelList, PatientModel.patNameComparator);
        for(PatientModel str: patientModelList){
            Logger.log(Level.DEBUG, ClassConstants.PATIENT_LIST_FRAGMENT,"Sort BY NAME---))"+str);
            patientListSortedByName.add(str);
        }

        return patientListSortedByName;
    }


    private List<PatientModel> sortByAge(List<PatientModel> patientModelList){

        patientListSortedByAGE=new ArrayList<>();
        Collections.sort(patientModelList);

        for(PatientModel str: patientModelList){
            Logger.log(Level.DEBUG, ClassConstants.PATIENT_LIST_FRAGMENT,"Sort BY AGE---))"+str);

            patientListSortedByAGE.add(str);
            }
        return patientListSortedByAGE;
    }


    private List<PatientModel> sortByID(List<PatientModel> patientModelList){

        patientListSortedByID=new ArrayList<>();
        Collections.sort(patientModelList, PatientModel.patIdComparator);

        for(PatientModel str: patientModelList){
            Logger.log(Level.DEBUG, ClassConstants.PATIENT_LIST_FRAGMENT,"Sort BY ID---))"+str);
            patientListSortedByID.add(str);

        }


        return patientListSortedByID;
    }

    public interface OnHeadlineSelectedListener {
        boolean showcheckbox(boolean show);
    }
}
