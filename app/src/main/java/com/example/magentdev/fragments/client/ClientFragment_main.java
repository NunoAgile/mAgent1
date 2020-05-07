package com.example.magentdev.fragments.client;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.example.magentdev.AuxOperations;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.activities.AssetsInfoOperations;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment_main extends Fragment {
    RequestQueue requestQueue;
    PrivateData pd;
    private TextInputEditText tiGIDInput;
    private TextInputLayout tilGIDInput, tilAccList;
    private MaterialButton findGIDBtn, gotoDepositBtn, gotoWithdrawBtn, gotoMovementsBtn;
    private TextView userFoundTv;
    private AutoCompleteTextView editTextFilledExposedDropdown;

    public ClientFragment_main() {
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
        return inflater.inflate(R.layout.fragment_client_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();
        try {
            pd = new PrivateData(getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tiGIDInput = getView().findViewById(R.id.tiGIDInput);
        tilGIDInput = getView().findViewById(R.id.tilGIDInput);
        findGIDBtn = getView().findViewById(R.id.findgidBtn);
        gotoDepositBtn = getView().findViewById(R.id.gotoDepositBtn);
        gotoWithdrawBtn = getView().findViewById(R.id.gotoWithdrawBtn);
        gotoMovementsBtn = getView().findViewById(R.id.gotoMovementsBtn);
        userFoundTv = getView().findViewById(R.id.userFoundTv);
        editTextFilledExposedDropdown = getView().findViewById(R.id.filled_exposed_dropdown);
        tilAccList = getView().findViewById(R.id.accs_menulistLayout);

        findGIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiGIDInput.getText().toString().equals("")){
                    tilGIDInput.setError("Insert document identifier first");
                }else{
                    tilGIDInput.setError("");
                    try {
                        wsrQryEntityGID();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void wsrQryEntityGID() throws JSONException {
        final String dtk = pd.getDeviceToken();
        final String stk = pd.getSessionToken();
        String did = Objects.requireNonNull(tiGIDInput.getText()).toString();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    JSONArray ei = response.getJSONArray("EI");
                   if(ei.length() > 0){
                       JSONObject eiItem = ei.getJSONObject(0);
                       String gid = eiItem.getString("GID");
                       wsrIAccountList(dtk,stk,gid);
                   }else{
                       userFoundTv.setText("Invalid ID");
                       new MaterialAlertDialogBuilder(getContext(),  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                               .setTitle("User not found")
                               .setBackgroundInsetStart(250)
                               .setBackgroundInsetEnd(250)
                               .setIcon(R.drawable.round_cancel_24)
                               .show();
                       tilAccList.setEnabled(false);
                       editTextFilledExposedDropdown.setText("");
                   }
                }
            }

            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        AuxOperations.wsrQryEntityGID(dtk, stk, did, callback, requestQueue);
    }

    private void wsrIAccountList(String dtk, String stk, String gid) throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray ao = response.getJSONArray("AO");
                List<String> list = new ArrayList<String>();
                for(int i = 0; i < ao.length(); i++){
                    JSONObject aoItem = ao.getJSONObject(i);
                    String nameToShow;
                    try{
                        if(aoItem.getString("APN").equals("")) nameToShow = aoItem.getString("AHI");
                        else nameToShow = aoItem.getString("APN");
                    }catch(org.json.JSONException exception){
                        nameToShow = aoItem.getString("AHI");
                    }
                    String currType = aoItem.getString("ACT");
                    list.add(nameToShow + " | " + currType);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, list);

                tilAccList.setEnabled(true);
                editTextFilledExposedDropdown.setText(list.get(0));
                editTextFilledExposedDropdown.setAdapter(adapter);
                // TODO: FIQUEI AQUI, TA O SPINNER FEITO
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        AssetsInfoOperations.wsrIAccountList(dtk,stk,gid,callback,requestQueue);
    }
}
