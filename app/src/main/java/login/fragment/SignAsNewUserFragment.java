package login.fragment;

import android.content.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import custom.view.*;
import database.*;
import logger.*;

/**
 * Created by abhishekraj on 13/07/18.
 */

public class SignAsNewUserFragment extends Fragment {



    private TextView signUpFresh;
    private EditText userId,password;

    LoginActivityListner loginActivityListner;
    ImageView proceed;

    ViewFlipper viewFlipper;

    ImageButton left,right;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.log_out ,container,false);
        signUpFresh=view.findViewById(R.id.signUpFresh);

        password=view.findViewById(R.id.password);
        userId=view.findViewById(R.id.userId);
        proceed=view.findViewById(R.id.proceed);

        viewFlipper = view.findViewById(R.id.linearParams);

        left=view.findViewById(R.id.left);
        right=view.findViewById(R.id.right);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
        loginActivityListner.OnCurrentFragment(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);


        signUpFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigateToSignUpFreshFragment();
            }
        });



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginPass(userId,password);
            }
        });


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewFlipper.showNext();
                getCurrentViewFlipperID(getActivity());
            }
        });


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               viewFlipper.showNext();
            }
        });



        ShowViewFlipper(viewFlipper, getActivity());
    }


    private void navigateToSignUpFreshFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        SignUpFragment signUpFragment = new SignUpFragment();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment, ClassConstants.SIGNUP_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.SIGNUP_FRAGMENT);

        fragmentTransaction.commit();


    }




    private void loginPass(EditText userId, EditText password)
    {
        if (userId.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.email_n_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.pwd_n_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId.getText().toString() != "" && password.getText().toString() != "") {



            // call the database query
            DatabaseManager.getInstance().openDatabase();
            if (DatabaseManager.getInstance().Login(userId.getText().toString().trim(),
                    password.getText().toString().trim())) {


                navigateFragment();
            } else {
                Toast.makeText(getActivity(), "Invalid Password and UserId", Toast.LENGTH_SHORT).show();
                return;
            }




        }
    }



    private void navigateFragment(){

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        PatientMenuTrackFragment patientMenuTrackFragment = new PatientMenuTrackFragment();

        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        fragmentTransaction.replace(R.id.fragmentContainer,patientMenuTrackFragment, ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.PATIENT_MENU_TRACK_FRAGMENT);
        fragmentTransaction.commit();

    }



    RoundedImageView roundedImageView;

    private List<String> mapUserIDICon(Context context) {


        List<String> userList = new ArrayList<>();
        List<String> userIconsUri = new ArrayList<>();
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);


        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            userList.add(entry.getKey());
            userIconsUri.add(entry.getValue().toString());


        }


        return userIconsUri;

    }

    private void ShowViewFlipper(ViewFlipper viewFlipper, Context context) {
        List<String> UriList = mapUserIDICon(getActivity());
        Uri uri;

        for (int i = 0; i < UriList.size(); i++) {
            roundedImageView = new RoundedImageView(context);
            uri = Uri.parse(UriList.get(i));
            loadImageWithGlide(uri.toString(), roundedImageView);
            roundedImageView.setId(i);
            viewFlipper.addView(roundedImageView);

        }


    }


    public void getCurrentViewFlipperID(Context context) {

        int index = -1;
        List<String> UriList = mapUserIDICon(getActivity());


        for (int i = 0; i < UriList.size(); i++) {

            if (i == viewFlipper.getCurrentView().getId()) {
                index = i;
            }
        }

        if (index == -1) {
            // failed to determine the right index
            Log.w("imagen1", "Could not determine the right index!");
            return;
        }

        Uri uri = Uri.parse(UriList.get(index));
        Logger.log(Level.DEBUG, "====", "--GET URI from current View Flipper---" + uri);


        getCurrentUserIdFromCurrentViewFlipper(uri.toString(), context);

    }


    private String getCurrentUserIdFromCurrentViewFlipper(String uriAsValue, Context context) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);

        String currentUserId = "";

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            if (uriAsValue.equals(entry.getValue())) {
                currentUserId = entry.getKey();
                break;
            }


        }
        Logger.log(Level.DEBUG, "---)))))))", "--Get current UserID mapped from Current Icons display--=" + currentUserId);
        userId.setText(currentUserId);
        return currentUserId;
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

}
