package hospital;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;
import logger.*;
import login.fragment.*;
import model.*;
import utility.*;

public class AddHospitalDoctors extends Fragment {

    LoginActivityListner loginActivityListner;
    ListR listR;

    DoctorRecyclerViewAdapter doctorRecyclerViewAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
        try
        {
            listR= (ListR) getActivity();
            Logger.log(Level.DEBUG,"","--On Success--");
        }catch (ClassCastException e)
        {
            e.printStackTrace();
        }



    }

    RecyclerView listOfDocs;
    List<String>  DocsList;
    Set<String> doctorsSet;
    boolean isChecked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.add_doc_hosp,container,false);
        listOfDocs=view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass(ClassConstants.ADD_HOSPITAL_DOCTORS_FRAGMENT);

        Bundle b = getArguments();
        isChecked= b.getBoolean("checked");



        doctorsSet=Utility.getListOFHospitalDocs(getActivity());
        if(doctorsSet!=null){
            DocsList=new ArrayList<>();
            DocsList.addAll(doctorsSet);
            populateRecyclerView();
            if(isChecked){
                doctorRecyclerViewAdapter.markAll(DocsList);
            }
        }



        }


    private void populateRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        listOfDocs.setLayoutManager(layoutManager);

         doctorRecyclerViewAdapter=new DoctorRecyclerViewAdapter(getActivity(),
                loginActivityListner,listR,
                DocsList,isChecked);


        listOfDocs.setAdapter(doctorRecyclerViewAdapter);


    }




    private void deleteSelectedRecords(boolean [] array){
        for(int i=0;i<array.length;i++){
            if(array[i]){
                // delete those records
                deleteRecords(i);
            }
        }

    }



    private void deleteRecords(int index){

        DatabaseManager.getInstance().openDatabase();
        try{
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    }
