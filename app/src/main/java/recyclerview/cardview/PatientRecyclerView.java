package recyclerview.cardview;

import android.content.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import login.fragment.*;
import model.*;


public class PatientRecyclerView extends RecyclerView.Adapter<PatientRecyclerView.CustomViewHolder>{

   Context context;
   LoginActivityListner loginActivityListner;
   List<PatientModel> patModelList;

    public PatientRecyclerView(Context context, LoginActivityListner listner, List<PatientModel> pat) {
        this.context = context;
        this.loginActivityListner=listner;
        this.patModelList=pat;
    }




    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pat_list,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.patName.setText(patModelList.get(position).getPatName());
        holder.patId.setText(String.valueOf(patModelList.get(position).getPatId()));

        holder.patAge.setText(String.valueOf(patModelList.get(position).getPatAge()));

        holder.patSex.setText(patModelList.get(position).getGender());
        holder.patRace.setText(patModelList.get(position).getPatRace());



    }

    @Override
    public int getItemCount() {
        return patModelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        LinearLayout layout;
        private TextView patName;
        private TextView patId;
        private TextView patAge;
        private TextView patSex;
        private TextView patRace;

        public CustomViewHolder(View view) {
            super(view);

        this.layout=view.findViewById(R.id.layout);
        this.layout.setOnClickListener(this);

       this.patName=view.findViewById(R.id.patName);
       this. patId=view.findViewById(R.id.patId);
       this. patAge=view.findViewById(R.id.patAge);
       this. patSex=view.findViewById(R.id.patSex);
        this.patRace=view.findViewById(R.id.patRace);



        }


        @Override
        public void onClick(View view) {
            if(view==layout){
               // loginActivityListner.navigateFragment(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
            }
        }
    }


}
