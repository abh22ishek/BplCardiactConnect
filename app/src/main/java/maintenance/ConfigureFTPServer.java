package maintenance;

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

import cardiact.bpl.pkg.com.bplcardiactconnect.*;

public class ConfigureFTPServer extends Fragment {

   RelativeLayout urlBar,portBar,usernameBar,passwordBar,saveDirBar;
   RadioButton radioUrl,radioport,radioUserName,radioPassword,radioSaveDir;
   Button btnEdit,btnUpload;
   String  headerText;
    String server = "ftp://14.141.84.241/";
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

               // configFTPClient(user,pass);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                startActivity(browserIntent);
            }
        });


    }


    private void configFTPClient(String user, String password)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        FTPClient ftpClient = new FTPClient();

       //final String serverFinal=server;
        BufferedInputStream buffIn = null;

        String fileNameDirectory="BPL_CARDIART";

        File file =new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(),fileNameDirectory);
        String firstRemoteFile =file+"/"+ "Cardiart__LPP1_ECG.pdf";

        try {
            buffIn = new BufferedInputStream(new FileInputStream(firstRemoteFile));

            ftpClient.connect(server,port);
            ftpClient.login(user, password);
           // ftpClient.changeWorkingDirectory(serverRoad);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile("test.txt", buffIn);
            buffIn.close();
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
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
