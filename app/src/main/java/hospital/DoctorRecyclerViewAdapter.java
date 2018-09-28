package hospital;

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
import recyclerview.cardview.*;
import utility.*;

public class DoctorRecyclerViewAdapter extends  RecyclerView.Adapter<DoctorRecyclerViewAdapter.CustomViewHolder> {

    Context context;
    LoginActivityListner loginActivityListner;
    List<DoctorModel> doctorModelList;
    boolean iselection;

    boolean array[];


    public DoctorRecyclerViewAdapter(Context context, LoginActivityListner listner, List<DoctorModel> doc,boolean Isseleted) {
        this.context = context;
        this.loginActivityListner=listner;
        this.doctorModelList=doc;

        this.iselection=Isseleted;

        array=new boolean[doc.size()];
    }




    @NonNull
    @Override
    public DoctorRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.docs_list,parent,false);
        DoctorRecyclerViewAdapter.CustomViewHolder viewHolder = new DoctorRecyclerViewAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorRecyclerViewAdapter.CustomViewHolder holder, int position) {

        final int pos=position;

        holder.DocName.setText(doctorModelList.get(position).getDocName());


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
                    loginActivityListner.getSelectedUser("doc",array);
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

            }
        });




    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder  {


        RelativeLayout layout;
        private TextView DocName;
        private  CheckBox checkBox;


        public CustomViewHolder(View view) {
            super(view);

            this.layout=view.findViewById(R.id.layout);
            this.DocName=view.findViewById(R.id.DocName);
            this.checkBox=view.findViewById(R.id.checkBox);

        }



    }

}
