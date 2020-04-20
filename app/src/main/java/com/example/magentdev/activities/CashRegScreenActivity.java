package com.example.magentdev.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.SessionOperations;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.fragments.cash.CashFragment_main;
import com.example.magentdev.fragments.client.ClientFragment_main;
import com.example.magentdev.fragments.transfers.TransfersFragment_main;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class CashRegScreenActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    PrivateData pd;
    private BottomNavigationView bottom_nav;
    private NestedScrollView scroll_frame;
    private MaterialToolbar topAppbar;
    private CashFragment_main cash_fragment_main;
    private ClientFragment_main client_fragment_main;
    private TransfersFragment_main transfers_fragment_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_reg_screen);

        bottom_nav = findViewById(R.id.bottom_nav);
        scroll_frame = findViewById(R.id.nestedScrollView);
        topAppbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppbar);
        topAppbar.setBackground(getResources().getDrawable(R.drawable.top_app_bar_round));

        cash_fragment_main = new CashFragment_main();
        client_fragment_main = new ClientFragment_main();
        transfers_fragment_main = new TransfersFragment_main();

        setFragment(cash_fragment_main);


        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.nav_cash):
                        setFragment(cash_fragment_main);
                        topAppbar.setTitle(R.string.page_cash);
                        return true;

                    case (R.id.nav_client):
                        setFragment(client_fragment_main);
                        topAppbar.setTitle(R.string.page_client);
                        return true;

                    case (R.id.nav_transfers):
                        setFragment(transfers_fragment_main);
                        topAppbar.setTitle(R.string.page_transfers);
                        return true;

                    default:
                        return false;
                }
            }
        });


        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        try {
            pd = new PrivateData(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nestedScrollView, fragment);
        fragmentTransaction.commit();
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
                                    Intent intent = new Intent(CashRegScreenActivity.this, LoginActivity.class);
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


}

