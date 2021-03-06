package login.fragment;

import android.annotation.*;
import android.content.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;


import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.*;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

import java.util.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import constants.*;
import logger.*;

@SuppressWarnings("SpellCheckingInspection")
public class WelcomeUserFragment extends Fragment {


    private LoginActivityListner loginActivityListner;
    private TextView welcomeText,signUpFresh;
    private ImageView proceed;

    private TextView signAsOtherUser;

    ImageView icon,hospitalImage;
    TextView hospName,department_name;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner = (LoginActivityListner) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityListner.OnCurrentFragment(ClassConstants.WELCOME_USER_FRAGMENT);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.welcome_user, container, false);
        welcomeText=view.findViewById(R.id.textView2);
        proceed=view.findViewById(R.id.proceed);
        signUpFresh=view.findViewById(R.id.signUpFresh);
        hospName=view.findViewById(R.id.hospName);
        department_name=view.findViewById(R.id.departName);
        hospitalImage=view.findViewById(R.id.hospitalImage);
        signAsOtherUser=view.findViewById(R.id.signAsOtherUser);
        icon=view.findViewById(R.id.pixel);

        return view;
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginActivityListner.onDataPass(ClassConstants.WELCOME_USER_FRAGMENT);


        if (null != getArguments().getString(Constants.USER_NAME)) {

            mUsername = getArguments().getString(Constants.USER_NAME);
            welcomeText.setText("Welcome "+mUsername);
            Constants.Logged_User_ID=mUsername;

        }



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
        signUpFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSignUpFreshFragment();

            }
        });



        signAsOtherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSignUpNewUserFragment();
            }
        });



        display_image();


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateFragment();
            }
        });
    }

    private String mUsername = "";


    private void display_image() {

        if (!Objects.equals(get_profile_image(mUsername), "") || !get_profile_image(mUsername).equals("")) {


            Uri uri = Uri.parse(get_profile_image(mUsername));

            Logger.log(Level.DEBUG, ClassConstants.WELCOME_USER_FRAGMENT, "Uri=" + uri);
            if (uri != null) {
              //  loginActivityListner.displayImage(uri);
                loadImageWithGlide(uri.toString(),icon);

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
                        // Add custom code



                    }
                });

    }

    private String get_profile_image(String key_username)
    {
        SharedPreferences profile_image;
        profile_image = getActivity().getSharedPreferences(Constants.PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE);


        String image_str=profile_image.getString(key_username,"");
        Logger.log(Level.INFO, ClassConstants.WELCOME_USER_FRAGMENT, "get profile image from shared pref=" + image_str);

        return image_str;

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


    private void navigateToSignUpNewUserFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        SignAsNewUserFragment signUpFragment = new SignAsNewUserFragment();

        Bundle bundle =new Bundle();
        bundle.putString(Constants.USER_NAME,"");
        signUpFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment, ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
        fragmentTransaction.addToBackStack(ClassConstants.SIGN_AS_NEW_USER_FRAGMENT);
        fragmentTransaction.commit();


    }

    String hosName,departName,hospInitials, savedUriHosp;


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