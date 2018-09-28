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
import login.fragment.*;
import model.*;

public class AddHospitalDoctors extends Fragment {

    LoginActivityListner loginActivityListner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    RecyclerView listOfDocs;
    List<DoctorModel>  DocsList;

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



        if((Objects.requireNonNull(getArguments())).getParcelableArrayList(Constants.USER_NAME)!=null){
          DocsList=getArguments().getParcelableArrayList(Constants.USER_NAME);
        }
        populateRecyclerView();

        }


    private void populateRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        listOfDocs.setLayoutManager(layoutManager);

        DoctorRecyclerViewAdapter doctorRecyclerViewAdapter=new DoctorRecyclerViewAdapter(getActivity(),loginActivityListner,
                DocsList,false);

        listOfDocs.setAdapter(doctorRecyclerViewAdapter);


    }


    }
