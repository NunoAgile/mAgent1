package com.example.magentdev.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;


import com.example.magentdev.LoadingDialog;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.API_Operations.WsrAuth_Session;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameET;
    private TextInputEditText passwordET;
    private TextInputEditText pin1ET;
    private TextInputEditText pin2ET;
    private TextInputLayout usernameTIL;
    private TextInputLayout passwordTIL;
    private TextInputLayout pin1TIL;
    private TextInputLayout pin2TIL;
    private Button loginBtn;
    private String dtk;
    private String rtk;
    private String stk;
    private boolean hasDtk;
    private SwitchMaterial touchIDSw;
    private LoadingDialog loadingDialog;


    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // close keyboard on startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        usernameET = findViewById(R.id.tiUsername);
        passwordET = findViewById(R.id.tiPw);
        pin1ET = findViewById(R.id.tiPIN);
        pin2ET = findViewById(R.id.tiPINC);
        loginBtn = findViewById(R.id.btnLogin);
        usernameTIL = findViewById((R.id.tilUsername));
        passwordTIL = findViewById(R.id.tilPw);
        pin1TIL = findViewById(R.id.tilPIN);
        pin2TIL = findViewById(R.id.tilPINC);
        touchIDSw = findViewById(R.id.switchMaterial3);

        loadingDialog = new LoadingDialog(LoginActivity.this);
        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();

        try {
            checkDevice();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        checkFields();
        checkIntent();

    }

    public void checkFields() {

        usernameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (usernameET.getText().toString().trim().equals("")) {
                        CharSequence usrWarn = "Insert username";
                        usernameTIL.setError(usrWarn);
                    }
                } else {
                    usernameTIL.setError("");

                }
                checkAll();
            }
        });
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAll();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (passwordET.getText().toString().trim().equals("")) {
                        CharSequence pwWarn = "Insert password";
                        passwordTIL.setError(pwWarn);

                    }
                } else {
                    passwordTIL.setError("");

                }
                checkAll();
            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAll();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pin1ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (pin1ET.getText().toString().trim().equals("")) {
                        CharSequence pin1Warn = "Insert PIN";
                        pin1TIL.setError(pin1Warn);

                    } else if (pin1ET.getText().toString()
                            .length() < 4) {
                        CharSequence pin1Warn2 = "PIN must be 4-digit long";
                        pin1TIL.setError(pin1Warn2);

                    }
                } else {
                    pin1TIL.setError("");

                }
                checkAll();
            }
        });
        pin1ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAll();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pin2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (pin2ET.getText().toString().trim().equals("")) {
                        CharSequence pin2Warn = "Insert PIN";
                        pin2TIL.setError(pin2Warn);

                    } else if (!pin2ET.getText().toString().equals(pin1ET.getText()
                            .toString()) || pin1ET.getText().toString()
                            .length() < 4) {
                        CharSequence pin2Warn2 = "PINs don't match";
                        pin2TIL.setError(pin2Warn2);

                    }
                } else {
                    pin2TIL.setError("");

                }
                checkAll();
            }
        });
        pin2ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pin2ET.getText().toString().trim().equals("")) {
                    CharSequence pin2Warn = "Insert PIN";
                    pin2TIL.setError(pin2Warn);
                } else if (!pin2ET.getText().toString().equals(pin1ET.getText()
                        .toString()) || pin1ET.getText().toString()
                        .length() < 4) {
                    CharSequence pin2Warn2 = "PINs don't match";
                    pin2TIL.setError(pin2Warn2);

                }else{
                    pin2TIL.setError("");

                }
                checkAll();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public boolean checkAll(){
        if (!usernameET.getText().toString().trim().equals("")) {
            if (!passwordET.getText().toString().trim().equals("")) {
                if (!pin1ET.getText().toString().trim().equals("")) {
                    if (!pin2ET.getText().toString().trim().equals("") && pin1ET.getText().toString()
                            .equals(pin2ET.getText().toString()) ) {
                        loginBtn.setEnabled(true);
                        loginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                authReg();
                                loadingDialog.startingDialog();
                            }
                        });
                        return true;
                    }
                }
            }
        }
        loginBtn.setEnabled(false);
        return false;
    }

    private void checkDevice() throws FileNotFoundException {
        String[] files = getApplicationContext().fileList();
        if(files.length > 0){
            for (int i = 0; i < files.length; i++){
                if(files[i].equals("RTK")){
                    Intent intent = new Intent(LoginActivity.this, QuickPinActivity.class);
                    startActivity(intent);
                    return;
                }
            }
            FileInputStream fis = getApplicationContext().openFileInput("DTK");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                dtk = stringBuilder.toString();
                dtk = dtk.replace("\n","");
                hasDtk = true;
            }
        }else{
           hasDtk = false;
        }
    }

    private void authReg() {
        try {
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONObject S = response.getJSONObject("S");
                        if(S.getInt("ECD") == 0){
                            if(!hasDtk){
                                dtk = response.getString("DTK");
                                String filenameDTK = "DTK";
                                String fileContentDTK = dtk;
                                byte[] bDTK = fileContentDTK.getBytes(StandardCharsets.UTF_8);

                                try (FileOutputStream fos = getApplicationContext().openFileOutput(filenameDTK, getApplicationContext().MODE_PRIVATE)) {
                                    fos.write(bDTK);
                                }
                            }
                            rtk = response.getString("RTK");
                            String filenameRTK = "RTK";
                            String fileContentRTK = rtk;
                            byte[] bRTK = fileContentRTK.getBytes(StandardCharsets.UTF_8);
                            try (FileOutputStream fos = getApplicationContext().openFileOutput(filenameRTK, getApplicationContext().MODE_PRIVATE)) {
                                fos.write(bRTK);
                            }
                            String filenamePIN = "PIN";
                            String fileContentPIN = pin1ET.getText().toString();
                            byte[] bPIN = fileContentPIN.getBytes(StandardCharsets.UTF_8);
                            try (FileOutputStream fos = getApplicationContext().openFileOutput(filenamePIN,getApplicationContext().MODE_PRIVATE)){
                                fos.write(bPIN);
                            }
                            openSession();

                        }else if(S.getInt("ECD") == 300){
                            new MaterialAlertDialogBuilder(LoginActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                    .setTitle("Wrong Username or Password")
                                    .setBackgroundInsetStart(250)
                                    .setBackgroundInsetEnd(250)
                                    .setIcon(R.drawable.round_cancel_24)
                                    .show();
                        }
                        loadingDialog.dismissDialog();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {
                    System.out.println(result);
                }
            };
            WsrAuth_Session.wsrAuthReg(hasDtk,usernameET.getText().toString(),passwordET.getText().toString(),dtk,callback,requestQueue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void openSession() {
        try {
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONObject S = response.getJSONObject("S");
                        if(S.getInt("ECD") == 0){
                            stk = S.getString("STK");
                            String filenameSTK = "STK";
                            String fileContentSTK = stk;
                            byte[] bSTK = fileContentSTK.getBytes(StandardCharsets.UTF_8);
                            try (FileOutputStream fos = getApplicationContext().openFileOutput(filenameSTK, getApplicationContext().MODE_PRIVATE)) {
                                fos.write(bSTK);
                            }
                            String filenameFP = "FP";
                            String fileContentFP = String.valueOf(touchIDSw.isChecked());
                            byte[] bFP = fileContentFP.getBytes(StandardCharsets.UTF_8);
                            try (FileOutputStream fos = getApplicationContext().openFileOutput(filenameFP, getApplicationContext().MODE_PRIVATE)) {
                                fos.write(bFP);
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(S.getInt("ECD") == -1){
                            new MaterialAlertDialogBuilder(LoginActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                    .setTitle("Unexpected error. Please try again later or contact support team.")
                                    .setBackgroundInsetStart(250)
                                    .setBackgroundInsetEnd(250)
                                    .setIcon(R.drawable.round_cancel_24)
                                    .show();
                        }
                        loadingDialog.dismissDialog();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(String result) {
                    System.out.println(result);
                    loadingDialog.dismissDialog();
                }
            };
            WsrAuth_Session.wsrAuthSessionOpen(dtk,rtk,callback,requestQueue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkIntent(){
        Intent intent = getIntent();
        if(intent.getStringExtra("activity_code") != null){
            String previousActivity = intent.getStringExtra("activity_code");
            if(previousActivity.equals("QuickPin_Timeout")){
                Snackbar.make(loginBtn,R.string.max_attempt,Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
