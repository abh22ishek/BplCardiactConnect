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

public class DoctorRecyclerViewAdapter extends  RecyclerView.Adapter<DoctorRecyclerViewAdapter.CustomViewHolder> implements SelectDoctorsListner{

    Context context;
    LoginActivityListner loginActivityListner;
    List<String> doctorModelList;
    boolean iselection;

    ListR listlistner;
    boolean array[];


    public DoctorRecyclerViewAdapter(Context context, LoginActivityListner listner,ListR listlistner,
                                     List<String> doc,boolean Isseleted) {
        this.context = context;
        this.loginActivityListner=listner;
        this.doctorModelList=doc;
        this.iselection=Isseleted;

        listlistner=listlistner;
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

        holder.DocName.setText(doctorModelList.get(position));


        if(iselection)
        {


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






        for(int i=0;i< array.length;i++)
        {
            holder.checkBox.setChecked(array[pos]);
           // listlistner.getSelectedUser("data",array);
        }








    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }

    @Override
    public void markAll(List<String> DocList) {

        doctorModelList=DocList;
        array =new boolean[DocList.size()];


        for(int i=0;i <array.length;i++){
            array[i]=true;
        }

        notifyDataSetChanged();
    }

    @Override
    public void unMarkAll(List<String> DocList) {
        doctorModelList =DocList;
        array =new boolean[DocList.size()];

        for(int i=0;i <array.length;i++){
            array[i]=false;
        }


        notifyDataSetChanged();
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
