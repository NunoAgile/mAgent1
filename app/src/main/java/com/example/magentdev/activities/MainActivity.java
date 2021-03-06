package com.example.magentdev.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.API_Operations.WsrAuth_Session;
import com.example.magentdev.SoftKeyboardStateHelper;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.fragments.cash.CashFragment_CashierContext;
import com.example.magentdev.fragments.cash.CashFragment_ManageShift;
import com.example.magentdev.fragments.client.ClientFragment_main;
import com.example.magentdev.fragments.transfers.TransfersFragment_main;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    PrivateData pd;
    private MaterialToolbar topAppbar;
    private CashFragment_ManageShift cashFragment_manageShift;
    private CashFragment_CashierContext cashFragment_cashierContext;
    private ClientFragment_main client_fragment_main;
    private TransfersFragment_main transfers_fragment_main;
    private TabLayout tabLayout;
    private RelativeLayout rl;
    private int selectedItemId;
    private int selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        final BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav);
        topAppbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppbar);
        topAppbar.setBackgroundResource(R.drawable.top_app_bar_round);
        tabLayout = findViewById(R.id.tabs);
        rl = findViewById(R.id.relativeTeste);
        cashFragment_manageShift = new CashFragment_ManageShift();
        cashFragment_cashierContext = new CashFragment_CashierContext();
        client_fragment_main = new ClientFragment_main();
        transfers_fragment_main = new TransfersFragment_main();


        setFragment(cashFragment_cashierContext);
        tabLayout.addTab(tabLayout.newTab().setText("Cashier Context"));
        tabLayout.addTab(tabLayout.newTab().setText("My Shift"));

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.nav_cash):
                        setFragment(cashFragment_cashierContext);
                        topAppbar.setTitle(R.string.page_cash);
                        if(tabLayout.getTabCount() > 0){
                            tabLayout.removeAllTabs();
                        }
                        tabLayout.addTab(tabLayout.newTab().setText("Cashier Context"));
                        tabLayout.addTab(tabLayout.newTab().setText("My Shift"));
                        return true;

                    case (R.id.nav_client):
                        setFragment(client_fragment_main);
                        topAppbar.setTitle(R.string.page_client);
                        if(tabLayout.getTabCount() > 0){
                            tabLayout.removeAllTabs();
                        }
                        return true;

                    case (R.id.nav_transfers):
                        setFragment(transfers_fragment_main);
                        topAppbar.setTitle(R.string.page_transfers);
                        if(tabLayout.getTabCount() > 0){
                            tabLayout.removeAllTabs();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                switch(tabText){
                    case("Cashier Context"):
                        setFragment(cashFragment_cashierContext);
                        return;
                    case("My Shift"):
                        setFragment(cashFragment_manageShift);
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        try {
            pd = new PrivateData(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(getApplicationContext(), findViewById(R.id.relativeTeste));
        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                bottom_nav.setElevation(0);
                bottom_nav.setBackgroundColor(Color.parseColor("#f5f7f7"));
                for(int i = 0; i < bottom_nav.getMenu().size(); i++){
                    if(bottom_nav.getMenu().getItem(i).getIcon() != null) {
                        bottom_nav.getMenu().getItem(i).setIcon(null);
                        bottom_nav.getMenu().getItem(i).setEnabled(false);
                        bottom_nav.getMenu().getItem(i).setTitle("");
                    }
                }
            }

            @Override
            public void onSoftKeyboardClosed() {
                bottom_nav.setElevation(16);
                bottom_nav.setBackgroundColor(Color.parseColor("#f7fafa"));
                bottom_nav.getMenu().getItem(0).setIcon(R.drawable.baseline_account_balance_24).setEnabled(true).setTitle("Cash");
                bottom_nav.getMenu().getItem(2).setIcon(R.drawable.baseline_supervisor_account_24).setEnabled(true).setTitle("Client");
                bottom_nav.getMenu().getItem(4).setIcon(R.drawable.baseline_sync_alt_24).setEnabled(true).setTitle("Transfers");

            }
        };
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nestedScrollView, fragment);
        fragmentTransaction.addToBackStack(null);
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
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(String result) {
                                    System.out.println(result);
                                }
                            };
                            WsrAuth_Session.wsrAuthSessionClose(pd.getDeviceToken(),pd.getSessionToken(),callback,requestQueue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setIcon(R.drawable.round_exit_to_app_24)
                .show();
    }



}

