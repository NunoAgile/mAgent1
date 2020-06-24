package com.example.magentdev.fragments.cash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.magentdev.API_Operations.WsrCash_Information;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.UpdateCashierCash;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CashFragment_CashierContext extends Fragment {
    private TextView tvLatestShift, tvLatestOperation, tvAgentAssets, tvShiftAssets;
    private TextInputLayout tilShiftsList;
    private Button btnCheckDtls;
    private AutoCompleteTextView editTextFilledExposedDropdown;
    private PrivateData pd;
    private RequestQueue requestQueue;
    private LoadingDialog  loadingDialog;

    public CashFragment_CashierContext() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cash_cashier_context, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvLatestShift = getView().findViewById(R.id.tvLatestShift);
        tvLatestOperation = getView().findViewById(R.id.tvLatestOperation);
        tvAgentAssets = getView().findViewById(R.id.tvAgentAssets);
        tvShiftAssets = getView().findViewById(R.id.tvAgentSAssets);
        tilShiftsList = getView().findViewById(R.id.shiftslist_menulayout);
        editTextFilledExposedDropdown = getView().findViewById(R.id.filled_exposed_dropdown);
        btnCheckDtls = getView().findViewById(R.id.chkSftDtlsBtn);

        requestQueue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();
        try {
            pd = new PrivateData(getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            getShiftsList();
            getAgentAssets();
            loadingDialog = new LoadingDialog(getActivity());
            loadingDialog.startingDialog();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getShiftsList() throws JSONException {
        final String dtk = pd.getDeviceToken();
        final String stk = pd.getSessionToken();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    JSONArray cb = response.getJSONArray("CB");
                    String operationType = cb.getJSONObject(0).getString("SBT");
                    List<String> list = new ArrayList<>();
                    StringBuilder shiftCashString = new StringBuilder("My Shift assets:\n");
                    if(operationType.equals("C")) operationType = "Closed";
                    else operationType = "Open";

                    for(int i = 0; i < cb.length(); i++){
                        JSONObject cbItem = cb.getJSONObject(i);
                        list.add(cbItem.getString("SBD").replace("T"," "));
                        if(operationType.equals("Open")){
                            if(UpdateCashierCash.getCash_map() == null){
                                if(i == 0){
                                    System.out.println(i);
                                    HashMap<String, Integer> cash_map = new HashMap<String, Integer>();
                                    for(int j =0; j < cbItem.getJSONArray("AC").length(); j++){
                                        System.out.println(j);
                                        int amt = cbItem.getJSONArray("AC").getJSONObject(j).getJSONObject("CA").getInt("AMT");
                                        cash_map.put(cbItem.getJSONArray("AC").getJSONObject(j).getJSONObject("CA").getString("CRR"),amt);
                                        UpdateCashierCash.setCash_map(cash_map);
                                    }

                                }
                            }
                        }
                    }
                    HashMap<String, Integer> cash_map = UpdateCashierCash.getCash_map();
                    if(cash_map != null){
                        for(String entry:cash_map.keySet()){
                            System.out.println(entry);
                            shiftCashString.append(cash_map.get(entry)).append(" ").append(entry).append("\n");
                        }
                    }





                    String latestShift = tvLatestShift.getText().toString() + " " + response.getJSONArray("CB").getJSONObject(0).getString("SBD").replace("T"," ") + " | " + operationType;
                    tvLatestShift.setText(latestShift);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, list);
                    tilShiftsList.setEnabled(true);
                    tvShiftAssets.setText(shiftCashString);
                    editTextFilledExposedDropdown.setText(list.get(0));
                    editTextFilledExposedDropdown.setAdapter(adapter);
                    loadingDialog.dismissDialog();


                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrCash_Information.wsrCashierShiftList(dtk,stk,callback,requestQueue);
    }

    private void getAgentAssets() throws JSONException {
        final String dtk = pd.getDeviceToken();
        final String stk = pd.getSessionToken();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                HashMap<String, Integer> hash_map = new HashMap<String, Integer>();
                if(s.getInt("ECD") == 0){
                    JSONArray ac = response.getJSONArray("AC");
                    for(int i = 0; i < ac.length(); i++){
                        JSONObject acItem = ac.getJSONObject(i).getJSONObject("CA");
                        if(hash_map.get(acItem.getString("CRR")) == null){
                            hash_map.put(acItem.getString("CRR"),acItem.getInt("AMT"));
                        }else{
                            hash_map.put(acItem.getString("CRR"),acItem.getInt("AMT")+hash_map.get(acItem.getString("CRR")));
                        }
                    }
                    StringBuilder assetsString = new StringBuilder("My assets total:\n");
                    for (String entry : hash_map.keySet()) {
                        assetsString.append(hash_map.get(entry)).append(" ").append(entry).append("\n");
                    }
                    tvAgentAssets.setText(assetsString);
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrCash_Information.wsrCashierCashBalance(dtk,stk,callback,requestQueue);
    }
}
