package hospital;

import android.*;
import android.app.*;
import android.content.*;
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
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.io.*;
import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import constants.*;
import logger.*;
import login.fragment.*;

import static android.app.Activity.RESULT_OK;

public class HospitalProfileFragment extends Fragment {


    LinearLayout linerarEditInitilasHospName,linerarupdateCons,linerarEditHospName,lineareditDepartmentName;
    RadioButton r1,r2,r3,r4;
    Button edit;
    String  headerText;
    String TAG="";

    LoginActivityListner loginActivityListner;
    TextView hospName,department_name;

    ImageView hospitalImage;
    ImageButton editBtn;

    String hospitalIconUri;
    String hosName,departName,hospInitials, savedUriHosp;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.hosp_profile,container,false);
        linerarEditInitilasHospName=view.findViewById(R.id.linerarEditInitilasHospName);
        linerarupdateCons=view.findViewById(R.id.linerarupdateCons);
        linerarEditHospName=view.findViewById(R.id.linerarEditHospName);
        lineareditDepartmentName=view.findViewById(R.id.lineareditDepartmentName);
        hospName=view.findViewById(R.id.hospName);
        department_name=view.findViewById(R.id.departName);

        editBtn=view.findViewById(R.id.editBtn);
        r1=view.findViewById(R.id.r1);
        r2=view.findViewById(R.id.r2);
        r3=view.findViewById(R.id.r3);
        r4=view.findViewById(R.id.r4);

        edit=view.findViewById(R.id.btnEdit);
        hospitalImage=view.findViewById(R.id.hospitalImage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        r1.setChecked(true);
        headerText="Enter Hospital Initials";
        hospitalIconUri="";

        hospName.setText(getString(R.string.hosp_name));
        department_name.setText(getString(R.string.department_name));


        if(getHospitalDetails(getActivity())!=null){

            hospName.setText(hosName);
            department_name.setText(departName);
            if(savedUriHosp.equals(""))
            {
                hospitalImage.setImageDrawable(this.getResources().getDrawable(R.mipmap.hosp_img));

            }else{
                loadImageWithGlide(savedUriHosp,hospitalImage);

            }

        }
        linerarEditInitilasHospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(true);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);

                headerText="Enter Hospital Initials";
                TAG="";

            }
        });



        linerarupdateCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(true);
                r3.setChecked(false);
                r4.setChecked(false);

                headerText="Enter Consulting Doctor";
                TAG="";

                storeHospitalCredentials();
                loginActivityListner.onDataPass(ClassConstants.HOSPITAL_PROFILE_FRAGMENT);



            }
        });


        linerarEditHospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(true);
                r4.setChecked(false);

                headerText="Enter Hospital Name";
                TAG="HOSPITAL_NAME";

            }
        });


        lineareditDepartmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(true);

                headerText="Enter Department Name";
                TAG="DEPARTMENT_NAME";


            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectOptions(getActivity(),headerText,TAG);
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectOptionsF(getActivity());
            }
        });


        }



        Dialog dialog;

    private void selectOptions(final Context context,String headerText,String Tag) {

        if (dialog == null) {
            dialog = new Dialog(context);
        }


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.new_custom_dialog);
        dialog.setCancelable(true);

        final EditText content = dialog.findViewById(R.id.textEdit);
        final TextView header = dialog.findViewById(R.id.header);
        final TextView textview = dialog.findViewById(R.id.textview);
        textview.setVisibility(View.GONE);
        final Button OK=dialog.findViewById(R.id.btnOk);
        header.setText(headerText);
        if(TAG.equalsIgnoreCase("Hospital_name"))
        {
            content.setText(hospName.getText().toString().trim());
        }
        else if(TAG.equalsIgnoreCase("Department_name"))
        {
            content.setText(department_name.getText().toString().trim());
        }


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TAG.equalsIgnoreCase("Hospital_name"))
                {
                    hospName.setText(content.getText().toString().trim());
                }
                else if(TAG.equalsIgnoreCase("Department_name"))
                {
                    department_name.setText(content.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("OnActivityResult()", "1");

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.HOSPITAL_PROFILE_REQUEST_CODE && resultCode == RESULT_OK
                && null != data) {
            Uri uri = data.getData();

            hospitalIconUri=uri.toString();
           loadImageWithGlide(uri.toString(),hospitalImage);

           }
        else if(requestCode==Constants.HOSPITAL_PROFILE_CAMERA_CODE && resultCode==RESULT_OK)
        {
            String uri;
            if(file.exists())
            {
                Log.i("**Absolute path**=", "" + file.getAbsolutePath());
                uri=file.getAbsolutePath();

                hospitalIconUri="file://"+uri;

                loadImageWithGlide(uri,hospitalImage);


            }else{
                hospitalImage.setImageDrawable(this.getResources().getDrawable(R.mipmap.hosp_img));
                hospitalIconUri="";
            }


        }

    }


    private void loadImageWithGlide(String uri, ImageView imageView) {


        //noinspection SpellCheckingInspection


        Glide
                .with(getActivity())
                .load(uri)


                .centerCrop()

                // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);

                        Logger.log(Level.DEBUG, TAG, "Glide loaded the image successfully");
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);

                        Logger.log(Level.ERROR, TAG, e.toString());
                    }
                });

    }


    List<String> options;


    private void selectOptionsF(final Context context) {


        if (options == null) {
            options = new ArrayList<>();
            options.add(getString(R.string.def));
            options.add(getString(R.string.camera));
            options.add(getString(R.string.gallery));

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, options);
        if (dialog == null) {
            dialog = new Dialog(context);
        }


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_list);

        final ListView content = dialog.findViewById(R.id.list_content);
        final TextView header = dialog.findViewById(R.id.header);


        content.setAdapter(adapter);
        header.setText(getResources().getString(R.string.ch_opt));
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals(getString(R.string.def))) {

                    hospitalImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.hosp_img));
                    hospitalIconUri="";


                } else if (adapterView.getItemAtPosition(i).equals(getString(R.string.camera))) {
                    selectCameraOptions();
                } else {
                    selectPicsFromGallery();

                }
                dialog.dismiss();
            }

        });

        dialog.show();
    }


    File file;
    private void selectCameraOptions() {


        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "test.jpg" + System.currentTimeMillis());
            Uri tempUri = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, Constants.HOSPITAL_PROFILE_CAMERA_CODE);

    }




    private void selectPicsFromGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                Constants.HOSPITAL_PROFILE_REQUEST_CODE);
    }





    private void storeHospitalCredentials()
    {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.HOSPITAL_INFO_FILE,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(Constants.HOSPITAL_INITIALS,"");
        editor.putString(Constants.HOSPITAL_NAME,hospName.getText().toString().toLowerCase().trim());

        editor.putString(Constants.HOSPITAL_DEPARTMENT,department_name.getText().toString().toLowerCase().trim());
        editor.putString(Constants.HOSPITAL_URI,hospitalIconUri);



        editor.apply();
}




    public  SharedPreferences getHospitalDetails(Context context)
    {


        SharedPreferences prefs;
        prefs = context.getSharedPreferences(Constants.HOSPITAL_INFO_FILE, Context.MODE_PRIVATE);

        hosName =prefs.getString(Constants.HOSPITAL_NAME, "");
        hospInitials =prefs.getString(Constants.HOSPITAL_NAME, "");
        departName =prefs.getString(Constants.HOSPITAL_DEPARTMENT, "");
        savedUriHosp =prefs.getString(Constants.HOSPITAL_URI, "");


        return prefs;
    }

}
