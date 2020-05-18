package com.example.magentdev.fragments.client;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.example.magentdev.API_Operations.WsrAux;
import com.example.magentdev.AgentOperation;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.API_Operations.WsrAinf;
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
    private RequestQueue requestQueue;
    private PrivateData pd;
    private TextInputEditText tiGIDInput;
    private TextInputLayout tilGIDInput, tilAccList;
    private MaterialButton findGIDBtn, gotoDepositBtn, gotoWithdrawBtn, gotoMovementsBtn;
    private TextView userFoundTv;
    private AutoCompleteTextView editTextFilledExposedDropdown;
    private List<String> gidList = new ArrayList<>();
    private int listIndex = 0;
    private LoadingDialog loadingDialog;
    private AgentOperation agentOperation;

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
        loadingDialog = new LoadingDialog(getActivity());

        findGIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiGIDInput.getText().toString().equals("")){
                    tilGIDInput.setError("Insert document identifier first");
                }else{
                    loadingDialog.startingDialog();
                    tilGIDInput.setError(null);
                    try {
                        wsrQryEntityGID();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        gotoWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiGIDInput.getText().toString().equals("")){
                    tilGIDInput.setError("Insert document identifier first");
                }else if(editTextFilledExposedDropdown.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Search for the document and select an account first.",Toast.LENGTH_LONG).show();
                }else{
                    tiGIDInput.setError(null);
                    String selectedAccountGID = gidList.get(listIndex);
                    String operationType = "CW";
                    String operationName = "Withdraw";
                    String[] selectedAccInfo = editTextFilledExposedDropdown.getText().toString().split(" ");
                    agentOperation = new AgentOperation(selectedAccountGID,operationType,operationName,selectedAccInfo[0],selectedAccInfo[2],Integer.parseInt(pd.getSid()));


                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AgentOperation",agentOperation);
                    Fragment clientWithdraw = new ClientFragment_CashOperation();
                    clientWithdraw.setArguments(bundle);
                    ft.addToBackStack(null);
                    ft.replace(R.id.nestedScrollView, clientWithdraw);
                    ft.commit();
                }
            }
        });

        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listIndex = position;
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
                       userFoundTv.setText("Username here");
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
                       loadingDialog.dismissDialog();
                   }
                }
            }

            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrAux.wsrQryEntityGID(dtk, stk, did, callback, requestQueue);
    }

    private void wsrIAccountList(String dtk, String stk, String gid) throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray ao = response.getJSONArray("AO");
                List<String> list = new ArrayList<>();
                for(int i = 0; i < ao.length(); i++){
                    JSONObject aoItem = ao.getJSONObject(i);
                    String nameToShow;

                    if(aoItem.getString("APN").equals("") || aoItem.getString("APN").equals("null")) nameToShow = aoItem.getString("AHI");
                    else nameToShow = aoItem.getString("APN");

                    String currType = aoItem.getString("ACT");

                    list.add(nameToShow + " | " + currType);
                    gidList.add(aoItem.getString("AGI"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, list);

                tilAccList.setEnabled(true);
                editTextFilledExposedDropdown.setText(list.get(0));
                editTextFilledExposedDropdown.setAdapter(adapter);
                loadingDialog.dismissDialog();
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrAinf.wsrIAccountList(dtk,stk,gid,callback,requestQueue);
    }



    @Override
    public void onResume() {
        super.onResume();
        editTextFilledExposedDropdown.setText("");
    }

}
