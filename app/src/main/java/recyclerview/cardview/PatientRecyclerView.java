package recyclerview.cardview;

import android.content.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import login.fragment.*;


public class PatientRecyclerView extends RecyclerView.Adapter<PatientRecyclerView.CustomViewHolder>{

   Context context;
   LoginActivityListner loginActivityListner;

    public PatientRecyclerView(Context context,LoginActivityListner listner) {
        this.context = context;
        this.loginActivityListner=listner;
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


    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        LinearLayout layout;

        public CustomViewHolder(View view) {
            super(view);

        layout=view.findViewById(R.id.layout);
        layout.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if(view==layout){
               // loginActivityListner.navigateFragment(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
            }
        }
    }


}
