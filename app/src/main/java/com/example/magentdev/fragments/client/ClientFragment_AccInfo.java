package com.example.magentdev.fragments.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.magentdev.API_Operations.WsrAinf;
import com.example.magentdev.AgentOperation;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.MovementDetails;
import com.example.magentdev.MyAdapter;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientFragment_AccInfo extends Fragment {

    private RequestQueue requestQueue;
    private PrivateData pd;
    private LoadingDialog loadingDialog;
    private AgentOperation agentOperation;
    private List<MovementDetails> movList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MaterialToolbar tb;
    private ImageView goBackIV;
    private TextView accNumTv, accBalanceTv, accOwnerTv, accCurrTv, accNameTv;

    public ClientFragment_AccInfo() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_acc_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recyclerView);
        requestQueue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();
        accBalanceTv = getView().findViewById(R.id.accBalanceTv);
        accCurrTv = getView().findViewById(R.id.accCurrencyTv);
        accNameTv = getView().findViewById(R.id.accNameTv);
        accNumTv = getView().findViewById(R.id.accNumTv);
        accOwnerTv = getView().findViewById(R.id.accOwnerTv);
        try {
            pd = new PrivateData(getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loadingDialog = new LoadingDialog(getActivity());

        try {
            getMovementDetailsList();
            fillDetails();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tb = getActivity().findViewById(R.id.topAppBar);
        tb.setTitle("Client - " + agentOperation.getOperationName());

        goBackIV = getView().findViewById(R.id.goBackIV2);
        goBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStackImmediate();
                tb.setTitle("Client");
            }
        });
    }



    private void getMovementDetailsList() throws JSONException {
        loadingDialog.startingDialog();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                String fulldate = "";
                if(s.getInt("ECD") == 0){
                    JSONArray array = response.getJSONArray("AM");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject item = array.getJSONObject(i);
                        String mds = item.getString("MDS");
                        String mst = item.getString("MST");
                        String vdt = item.getString("VDT");
                        double mvl = item.getDouble("MVL");

                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
                        String[] dateValue = vdt.split(" ");
                        try {
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateValue[0]);
                            String dateFormated = dateFormat.format(date1);
                            Date date2 = new SimpleDateFormat("HH:mm").parse(dateValue[1]);
                            String timeFormated = timeFormat.format(date2);
                            System.out.println(timeFormated);
                            fulldate = dateFormated +" "+ timeFormated;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




                        movList.add(new MovementDetails(mvl,mst,fulldate,mds));
                    }
                    setupRv(movList);
                }
            }

            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        System.out.println(agentOperation.getClientAccGID());
        WsrAinf.wsrIAccountMovements(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getClientAccGID(),callback,requestQueue);
    }

    private void setupRv(List<MovementDetails> movlist) {
        MyAdapter myAdapter = new MyAdapter(getContext(),movlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
        loadingDialog.dismissDialog();
    }

    private void fillDetails() throws JSONException {
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray ao = response.getJSONArray("AO");
                for(int i = 0; i < ao.length(); i++){
                    JSONObject aoItem = ao.getJSONObject(i);
                    System.out.println(agentOperation.getClientAccGID());
                    if(aoItem.getString("AGI").equals(agentOperation.getClientAccGID())){
                        accNumTv.setText(accNumTv.getText() + " " + aoItem.getString("AHI"));
                        accCurrTv.setText(accCurrTv.getText() + " " + agentOperation.getTransactionCurr());
                        accBalanceTv.setText(accBalanceTv.getText() + " " + String.valueOf(aoItem.getJSONObject("AB").getDouble("BVL")));
                        if(aoItem.getString("APN").equals("") || aoItem.getString("APN").equals("null")) accNameTv.setText(accNameTv.getText() + " " + "Not defined by the client.");
                        else accNameTv.setText(accNameTv.getText() + " " + agentOperation.getClientAccName());
                    }
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
            }
        };
        System.out.println(agentOperation.getClientAccGID());
        WsrAinf.wsrIAccountList(pd.getDeviceToken(),pd.getSessionToken(),agentOperation.getClientAccGID(),callback,requestQueue);
    }

}
