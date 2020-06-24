package com.example.magentdev.fragments.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.chaos.view.PinView;
import com.example.magentdev.API_Operations.WsrBtrn_Authorisation;
import com.example.magentdev.API_Operations.WsrBtrn_Control;
import com.example.magentdev.AgentOperation;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.UpdateCashierCash;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.fragments.cash.Cash_CloseShiftActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class Client_AuthReplyMatrixActivity extends AppCompatActivity {
    private PinView securityMatrixPv;
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,unlockBtn,delBtn,cnfBtn,decBtn;
    private PrivateData pd;
    private TextView matrixCoordsTv;
    RequestQueue requestQueue;
    private AgentOperation agentOperation;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_auth_reply_matrix);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.4), (int) (height*.55));
        getWindow().setBackgroundDrawableResource(R.drawable.round_corners);
        getWindow().setElevation(16);
        cnfBtn = findViewById(R.id.confirmSPinBtn);
        decBtn = findViewById(R.id.declineSPinBtn);

        loadingDialog = new LoadingDialog(Client_AuthReplyMatrixActivity.this);
        matrixCoordsTv = findViewById(R.id.insertMatrixTv);
        securityMatrixPv = findViewById(R.id.matrixPinPV);
        securityMatrixPv.setShowSoftInputOnFocus(false);
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
        agentOperation = (AgentOperation) getIntent().getExtras().getSerializable("AgentOperation");
        matrixCoordsTv.setText(getIntent().getExtras().getString("MatrixCoords"));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b0.getText()));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b1.getText()));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b2.getText()));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b3.getText()));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b4.getText()));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b5.getText()));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b6.getText()));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b7.getText()));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b8.getText()));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityMatrixPv.setText(securityMatrixPv.getText().insert(securityMatrixPv.getText().length(),b9.getText()));
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = securityMatrixPv.getText().toString().trim();
                if(str.length() !=0){
                    str  = str.substring( 0, str.length() - 1 );
                    securityMatrixPv.setText ( str );
                }
            }
        });

        cnfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkAuthReply();
                    loadingDialog.startingDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        System.out.println(response);
                        JSONObject s = response.getJSONObject("S");
                        if(s.getInt("ECD") == 0){
                            new MaterialAlertDialogBuilder(Client_AuthReplyMatrixActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                    .setTitle("Operation aborted.")
                                    .setBackgroundInsetStart(250)
                                    .setBackgroundInsetEnd(250)
                                    .setIcon(R.drawable.round_cancel_24)
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                    @Override
                    public void onError(String result) {
                        System.out.println(result);
                    }
                };
                try {
                    WsrBtrn_Control.wsrBtrnAbort(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),callback,requestQueue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void checkAuthReply() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    if(response.getString("BTA").equals("AR")){
                        securityMatrixPv.setText(null);
                        loadingDialog.dismissDialog();
                        new MaterialAlertDialogBuilder(Client_AuthReplyMatrixActivity.this,  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                .setTitle("Incorrect matrix coordinates. Try again.")
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

                    }else if(response.getString("BTA").equals("AC")){
                        validateCashOp();
                    }
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrBtrn_Authorisation.wsrBtrnAuthReply(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),securityMatrixPv.getText().toString(),callback,requestQueue);
    }

    public void validateCashOp() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    closeCashOp();
                    UpdateCashierCash.updateCash(agentOperation.getAmt(),agentOperation.getTransactionCurr(),agentOperation.getOperationType());
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrBtrn_Control.wsrBtrnValidate(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),callback,requestQueue);
    }

    private void closeCashOp() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                loadingDialog.dismissDialog();
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    finish();
                    Toast.makeText(Client_AuthReplyMatrixActivity.this,"Operation successfull", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
                loadingDialog.dismissDialog();
            }
        };
        WsrBtrn_Control.wsrBtrnClose(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),callback,requestQueue);
    }
}
