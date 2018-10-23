package login.fragment;

import android.*;
import android.annotation.*;
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
import android.support.v4.app.FragmentTransaction;
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

import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import constants.*;
import custom.view.*;
import database.*;
import logger.*;
import store.credentials.*;

import static android.app.Activity.*;

public class SignUpFragment extends Fragment {

    private RoundedImageView UserIcon;

    Button btnSignUp;
    LoginActivityListner loginActivityListner;
    private EditText email_id,password,confirmPassword;

    private EditText security_question1,security_question2,security_question3;
    SQLiteDatabase database;

    Uri NavigationUserIconUri;
    private String userIconUri;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On Create() of Fragment");
        loginActivityListner.onDataPass(ClassConstants.SIGNUP_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIGNUP_FRAGMENT);
        loginActivityListner.isImaggeIconVisible(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.signup,container,false);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        UserIcon = view.findViewById(R.id.hospitalIcon1);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On CreateView() of Fragment");



        email_id=  view.findViewById(R.id.email);
        password= view.findViewById(R.id.password);


        confirmPassword=view.findViewById(R.id.confirmPassword);
        security_question1=  view.findViewById(R.id.securityques1);
        security_question2=  view.findViewById(R.id.securityques2);
        security_question3=  view.findViewById(R.id.securityques3);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.log(Level.DEBUG,ClassConstants.SIGNUP_FRAGMENT,"On onActivityCreated() of Fragment");


        loginActivityListner= (LoginActivityListner) getActivity();
        UserIcon.setOnClickListener(view -> selectOptions(getActivity()));
        email_id.setOnFocusChangeListener((v, hasFocus) -> {

            if(!hasFocus)
            {
                database = DatabaseManager.getInstance().openDatabase();
                if(DatabaseManager.getInstance().IsUsernameexists(email_id.getText().toString()))
                {
                    email_id.setError("Email id has already taken");
                    disableFields();
                }else{
                    enableFields();
                }
            }
        });
        btnSignUp.setOnClickListener(view -> {

            if(isValidFields())
            {
                userRegistration(getActivity());
                navigateFragment();
              //  destroyCurrentFragment();

                // unable to destroy current fragment android


            }

        });
    }


    private void disableFields()
    {
        password.setEnabled(false);
        confirmPassword.setEnabled(false);
        security_question1.setEnabled(false);
        security_question2.setEnabled(false);
        security_question3.setEnabled(false);
        btnSignUp.setEnabled(false);
    }


    private void enableFields()
    {
        password.setEnabled(true);
        confirmPassword.setEnabled(true);
        security_question1.setEnabled(true);
        security_question2.setEnabled(true);
        security_question3.setEnabled(true);
        btnSignUp.setEnabled(true);
    }


    private void userRegistration(Context context) {

       database= DatabaseManager.getInstance().openDatabase();
        if (!DatabaseManager.getInstance().IsUsernameexists(email_id.getText().toString().trim())) {

            database.insert(FeedReaderDbHelper.TABLE_NAME_USERS, null,
                    addUsers(email_id.getText().toString().trim(), password.getText().toString().trim(),
                            security_question1.getText().toString().toLowerCase().trim()
                            , security_question2.getText().toString().toLowerCase().trim(),
                            security_question3.getText().toString().toLowerCase().trim()));
            // database.close(); Don't close it directly!

            loginActivityListner.setUserName(email_id.getText().toString().trim(),ClassConstants.SIGNUP_FRAGMENT);
            saveCredentials(email_id.getText().toString().trim(),userIconUri,ClassConstants.SIGNUP_FRAGMENT);
            // globalVariable.setUsername(email_id.getText().toString().trim());


            Toast.makeText(context,getString(R.string.success_regist), Toast.LENGTH_SHORT).show();
        }

    }


    private void navigateFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment,ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);

        fragmentTransaction.commit();

    }




    private boolean isValidFields()
    {
        boolean b=false;
        if(email_id.getText().toString().trim().equals(""))
        {
            email_id.setError("Please Enter the Email Id");
            email_id.requestFocus();
            return b;

        }

        if(password.getText().toString().trim().equals("")){
            password.setError("Please Enter Password");
            password.requestFocus();
            return b;
        }

        if(confirmPassword.getText().toString().trim().equals("")){
            confirmPassword.setError("Please Enter Confirm Password");
            confirmPassword.requestFocus();
            return b;
        }
        else if (!isValidEmail(email_id.getText().toString().trim()))
        {
            Toast.makeText(getActivity(),"Email id format is not correct",Toast.LENGTH_SHORT).show();
            return b;

        }


        else if(password.getText().toString().trim().length()<6)
        {
            password.setError("Password should be minimum 6 characters");
            password.requestFocus();
            return b;
        }
        else if(security_question1.getText().toString().trim().equals(""))
        {
            security_question1.setError("Security Question 1 cannot be empty");
            security_question1.requestFocus();
            return b;
        }

        else if(security_question2.getText().toString().trim().equals(""))
        {
            security_question2.setError("Security Question 2 cannot be empty");
            security_question2.requestFocus();
            return b;
        }

        else if(security_question3.getText().toString().trim().equals(""))
        {
            security_question3.setError("Security Question 3 cannot be empty");
            security_question3.requestFocus();
            return b;
        }

        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {

            Toast.makeText(getActivity(),"Password should match Confirm Password",Toast.LENGTH_SHORT).show();
            return b;
        }

        b=true;
        return b;
    }


    private  final  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ContentValues addUsers(String username, String password, String security_ques1,
                                   String security_ques2, String security_ques3)
    {
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.USER_NAME, username);
        values.put(FeedReaderDbHelper.PASSWORD, password);
        values.put(FeedReaderDbHelper.SECURITY_Q_1, security_ques1);
        values.put(FeedReaderDbHelper.SECURITY_Q_2, security_ques2);
        values.put(FeedReaderDbHelper.SECURITY_Q_3, security_ques3);

        return values;

    }



    List<String> options;
    Dialog dialog;
    File file;

    private void selectOptions(final Context context) {


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
        content.setOnItemClickListener((adapterView, view, i, l) -> {

            if (adapterView.getItemAtPosition(i).equals(getString(R.string.def))) {

                UserIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_icon));


            } else if (adapterView.getItemAtPosition(i).equals(getString(R.string.camera))) {
                selectCameraOptions();
            } else {
                selectPicsFromGallery();

            }
            dialog.dismiss();
        });

        dialog.show();
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
                Constants.SELECT_PICTURE);
    }


    private void selectCameraOptions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.CAMERA_REQUEST_CODE);


        } else {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "test.jpg" + System.currentTimeMillis());
            Uri tempUri = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, Constants.CAMERA_CODE);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        // Log.i("TAG", "**Grant Results**=" + grantResults[0]+" "+grantResults[1]+" "+grantRes + "request code=" + requestCode);

        for (int grantResult : grantResults) {
            Logger.log(Level.DEBUG, ClassConstants.SIGNUP_FRAGMENT, "grant results[]=" + grantResult);

        }
        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    ) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg" + System.currentTimeMillis());
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, Constants.CAMERA_CODE);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED)

            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), getString(R.string.per_nec), Toast.LENGTH_SHORT).show();
                    showDialogOK("Camera and Write External Storage Permission required for this app for User Icon",
                            (dialog, which) -> {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                Constants.CAMERA_REQUEST_CODE);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        UserIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.user_icon));
                                        break;
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Go to settings And enable permissions", Toast.LENGTH_LONG).show();

                }


            }


        }


    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), okListener)
                .create()
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            userIconUri = uri.toString();

            NavigationUserIconUri = uri;
            if (uri != null){

            }
             loadImageWithGlide(uri.toString(), UserIcon);

        } else if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            String uri;
            if (file.exists()) {
                Log.i("**Absolute path**=", "" + file.getAbsolutePath());
                uri = file.getAbsolutePath();

                NavigationUserIconUri = Uri.parse(uri);

                userIconUri = "file://" + uri;
                 loadImageWithGlide(uri, UserIcon);


            } else {
                userIconUri = "no_image";
                NavigationUserIconUri = null;
                  UserIcon.setImageDrawable(this.getResources().getDrawable(R.drawable.user_icon));
            }


        } else if((requestCode==Constants.PATIENT_PROFILE_REQUEST_CODE || requestCode==Constants.PATIENT_PROFILE_CAMERA_CODE)&& resultCode==RESULT_OK){
            try{
                Fragment fragment =getActivity(). getSupportFragmentManager().findFragmentByTag(ClassConstants.PATIENT_PROFILE_FRAGMENT);
                if(fragment!=null){
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }




        else if((requestCode==Constants.HOSPITAL_PROFILE_REQUEST_CODE || requestCode==Constants.HOSPITAL_PROFILE_CAMERA_CODE)
                && resultCode==RESULT_OK){
            try{
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(ClassConstants.HOSPITAL_PROFILE_FRAGMENT);
                if(fragment!=null){
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    private void loadImageWithGlide(String uri, ImageView imageView) {


        //noinspection SpellCheckingInspection
        Glide
                .with(getActivity())
                .load(uri)
                .override(100, 100)
                .centerCrop()// resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);

                        Logger.log(Level.DEBUG, "----", "Glide loaded the image successfully");
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);

                        Logger.log(Level.ERROR, "------p", e.toString());
                    }
                });

    }



    private void saveCredentials(String userName,String uri, String TAG){
        // Set the global user Name

        StoreCredentialsFile.storeSignUpCredentials(getActivity(), userName, TAG);
        StoreCredentialsFile.store_profile_image(getActivity(), uri, TAG, userName);

        }
}
