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
import patient.list.*;
import utility.*;


public class PatientRecyclerView extends RecyclerView.Adapter<PatientRecyclerView.CustomViewHolder>{

   Context context;
   LoginActivityListner loginActivityListner;
   List<PatientModel> patModelList;
  boolean iselection;

  boolean array[];
    public PatientRecyclerView(Context context, LoginActivityListner listner, List<PatientModel> pat,boolean Isseleted) {
        this.context = context;
        this.loginActivityListner=listner;
        this.patModelList=pat;

        this.iselection=Isseleted;

        array=new boolean[pat.size()];
    }




    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pat_list,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {


       final int pos=position;

        holder.patName.setText(patModelList.get(position).getPatName());
        holder.patId.setText(String.valueOf(patModelList.get(position).getPatId()));

        holder.patAge.setText(String.valueOf(patModelList.get(position).getPatAge()));

        holder.patSex.setText(patModelList.get(position).getGender());
        holder.patRace.setText(patModelList.get(position).getPatRace());


        if(iselection)
        {
            holder.checkBox.setVisibility(View.VISIBLE);

        }


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked)
                {
                    array[pos]=true;
                    loginActivityListner.getSelectedUser("data",array);
                }else{
                    array[pos]=false;
                }

            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                iselection=true;
                notifyDataSetChanged();

                return true;
            }
        });




            for(int i=0;i< array.length;i++)
            {
                holder.checkBox.setChecked(array[pos]);
            }


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.layout.setBackgroundColor(Utility.getColorWrapper(context,R.color.header_itltle_color));
                    loginActivityListner.onDataPass(Constants.SHOW_ECG_DATA);
                }
            });



    }

    @Override
    public int getItemCount() {
        return patModelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder  {


        LinearLayout layout;
        private TextView patName;
        private TextView patId;
        private TextView patAge;
        private TextView patSex;
        private TextView patRace;
        private  CheckBox checkBox;

        public CustomViewHolder(View view) {
            super(view);

        this.layout=view.findViewById(R.id.layout);


       this.patName=view.findViewById(R.id.patName);
       this. patId=view.findViewById(R.id.patId);
       this. patAge=view.findViewById(R.id.patAge);
       this. patSex=view.findViewById(R.id.patSex);
        this.patRace=view.findViewById(R.id.patRace);
        this.checkBox=view.findViewById(R.id.checkBox);




        }



    }


}
