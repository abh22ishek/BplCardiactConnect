package patient.list;

import android.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.content.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.io.*;
import java.util.*;

import application.*;
import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import constants.*;
import custom.view.*;
import data.*;
import database.*;
import logger.*;
import login.fragment.*;
import utility.*;

public class PatientProfileFragment extends Fragment {

    LoginActivityListner loginActivityListner;

    Spinner patRaceSpinner ,patDrug1Spinner,patDrug2Spinner,clinicDiagSpinner;
    ArrayAdapter<CharSequence> adapter;
    Button submit;

    EditText patName,patId,patAge,patHeight,patWeight,patsystolic,patDiabolic,patConsulDoc,patRefDoc;

    RadioGroup sex,pacemaker;
    String gender="Male";
    String drug1="";
    String mPacemaker="Yes";
    SQLiteDatabase database;

    RoundedImageView patIcon;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = 0;

    BaseApplicationClass globalVariable;

    Set<String> ConsultationDocs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginActivityListner= (LoginActivityListner) getActivity();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view= inflater.inflate(R.layout.pat_profile,container,false);
        patRaceSpinner=view.findViewById(R.id.patRace);
        patDrug1Spinner=view.findViewById(R.id.patDrug1);
        patDrug2Spinner=view.findViewById(R.id.patDrug2);
        clinicDiagSpinner=view.findViewById(R.id.patClinicDiagnosis);

        submit=view.findViewById(R.id.btnSubmit);
        pacemaker=view.findViewById(R.id.pacemaker);

        sex=view.findViewById(R.id.sex);
        patName=view.findViewById(R.id.patName);
        patId=view.findViewById(R.id.patId);
        patAge=view.findViewById(R.id.patAge);
        patHeight=view.findViewById(R.id.height);
        patWeight=view.findViewById(R.id.weight);
        patsystolic=view.findViewById(R.id.patSystolic);
        patDiabolic=view.findViewById(R.id.patDiabolic);
        patConsulDoc=view.findViewById(R.id.patConsDoc);
        patRefDoc=view.findViewById(R.id.patRefDoc);
        patIcon=view.findViewById(R.id.patIcon);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.PATIENT_PROFILE_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.PATIENT_PROFILE_FRAGMENT);


        patIcon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.user_icon));


        patIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectOptions(getActivity());
            }
        });

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.patient_race, android.R.layout.simple_spinner_item);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patRaceSpinner.setAdapter(adapter);


        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pat_drug, android.R.layout.simple_spinner_item);



        patDrug1Spinner.setAdapter(adapter);
        patDrug2Spinner.setAdapter(adapter);


        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.clinic_diag, android.R.layout.simple_spinner_item);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_btn_Male) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });



        pacemaker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_btn_Yes) {
                    mPacemaker = "Yes";
                } else {
                    mPacemaker = "No";
                }
            }
        });


        clinicDiagSpinner.setAdapter(adapter);


        patId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){
                    database = DatabaseManager.getInstance().openDatabase();
                    if(DatabaseManager.getInstance().isPatientIdExists(patId.getText().toString()))
                    {
                        patId.setError("Patient id  already taken");
                        disableFields();
                    }else{
                        enableFields();
                    }
                }
            }
        });
        final String currentDateTime= DateTime.getDateTime();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!isValidFields())
                    return;

               SQLiteDatabase mDatabase= DatabaseManager.getInstance().openDatabase();

               try{
                   mDatabase.insert(FeedReaderDbHelper.TABLE_NAME_PATIENT_PROFILE,null,
                           addMewPatient(Constants.Logged_User_ID,patName.getText().toString().toLowerCase().trim(),
                                   patId.getText().toString().trim(),
                                   currentDateTime,gender, patAge.getText().toString().trim()
                                   ,patHeight.getText().toString().trim(),
                                   patWeight.getText().toString().trim(),
                                   mPacemaker,patRaceSpinner.getSelectedItem().toString().toLowerCase(),
                                   patDrug1Spinner.getSelectedItem().toString().toLowerCase(),patDrug2Spinner.getSelectedItem()
                                   .toString().toLowerCase(),clinicDiagSpinner.getSelectedItem().toString().toLowerCase()
                                   ,patsystolic.getText().toString().trim(),patDiabolic.getText().toString().trim(),
                                   patConsulDoc.getText().toString().toLowerCase().trim()
                                   ,patRefDoc.getText().toString().toLowerCase().trim()
                           ));

                   Toast.makeText(getActivity(),"Patient Successfully Created",Toast.LENGTH_SHORT).show();
                   store_profile_image(getActivity(), profile_image, patId.getText().toString());

               }catch (Exception e){
                   e.printStackTrace();
               }

               finally {
                   loginActivityListner.onDataPass("back");
               }

            }
        });



        patRefDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConsultationDocs=Utility.getListOFHospitalDocs(getActivity());
                if(ConsultationDocs!=null)
                {
                // showDocs(getActivity(),ConsultationDocs,patRefDoc);
                }

            }
        });


        patConsulDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultationDocs=Utility.getListOFHospitalDocs(getActivity());
                if(ConsultationDocs!=null)
                {
                    showDocs(getActivity(),ConsultationDocs,patConsulDoc);
                }
            }
        });

    }


    private void enableFields()
    {
        patName.setEnabled(true);
      //  patId.setEnabled(true);
        patAge.setEnabled(true);
        patHeight.setEnabled(true);
        patWeight.setEnabled(true);
        patsystolic.setEnabled(true);
        patDiabolic.setEnabled(true);
        patConsulDoc.setEnabled(true);
        patRefDoc.setEnabled(true);
        patRaceSpinner.setEnabled(true);
        patDrug1Spinner.setEnabled(true);
        patDrug2Spinner.setEnabled(true);
        clinicDiagSpinner.setEnabled(true);
    }


    private void disableFields(){
        patName.setEnabled(false);
       // patId.setEnabled(false);
        patAge.setEnabled(false);
        patHeight.setEnabled(false);
        patWeight.setEnabled(false);
        patsystolic.setEnabled(false);
        patDiabolic.setEnabled(false);
        patConsulDoc.setEnabled(false);
        patRefDoc.setEnabled(false);
        patRaceSpinner.setEnabled(false);
        patDrug1Spinner.setEnabled(false);
        patDrug2Spinner.setEnabled(false);
        clinicDiagSpinner.setEnabled(false);
    }

    private ContentValues addMewPatient(String uname,String patname, String patid, String test_time,String gender,String  age, String height,
                 String weight,String pacemaker,String patRace,String drug1,String drug2, String clinicDiag,
                                        String systolic, String diabolic,String consulDoc, String refDoc)
    {
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.USER_NAME,uname);
        values.put(FeedReaderDbHelper.PATIENT_NAME,patname);
        values.put(FeedReaderDbHelper.PATIENT_ID,patid);
        values.put(FeedReaderDbHelper.PATIENT_TEST_TIME,test_time);

        values.put(FeedReaderDbHelper.PATIENT_GENDER,gender);
        values.put(FeedReaderDbHelper.PATIENT_AGE,age);
        values.put(FeedReaderDbHelper.PATIENT_HEIGHT,height);
        values.put(FeedReaderDbHelper.PATIENT_WEIGHT,weight);


        values.put(FeedReaderDbHelper.PATIENT_RACE,patRace);
        values.put(FeedReaderDbHelper.PATIENT_PACEMAKER,pacemaker);

        values.put(FeedReaderDbHelper.PATIENT_DRUG1,drug1);
        values.put(FeedReaderDbHelper.PATIENT_DRUG2,drug2);

        values.put(FeedReaderDbHelper.PATIENT_CLINICAL_DIAGNOSIS,clinicDiag);

        values.put(FeedReaderDbHelper.PATIENT_SYSTOLIC_DATA,systolic);
        values.put(FeedReaderDbHelper.PATIENT_DIABOLIC_DATA,diabolic);

        values.put(FeedReaderDbHelper.PATIENT_CONSULTATTION_DOC,consulDoc);
        values.put(FeedReaderDbHelper.PATIENT_REF_DOC,refDoc);

        return values;

    }



    private void selectOptions(final Context context) {

        List<String> options;


            options = new ArrayList<>();
            options.add(getString(R.string.def));
            options.add(getString(R.string.camera));
            options.add(getString(R.string.gallery));





        ArrayAdapter<String> adapter=new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,options);


          final Dialog  dialog = new Dialog(context);



        dialog.getWindow().getAttributes().windowAnimations =R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_list);

        final ListView content= dialog.findViewById(R.id.list_content);
        final TextView header=  dialog.findViewById(R.id.header);


        content.setAdapter(adapter);
        header.setText(getResources().getString(R.string.ch_opt));
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getItemAtPosition(i).equals(getString(R.string.def)))
                {

                    patIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_icon));

                    }else if(adapterView.getItemAtPosition(i).equals(getString(R.string.camera)))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                    Constants.PATIENT_PROFILE_CAMERA_CODE);


                        }
                        else
                        {
                          File  file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test.jpg"+System.currentTimeMillis());
                            Uri tempuri=Uri.fromFile(file);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,tempuri);
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                            startActivityForResult(intent,Constants.PATIENT_PROFILE_CAMERA_CODE);
                        }

                    }else{
                    Intent intent;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    }else{
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                    }
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PATIENT_PROFILE_REQUEST_CODE);

                }


                dialog.dismiss();
            }

        });

        dialog.show();
    }


    String profile_image="";

    File file;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        // Log.i("TAG", "**Grant Results**=" + grantResults[0]+" "+grantResults[1]+" "+grantRes + "request code=" + requestCode);

        for(int k=0;k<grantResults.length;k++)
        {
            Logger.log(Level.DEBUG,"--","grant results[]="+grantResults[k]);

        }
        if(requestCode==Constants.PATIENT_PROFILE_CAMERA_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED
                    ) {
                file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test.jpg"+System.currentTimeMillis());
                Uri tempuri=Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,tempuri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, Constants.PATIENT_PROFILE_CAMERA_CODE);

            }

            else if(grantResults[0]==PackageManager.PERMISSION_DENIED ||grantResults[1]==PackageManager.PERMISSION_DENIED)

            {
                Toast.makeText(getActivity(), "Go to settings And enable permissions", Toast.LENGTH_LONG).show();



            }


        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("OnActivityResult()", "1");

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.PATIENT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();




            Glide
                    .with(getActivity())
                    .load(uri)
                    .override(100, 100) // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                    .into(new GlideDrawableImageViewTarget(patIcon) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);

                            Logger.log(Level.DEBUG,"-----","Glide loaded the image successfully");
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);

                            Logger.log(Level.ERROR,"----",e.toString());
                        }
                    });


            hide_soft_keypad(getActivity());
            profile_image=uri.toString();






        }
        else if(requestCode==Constants.PATIENT_PROFILE_CAMERA_CODE && resultCode==RESULT_OK)
        {
            String uri;
            if(file.exists())
            {
                Log.i("**Absolute path**=", "" + file.getAbsolutePath());
                uri=file.getAbsolutePath();

                profile_image="file://"+uri;
                Glide
                        .with(getActivity())
                        .load(uri)
                        .override(100, 100)
                        .centerCrop()// resizes the image to these dimensions (in pixel). does not respect aspect ratio
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new GlideDrawableImageViewTarget(patIcon) {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);

                                Logger.log(Level.DEBUG,"---","Glide loaded the image successfully");
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);

                                Logger.log(Level.ERROR,"---",e.toString());
                            }
                        });


            }else{
                patIcon.setImageDrawable(this.getResources().getDrawable(R.drawable.user_icon));
            }


        }

    }

    private void hide_soft_keypad(Context context) {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    public static SharedPreferences store_profile_image(Context context,String uriImage,String key_username)
    {

        SharedPreferences profile_image;
        //=SignUpActivity.this.getPreferences(Context.MODE_PRIVATE) ;
        profile_image=context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = profile_image.edit();
        editor.putString(key_username,uriImage);
        editor.apply();


        Logger.log(Level.DEBUG, "---", "shared preference s file gets stored"+uriImage);
        // globalVariable.setUsername(email_id.getText().toString());
        return  profile_image;
    }


    private void showDocs(final Context context, Set<String> listofDocs, final View view) {


       List<String> listDocsW=new ArrayList<>();
        listDocsW.addAll(listofDocs);


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listDocsW);

            final Dialog dialog = new Dialog(context);


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_list);

        dialog.setCancelable(true);

        final ListView listview = dialog.findViewById(R.id.list_content);
        final TextView header = dialog.findViewById(R.id.header);

        if(view.equals(patRefDoc))
            header.setText(getString(R.string.sel_ref_doc));
        else
            header.setText(getString(R.string.sel_cons_doc));

        if(listofDocs.size()>6)
        {
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = 400;
            listview.setLayoutParams(params);
            listview.requestLayout();
        }

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

             if(view.equals(patRefDoc))
               patRefDoc.setText(adapterView.getItemAtPosition(i).toString());
             else
                 patConsulDoc.setText(adapterView.getItemAtPosition(i).toString());

                dialog.dismiss();
            }

        });

        dialog.show();
    }


    private boolean isValidFields()
    {
        boolean b=false;

        if(patId.getText().toString().trim().equals("")){
            patId.setError(getString(R.string.en_pat_id));
            patId.requestFocus();
            return b;
        }

        else if(patId.getText().toString().trim().length()<6)
        {
            patId.setError(getString(R.string.pat_min));
            patId.requestFocus();
            return b;
        }
        else if(patAge.getText().toString().trim().equals(""))
        {
            patAge.setError(getString(R.string.age_val));
            patAge.requestFocus();
            return b;
        }

        else if(patHeight.getText().toString().trim().equals(""))
        {
            patHeight.setError(getString(R.string.height_emp));
            patHeight.requestFocus();
            return b;
        }

        else if(patWeight.getText().toString().trim().equals(""))
        {
            patWeight.setError(getString(R.string.weigh_val));
            patWeight.requestFocus();
            return b;
        }

        else if(patConsulDoc.getText().toString().equals(""))
        {

           patConsulDoc.setError(getString(R.string.con_doc_val));
            patConsulDoc.requestFocus();
            return b;
        }

        b=true;
        return b;
    }
}
