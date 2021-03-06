package com.example.magentdev.fragments.cash;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.chaos.view.PinView;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.ShiftDetails;
import com.example.magentdev.API_Operations.WsrCash_Agent;
import com.example.magentdev.UpdateCashierCash;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Cash_CloseShiftActivity extends Activity {
    private PinView securityPinPv;
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,unlockBtn,delBtn,cnfBtn,decBtn;
    private PrivateData pd;
    RequestQueue requestQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_cash_secpin);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.4), (int) (height*.55));
        getWindow().setBackgroundDrawableResource(R.drawable.round_corners);
        getWindow().setElevation(16);
        cnfBtn = findViewById(R.id.confirmSPinBtn);
        decBtn = findViewById(R.id.declineSPinBtn);

        securityPinPv = findViewById(R.id.securityPinPV);
        securityPinPv.setShowSoftInputOnFocus(false);
        b1 = findViewById(R.id.n1);
        b2 = findViewById(R.id.n2);
        b3 = findViewById(R.id.n3);
        b4 = findViewById(R.id.n4);
        b5 = findViewById(R.id.n5);
        b6 = findViewById(R.id.n6);
        b7 = findViewById(R.id.n7);
        b8 = findViewById(R.id.n8);
        b9 = findViewById(R.id.n9);
        b0 = findViewById(R.id.n0);
        unlockBtn = findViewById(R.id.unlockBtn);
        delBtn = findViewById(R.id.delBtn);
        try {
            pd = new PrivateData(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        createKeypad();
        checkPinUpdate();

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b0.getText()));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b1.getText()));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b2.getText()));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b3.getText()));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b4.getText()));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b5.getText()));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b6.getText()));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b7.getText()));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b8.getText()));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityPinPv.setText(securityPinPv.getText().insert(securityPinPv.getText().length(),b9.getText()));
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = securityPinPv.getText().toString().trim();
                if(str.length() !=0){
                    str  = str.substring( 0, str.length() - 1 );
                    securityPinPv.setText ( str );
                }
            }
        });

        cnfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeShift();
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(Cash_CloseShiftActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Operation cancelled.")
                        .setBackgroundInsetStart(250)
                        .setBackgroundInsetEnd(250)
                        .setIcon(R.drawable.round_cancel_24)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        })
                        .show();
            }
        });

    }
    private void createKeypad(){
        String[] nums = {"0","1","2","3","4","5","6","7","8","9"};
        Collections.shuffle(Arrays.asList(nums));
        for(int i = 0; i < nums.length; i++ ){
            if(i == 0) b0.setText(nums[i]);
            else if(i == 1) b1.setText(nums[i]);
            else if(i == 2) b2.setText(nums[i]);
            else if(i == 3) b3.setText(nums[i]);
            else if(i == 4) b4.setText(nums[i]);
            else if(i == 5) b5.setText(nums[i]);
            else if(i == 6) b6.setText(nums[i]);
            else if(i == 7) b7.setText(nums[i]);
            else if(i == 8) b8.setText(nums[i]);
            else b9.setText(nums[i]);
        }
    }

    private void checkPinUpdate() {

        securityPinPv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(securityPinPv.length() == 6){
                    cnfBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void closeShift(){
        String sha256pin = Hashing.sha256()
                .hashString(securityPinPv.getText().toString(), StandardCharsets.UTF_8)
                .toString();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject S = response.getJSONObject("S");
                    System.out.println(response);
                    if(S.getInt("ECD") == 0){
                        ShiftDetails.setCurr_map(new HashMap<String, String[]>());
                        UpdateCashierCash.setCash_map(new HashMap<String, String[]>());
                        String sid = "-1";
                        String filenameSID = "SID";
                        String fileContentSID = sid;
                        byte[] bSID = fileContentSID.getBytes(StandardCharsets.UTF_8);

                        try (FileOutputStream fos = getApplicationContext().openFileOutput(filenameSID, getApplicationContext().MODE_PRIVATE)) {
                            fos.write(bSID);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finish();
                        Toast.makeText(Cash_CloseShiftActivity.this, "Shift successfully closed.", Toast.LENGTH_SHORT).show();
                    }else if(S.getInt("ECD") == 312){
                        new MaterialAlertDialogBuilder(Cash_CloseShiftActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                .setTitle("Incorrect security PIN.")
                                .setBackgroundInsetStart(250)
                                .setBackgroundInsetEnd(250)
                                .setIcon(R.drawable.round_cancel_24)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        securityPinPv.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        try {
            WsrCash_Agent.wsrShiftClose(pd.getDeviceToken(),pd.getSessionToken(),sha256pin, ShiftDetails.getCurr_map(),Integer.parseInt(pd.getSid()),callback,requestQueue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
