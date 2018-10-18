package maintenance;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.*;

import cardiact.bpl.pkg.com.bplcardiactconnect.*;
import it.sauronsoftware.ftp4j.*;
import it.sauronsoftware.ftp4j.FTPClient;
import logger.*;

public class ConfigureFTPServer extends Fragment {

   RelativeLayout urlBar,portBar,usernameBar,passwordBar,saveDirBar;
   RadioButton radioUrl,radioport,radioUserName,radioPassword,radioSaveDir;
   Button btnEdit,btnUpload;
   String  headerText;
    String server = "14.141.84.241";
    int port = 21;
    String user = "testrnd";
    String pass = "r&d9876";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.configure_ftp,container,false);
        urlBar=view.findViewById(R.id.urlBar);
        portBar=view.findViewById(R.id.portBar);
        usernameBar=view.findViewById(R.id.userNameBar);
        passwordBar=view.findViewById(R.id.passwordBar);
        saveDirBar=view.findViewById(R.id.saveDirBar);

        radioUrl=view.findViewById(R.id.radioURL);
        radioport=view.findViewById(R.id.radioPort);
        radioUserName=view.findViewById(R.id.radioUserName);
        radioPassword=view.findViewById(R.id.radioPassword);
        radioSaveDir=view.findViewById(R.id.radioSaveDir);
        btnEdit=view.findViewById(R.id.btnEdit);
        btnUpload=view.findViewById(R.id.btnUpload);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        radioUrl.setChecked(true);
        headerText="FTP Server URL";
        urlBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radioUrl.setChecked(true);
                radioport.setChecked(false);
                radioUserName.setChecked(false);
                radioPassword.setChecked(false);
                radioSaveDir.setChecked(false);
                headerText="FTP Server URL";
            }
        });




        portBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioUrl.setChecked(false);
                radioport.setChecked(true);
                radioUserName.setChecked(false);
                radioPassword.setChecked(false);
                radioSaveDir.setChecked(false);

                headerText="FTP Server Port";
            }
        });



        usernameBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioUrl.setChecked(false);
                radioport.setChecked(false);
                radioUserName.setChecked(true);
                radioPassword.setChecked(false);
                radioSaveDir.setChecked(false);

                headerText="FTP Server UserName";
            }
        });


        passwordBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioUrl.setChecked(false);
                radioport.setChecked(false);
                radioUserName.setChecked(false);
                radioPassword.setChecked(true);
                radioSaveDir.setChecked(false);

                headerText="FTP Server Password";
            }
        });

        saveDirBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioUrl.setChecked(false);
                radioport.setChecked(false);
                radioUserName.setChecked(false);
                radioPassword.setChecked(false);
                radioSaveDir.setChecked(true);

                headerText="FTP Server Directory";
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(getActivity(),headerText);
            }
        });



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                if(isNetworkAvailable(getActivity()) &&isInternetAvailable() )
                    new UploadPDFFiles().execute(new String[] { isloaded});
                else
                    Toast.makeText(getActivity(),R.string.check_ineternet,Toast.LENGTH_SHORT).show();
               // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
               // startActivity(browserIntent);
            }
        });


    }

    public boolean isNetworkAvailable(Context context)
    {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
        return false;
    }

    public void uploadFile(File fileName) {


        it.sauronsoftware.ftp4j.FTPClient client = new it.sauronsoftware.ftp4j.FTPClient();

        try {

            client.connect(server, 21);
            try
            {
                client.login(user, pass);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(" Login Failed ...! ...");
            }
            finally {
                client.setType(FTPClient.TYPE_BINARY);
                try{
                    client.changeDirectory("/ms/");
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(" Client change directory issue  ...");
                }


                client.upload(fileName, new MyTransferListener());

            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {

            Logger.log(Level.DEBUG,getActivity().getPackageName()," Upload Started ...");

        }

        public void transferred(int length) {

            Logger.log(Level.DEBUG,getActivity().getPackageName()," transferred ..."+length);

        }

        public void completed() {

            //   btn.setVisibility(View.VISIBLE);
            // Transfer completed

            Logger.log(Level.DEBUG,getActivity().getPackageName()," Completed ...");

        }

        public void aborted() {


            Logger.log(Level.DEBUG,getActivity().getPackageName()," Aborted ...");

        }

        public void failed() {


            Logger.log(Level.DEBUG,getActivity().getPackageName()," Failed !!!!!!!! ...");

        }
    }

    ProgressDialog progressDialog;
    String isloaded = "false";


    @SuppressLint("StaticFieldLeak")
    private  class UploadPDFFiles extends AsyncTask<String, String, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please wait ......");

                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

            }

        }

        @Override
        protected String doInBackground(String... urls) {
            String fileNameDirectory = "BPL_CARDIART";

            File f = new File(android.os.Environment.getExternalStorageDirectory().
                    getAbsolutePath(), fileNameDirectory + "/" + "Cardiart__LPP1_ECG.pdf");

            // Upload sdcard file
            try{
                uploadFile(f);
                isloaded="true";
            }catch (Exception e)
            {
                isloaded="false";
                e.printStackTrace();
            }


            return isloaded;
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (isloaded.equalsIgnoreCase("true")) {
                    Toast.makeText(getActivity(),
                            "PDF  Successfully uploaded", Toast.LENGTH_SHORT).show();


                }

            }

        }
    }







    Dialog dialog;
    private void showDialog(final Context context,String headerText) {

        if (dialog == null) {
            dialog = new Dialog(context);
        }


        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.new_custom_dialog);
        dialog.setCancelable(true);

        final EditText content = dialog.findViewById(R.id.textEdit);
        final TextView header = dialog.findViewById(R.id.header);

        final Button OK=dialog.findViewById(R.id.btnOk);

        header.setText(headerText);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
