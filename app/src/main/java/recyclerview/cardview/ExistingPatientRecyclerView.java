package recyclerview.cardview;

import android.content.*;
import android.graphics.drawable.*;
import android.support.annotation.*;
import android.support.v4.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.text.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import custom.view.*;
import logger.*;
import login.fragment.*;
import model.*;



public class ExistingPatientRecyclerView  extends RecyclerView.Adapter<ExistingPatientRecyclerView.CustomViewHolder> implements Filterable{


    Context context;
    LoginActivityListner loginActivityListner;
    List<PatientModel> patModelList;
    boolean iselection;

    boolean array[];
    List<PatientModel> filterList;

    CustomFilter filter;
    String searchByTag;

    public ExistingPatientRecyclerView(Context context, LoginActivityListner listner,
                                       List<PatientModel> pat,boolean Isseleted,String searchByTag) {
        this.context = context;
        this.loginActivityListner=listner;
        this.patModelList=pat;

        this.iselection=Isseleted;

       this. filterList=pat;
       this.searchByTag=searchByTag;

        array=new boolean[pat.size()];
    }




    @NonNull
    @Override
    public ExistingPatientRecyclerView.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.existing_pat_bg,parent,false);
        ExistingPatientRecyclerView.CustomViewHolder viewHolder = new ExistingPatientRecyclerView.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExistingPatientRecyclerView.CustomViewHolder holder, int position) {


        final int pos=position;

        holder.patName.setText(new StringBuilder().append(patModelList.get(position).getPatName()).append(",").toString());
        holder.patId.setText(new StringBuilder().append(String.valueOf(patModelList.get(position).getPatId())).append(",").toString());

        holder.patAge.setText(new StringBuilder().append(new StringBuilder().append(String.valueOf(patModelList.get(position).getPatAge())).
                append("Years").toString()).append(",").toString());

        holder.patSex.setText(patModelList.get(position).getGender());
        holder.patRace.setText(patModelList.get(position).getPatRace());
        holder.patHeight.setText(String.format("%s,", String.format("%sCm", patModelList.get(position).getHeight())));
        holder.patWeight.setText(MessageFormat.format("{0},", MessageFormat.format("{0}Kg", patModelList.get(position).getWeight())));

        String uri = get_profile_image(String.valueOf(patModelList.get(position).getPatId()));

        if(uri!=""){
            Glide
                    .with(context)
                    .load(uri)
                    .override(80, 80)
                    .centerCrop()// resizes the image to these dimensions (in pixel). does not respect aspect ratio
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new GlideDrawableImageViewTarget(holder.patientIcon) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);

                            Logger.log(Level.DEBUG,"Patient Icon","Glide loaded the image successfully");
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);

                            Logger.log(Level.ERROR,"Patient Icon",e.toString());
                        }
                    });


        }else{
            holder.patientIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_icon));
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginActivityListner.onDataPass("patient_ecg");
            }
        });

    }

    @Override
    public int getItemCount() {
        return patModelList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder  {


        RelativeLayout layout;
        private TextView patName;
        private TextView patId;
        private TextView patAge;
        private TextView patSex;
        private RoundedImageView patientIcon;
        private TextView patRace,patHeight,patWeight;


        public CustomViewHolder(View view) {
            super(view);

            this.layout=view.findViewById(R.id.layout);


            this.patName=view.findViewById(R.id.patName);
            this. patId=view.findViewById(R.id.patId);
            this. patAge=view.findViewById(R.id.patAge);
            this. patSex=view.findViewById(R.id.patSex);
            this.patRace=view.findViewById(R.id.patRace);
            this.patHeight=view.findViewById(R.id.height);
            this.patWeight=view.findViewById(R.id.weight);
            this.patientIcon=view.findViewById(R.id.patientIcon);



            }


    }


    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toLowerCase();

                ArrayList<PatientModel> filters=new ArrayList<>();


                if(searchByTag.equals("name"))
                {
                    for(int i=0;i<filterList.size();i++)
                    {
                        if(filterList.get(i).getPatName().toLowerCase().contains(constraint))
                        {
                            PatientModel m=new PatientModel();
                            m.setPatName(filterList.get(i).getPatName());
                            // m.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME_)));
                            m.setPatId(filterList.get(i).getPatId());
                            m.setTime(filterList.get(i).getTime());
                            m.setGender(filterList.get(i).getGender());
                            m.setPatAge(filterList.get(i).getPatAge());

                            m.setHeight(filterList.get(i).getHeight());
                            m.setWeight(filterList.get(i).getWeight());



                            m.setPatRace(filterList.get(i).getPatRace());
                            m.setPacemaker(filterList.get(i).getPacemaker());

                            m.setDrug1(filterList.get(i).getDrug1());
                            m.setDrug2(filterList.get(i).getDrug2());

                            m.setClinicDiagnosis(filterList.get(i).getClinicDiagnosis());
                            m.setSystolic(filterList.get(i).getSystolic());

                            m.setDiabolic(filterList.get(i).getDiabolic());
                            m.setConsultName(filterList.get(i).getConsultName());

                            m.setPatRefDoc(filterList.get(i).getPatRefDoc());


                            filters.add(m);
                        }
                    }
                }else{
                    for(int i=0;i<filterList.size();i++)
                    {
                        if(String.valueOf(filterList.get(i).getPatId()).contains(constraint))
                        {
                            PatientModel m=new PatientModel();
                            m.setPatName(filterList.get(i).getPatName());
                            // m.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME_)));
                            m.setPatId(filterList.get(i).getPatId());
                            m.setTime(filterList.get(i).getTime());
                            m.setGender(filterList.get(i).getGender());
                            m.setPatAge(filterList.get(i).getPatAge());

                            m.setHeight(filterList.get(i).getHeight());
                            m.setWeight(filterList.get(i).getWeight());



                            m.setPatRace(filterList.get(i).getPatRace());
                            m.setPacemaker(filterList.get(i).getPacemaker());

                            m.setDrug1(filterList.get(i).getDrug1());
                            m.setDrug2(filterList.get(i).getDrug2());

                            m.setClinicDiagnosis(filterList.get(i).getClinicDiagnosis());
                            m.setSystolic(filterList.get(i).getSystolic());

                            m.setDiabolic(filterList.get(i).getDiabolic());
                            m.setConsultName(filterList.get(i).getConsultName());

                            m.setPatRefDoc(filterList.get(i).getPatRefDoc());


                            filters.add(m);
                        }
                    }
                }
                //get specific items


                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            patModelList=(ArrayList<PatientModel>) results.values;
            notifyDataSetChanged();
        }

    }

    private String get_profile_image(String key_userId) {
        SharedPreferences profile_image_prefs;
        profile_image_prefs =context.
                getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);

        String image_str = profile_image_prefs.getString(key_userId, "");
        Logger.log(Level.INFO, "SHARED PREFS", "get profile image from shared pref=" + image_str);
        return image_str;

    }


}
