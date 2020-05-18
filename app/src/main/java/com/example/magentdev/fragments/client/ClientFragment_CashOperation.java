package com.example.magentdev.fragments.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.magentdev.API_Operations.WsrBtrn_Attributes;
import com.example.magentdev.API_Operations.WsrBtrn_Authorisation;
import com.example.magentdev.API_Operations.WsrBtrn_Control;
import com.example.magentdev.API_Operations.WsrCash_Client;
import com.example.magentdev.AgentOperation;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.VolleyCallback;
import com.example.magentdev.fragments.cash.Cash_OpenShiftActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ClientFragment_CashOperation extends Fragment {

    private AgentOperation agentOperation;
    private TextView slctdAccTv, amtCurrTv;
    private TextInputLayout tilCashAmt, tilAuthMethod;
    private TextInputEditText tiAmtInput;
    private AutoCompleteTextView authMethodList;
    private MaterialButton doCashOpBtn;
    private ImageButton goBackIV;
    private MaterialToolbar tb;
    private RequestQueue requestQueue;
    private PrivateData pd;
    private LoadingDialog loadingDialog;

    public ClientFragment_CashOperation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        agentOperation = (AgentOperation) bundle.getSerializable("AgentOperation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_withdraw, container, false);
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
        loadingDialog = new LoadingDialog(getActivity());
        slctdAccTv = getView().findViewById(R.id.selAccTv);
        slctdAccTv.setText(slctdAccTv.getText().toString() + "\n" + agentOperation.getClientAccName());
        amtCurrTv = getView().findViewById(R.id.amtCurrTv);
        amtCurrTv.setText(agentOperation.getTransactionCurr());
        tilCashAmt = getView().findViewById(R.id.tilCWAmount);
        tiAmtInput = getView().findViewById(R.id.tiCWAmountCW);
        tilAuthMethod = getView().findViewById(R.id.auth_menulistLayout);
        authMethodList = getView().findViewById(R.id.filled_exposed_dropdown);
        doCashOpBtn = getView().findViewById(R.id.doCashOpBtn);
        doCashOpBtn.setText(agentOperation.getOperationName());
        goBackIV = getView().findViewById(R.id.goBackIV);

        tb = getActivity().findViewById(R.id.topAppBar);
        tb.setTitle("Client - " + agentOperation.getOperationName());

        authMethodList.setText("Matrix Card");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()),R.array.auth_array,R.layout.dropdown_menu_popup_item);
        authMethodList.setAdapter(adapter);

        doCashOpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiAmtInput.getText().length() > 0){
                    loadingDialog.startingDialog();
                    tiAmtInput.setError(null);
                    try {
                        if(authMethodList.getText().toString().equals("Matrix Card")) wsrBtrnOpen();
                        else{loadingDialog.dismissDialog();
                            Toast.makeText(getContext(),"Not yet supported",Toast.LENGTH_LONG).show();}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    tiAmtInput.setError("Please insert amount first.");
                }

            }
        });

        goBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStackImmediate();
                tb.setTitle("Client");
            }
        });
    }

    private void wsrBtrnOpen() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    agentOperation.setTid(response.getInt("TID"));
                    wsrCashLink();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrBtrn_Control.wsrBtrnOpen(pd.getDeviceToken(),pd.getSessionToken(),"AC",agentOperation.getTransactionCurr(), callback, requestQueue);
    }

    private void wsrCashLink() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    wsrBtrnThirdParty();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrCash_Client.wsrCashLink(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getSid(),agentOperation.getTid(),agentOperation.getOperationType(),callback,requestQueue);
    }

    private void wsrBtrnThirdParty() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    executeCashOperation();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrBtrn_Attributes.wsrBtrnThirdParty(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),"TC",agentOperation.getClientAccGID(),callback,requestQueue);
    }

    private void executeCashOperation() throws JSONException {
            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) throws JSONException {
                    System.out.println(response);
                    JSONObject s = response.getJSONObject("S");
                    if(s.getInt("ECD") == 0){
                        computeCashOp();
                    }
                }
                @Override
                public void onError(String result) {
                    System.out.println(result);
                }
            };
            WsrCash_Client.wsrCashWithdraw(pd.getDeviceToken(),pd.getSessionToken(),Integer.parseInt(pd.getSid()),agentOperation.getTid(), Objects.requireNonNull(tiAmtInput.getText()).toString(),callback,requestQueue);
    }

    private void computeCashOp() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    cashOpAuthRequest();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        WsrBtrn_Control.wsrBtrnCompute(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),callback,requestQueue);
    }

    private void cashOpAuthRequest() throws JSONException {
       String authType = authMethodList.getText().toString();
       if(authType.equals("Matrix Card")){
           final VolleyCallback callback = new VolleyCallback() {
               @Override
               public void onSuccess(JSONObject response) throws JSONException {
                   System.out.println(response);
                   JSONObject s = response.getJSONObject("S");
                   if(s.getInt("ECD") == 0){
                       loadingDialog.dismissDialog();
                       String matrixCoords = response.getString("AMP");
                       Intent intent = new Intent(getContext(), Client_AuthReplyMatrixActivity.class);
                       Bundle mBundle = new Bundle();
                       mBundle.putSerializable("AgentOperation",agentOperation);
                       mBundle.putString("MatrixCoords",matrixCoords);
                       intent.putExtras(mBundle);
                       startActivity(intent);
                   }
               }
               @Override
               public void onError(String result) {
                   System.out.println(result);
               }
           };
           WsrBtrn_Authorisation.wsrBtrnAuthRequest(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getTid(),callback,requestQueue);
       }else Toast.makeText(getContext(), "Selected auth type not supported yet.", Toast.LENGTH_SHORT).show();
    }
}
