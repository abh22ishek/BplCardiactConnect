package patient.list;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import logger.*;

import login.fragment.*;
import model.*;
import recyclerview.cardview.*;

public class PatientListFragment extends Fragment{

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
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByAge(populatePatientList()));

                break;
            case Constants.SORT_BY_NAME:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByName(populatePatientList()));

                break;
            case Constants.SORT_BY_ID:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, sortByID(populatePatientList()));

                break;
            default:
                recyclerViewAdapter = new PatientRecyclerView(getActivity(), loginActivityListner, populatePatientList());
                break;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }



    private List<PatientModel> PatientModelList;



    private List<PatientModel> populatePatientList()
    {
       PatientModelList=new ArrayList<>();


           PatientModelList.add(new PatientModel(111111,"Patient 1",27,"Male","Asian",
                    " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel(111122,"Patient 2",23,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel(111134,"Patient 3",24,"Female","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(999999,"Mr. Prembrooke",37,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(555555,"Angelica D",45,"Female","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(121453,"Robinson J",19,"Male","Asian",
                " Doc 1","Doc 2"));


        PatientModelList.add(new PatientModel(111190,"Patient 4",44,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(111199,"Akash Mishra",65,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(111222,"Yasmeen Apse",21,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(128924,"Mickey Mouse",98,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(329321,"Donald Duck",12,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel(121323,"Snowhite 1",53,"Female","Indian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel(111189,"Snowwhite 2",23,"Female","Indian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel(111490,"Mrs Johnson",45,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel(111143,"Rosa Park",43,"Female","American",
                " Doc 1","Doc 2"));




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

}
