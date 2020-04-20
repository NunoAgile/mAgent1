package com.example.magentdev.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chaos.view.PinView;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.SessionOperations;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

public class QuickPinActivity extends AppCompatActivity {


    private PinView quickPinPV;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button unlockBtn;
    private Button delBtn;
    PrivateData pd;
    RequestQueue requestQueue;
    int tryCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pin);

        quickPinPV = findViewById(R.id.quickpinPV);
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

        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        // close keyboard on startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        createKeypad();
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b0.getText()));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b1.getText()));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b2.getText()));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b3.getText()));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b4.getText()));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b5.getText()));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b6.getText()));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b7.getText()));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b8.getText()));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickPinPV.setText(quickPinPV.getText().insert(quickPinPV.getText().length(),b9.getText()));
            }
        });

        checkPinUpdate();
        try {
            pd = new PrivateData(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkPinUpdate() {

        quickPinPV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(quickPinPV.length() == 4){
                   tryCount++;
                    if(quickPinPV.getText().toString().equals(pd.getQuickpin())){
                        Intent intent = new Intent(QuickPinActivity.this, CashRegScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        quickPinPV.setText(null);
                        hideKeyboard(QuickPinActivity.this);
                        Snackbar.make(quickPinPV, getResources().getString(R.string.wrong_pin), Snackbar.LENGTH_LONG).show();
                        if(tryCount == 3) {
                            pinTimeout();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void pinTimeout() {
        try {
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    deleteFile("RTK");
                    deleteFile("STK");
                    deleteFile("FP");
                    deleteFile("PIN");
                    Intent intent = new Intent(QuickPinActivity.this, LoginActivity.class);
                    intent.putExtra("activity_code", "QuickPin_Timeout");
                    startActivity(intent);
                }

                @Override
                public void onError(String result) {
                    System.out.println(result);
                }
            };
            SessionOperations.wsrAuthSessionClose(pd.getDeviceToken(),pd.getSessionToken(),callback,requestQueue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onBackPressed(){
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.close_alert_title)
                .setMessage(R.string.close_alert_text)
                .setNegativeButton(R.string.decline, null)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            final VolleyCallback callback = new VolleyCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    deleteFile("RTK");
                                    deleteFile("STK");
                                    deleteFile("PIN");
                                    deleteFile("FP");
                                    Intent intent = new Intent(QuickPinActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(String result) {
                                    System.out.println(result);
                                }
                            };
                            SessionOperations.wsrAuthSessionClose(pd.getDeviceToken(),pd.getSessionToken(),callback,requestQueue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setIcon(R.drawable.round_exit_to_app_24)
                .show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Quick Pin destruido");
    }
}
