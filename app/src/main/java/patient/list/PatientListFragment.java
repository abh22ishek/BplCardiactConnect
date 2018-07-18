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

RecyclerView recyclerView;
LoginActivityListner loginActivityListner;
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

        loginActivityListner.onDataPass("");
        populateRecyclerView();

    }


    private void populateRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        PatientRecyclerView recyclerViewAdapter=new PatientRecyclerView(getActivity(),loginActivityListner,populatePatientList());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }



    List<PatientModel> PatientModelList;



    private List<PatientModel> populatePatientList()
    {
       PatientModelList=new ArrayList<>();


           PatientModelList.add(new PatientModel("111111","Patient 1",27,"Male","Asian",
                    " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel("111122","Patient 2",23,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel("111134","Patient 3",24,"Female","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("99999","Mr. Prembrooke",37,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("555555","Angelica D",45,"Female","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("121453","Robinson J",19,"Male","Asian",
                " Doc 1","Doc 2"));


        PatientModelList.add(new PatientModel("111190","Patient 4",44,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("111199","Akash Mishra",65,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("111222","Yasmeen Apse",21,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("128924","Mickey Mouse",98,"Male","Asian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("329321","Donald Duck",12,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel("121323","Snowhite 1",53,"Female","Indian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel("111189","Snowwhite 2",23,"Female","Indian",
                " Doc 1","Doc 2"));

        PatientModelList.add(new PatientModel("111490","Mrs Johnson",45,"Male","Asian",
                " Doc 1","Doc 2"));
        PatientModelList.add(new PatientModel("111143","Rosa Park",43,"Female","American",
                " Doc 1","Doc 2"));




        return PatientModelList;
    }




    private void sortByName(List<PatientModel> patientModelList){

        Collections.sort(patientModelList, PatientModel.patNameComparator);
        for(PatientModel str: patientModelList){
            Logger.log(Level.DEBUG, ClassConstants.PATIENT_LIST_FRAGMENT,"Sort BY NAME---))"+str);
        }
    }


    private void sortByAge(List<PatientModel> patientModelList){

        Collections.sort(patientModelList);

        for(PatientModel str: patientModelList){
            Logger.log(Level.DEBUG, ClassConstants.PATIENT_LIST_FRAGMENT,"Sort BY AGE---))"+str);

        }
    }


}
