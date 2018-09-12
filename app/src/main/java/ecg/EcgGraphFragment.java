package ecg;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;


import cardiact.bpl.pkg.com.bplcardiactconnect.R;
import login.fragment.*;

public class EcgGraphFragment extends Fragment {


  LoginActivityListner loginActivityListner;


  LinearLayout first4,mid4,last4;
  int count=0;
  Button next,previous;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivityListner= (LoginActivityListner) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.ecg_graph,container,false);
        first4=view.findViewById(R.id.first4);
        mid4=view.findViewById(R.id.mid4);
        last4=view.findViewById(R.id.last4);
        next=view.findViewById(R.id.Next);
        previous=view.findViewById(R.id.Previous);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginActivityListner.onDataPass(EcgGraphFragment.class.getName());


        previous.setVisibility(View.INVISIBLE);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=count-5;

                if(count==5){
                 next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.VISIBLE);

                    first4.setVisibility(View.GONE);
                    last4.setVisibility(View.GONE);
                    mid4.setVisibility(View.VISIBLE);
                }else if(count==0)
                {
                    previous.setVisibility(View.GONE);
                    first4.setVisibility(View.VISIBLE);
                    last4.setVisibility(View.GONE);
                    mid4.setVisibility(View.GONE);
                }

                else if(count==10){
                    previous.setVisibility(View.VISIBLE);

                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count=count+5;

                if(count==5){
                    previous.setVisibility(View.VISIBLE);
                    first4.setVisibility(View.GONE);
                    mid4.setVisibility(View.VISIBLE);

                }else if(count==10)
                {
                   next.setVisibility(View.GONE);
                    previous.setVisibility(View.VISIBLE);
                    first4.setVisibility(View.GONE);
                    mid4.setVisibility(View.GONE);
                    last4.setVisibility(View.VISIBLE);

                }else if(count==15){
                   next.setVisibility(View.INVISIBLE);
                   previous.setVisibility(View.VISIBLE);
                   count=0;
                }

            }
        });





    }







}
