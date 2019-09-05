package patient.list;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import database.*;
import logger.*;
import login.fragment.*;
import model.*;
import recyclerview.cardview.*;

public class ExistingPatientFragment extends Fragment {

    LoginActivityListner loginActivityListner;

    RecyclerView recyclerView;
    private List<PatientModel> ExistingPatientModelList;
    private List<PatientModel> ExistingPatientModelListAfterFilter;
    private EditText autoCompleteTextView;

    ExistingPatientRecyclerView  recyclerViewAdapter;
    private ArrayAdapter<CharSequence> adapter;

    private Spinner SearchBy;
    String searchByTag="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view= inflater.inflate(R.layout.existing_pat,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        autoCompleteTextView=view.findViewById(R.id.autoText);
        SearchBy=view.findViewById(R.id.searchBy);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.EXISTING_PATIENT_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.EXISTING_PATIENT_FRAGMENT);


        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.search_by, android.R.layout.simple_spinner_item);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SearchBy.setAdapter(adapter);

        getExistingPatientFromDB();
        populateRecyclerView(searchByTag);

       final String [] spinnerArr= getResources().getStringArray(R.array.search_by);


        SearchBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if(adapterView.getItemAtPosition(pos).toString().equalsIgnoreCase(spinnerArr[0]))
                {
                    searchByTag="name";
                    autoCompleteTextView.setHint(getString(R.string.search_by_name));
                    autoCompleteTextView.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    searchByTag="id";
                    autoCompleteTextView.setHint(getString(R.string.search_by_id));
                    autoCompleteTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                autoCompleteTextView.getText().clear();
                hide_soft_keypad();
                populateRecyclerView(searchByTag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
                Logger.log(Level.DEBUG,ExistingPatientFragment.class.getName(),
                        "Nothing gets selected in Spinner");
            }


        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                  recyclerViewAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    private List<PatientModel>  getExistingPatientFromDB()
    {
        try {
            DatabaseManager.getInstance().openDatabase();
            ExistingPatientModelList= DatabaseManager.getInstance().getAllPatientsFromDB();

        }catch (Exception e){
            e.printStackTrace();
        }

        return ExistingPatientModelList;
    }

    private void populateRecyclerView(String searchByTag){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if(ExistingPatientModelList!=null){
            recyclerViewAdapter = new ExistingPatientRecyclerView(getActivity(),
                    loginActivityListner, ExistingPatientModelList,false,searchByTag);

            recyclerView.setAdapter(recyclerViewAdapter);
        }




    }


    private void hide_soft_keypad() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
}
