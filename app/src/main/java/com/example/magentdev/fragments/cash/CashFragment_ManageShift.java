package com.example.magentdev.fragments.cash;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.magentdev.API_Operations.WsrCash_Information;
import com.example.magentdev.LoadingDialog;
import com.example.magentdev.PrivateData;
import com.example.magentdev.R;
import com.example.magentdev.RequestQueueSingleton;
import com.example.magentdev.ShiftDetails;
import com.example.magentdev.SoftKeyboardStateHelper;
import com.example.magentdev.VolleyCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */

public class CashFragment_ManageShift extends Fragment {
    private Spinner spinner;
    private Button addBtn, remBtn, openShiftBtn;
    private TextInputEditText tiAmt, tiObs;
    private TextInputLayout tilAmt;
    private TextView tvShiftDetails, tvShiftInfo;
    private HashMap<String, String[]> curr_map;
    private PrivateData pd;
    private RequestQueue requestQueue;
    private LoadingDialog loadingDialog;

    public CashFragment_ManageShift() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curr_map = new HashMap<>();
        ShiftDetails.setCurr_map((HashMap<String, String[]>) curr_map);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_cash_shiftdetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = Objects.requireNonNull(getView()).findViewById(R.id.accs_menulist);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()),R.array.curr_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addBtn = getView().findViewById(R.id.addBtn);
        remBtn = getView().findViewById(R.id.remBtn);
        openShiftBtn= getView().findViewById(R.id.openShiftBtn);
        tiAmt = getView().findViewById(R.id.tiAmount);
        tilAmt = getView().findViewById(R.id.tilAmount);
        tiObs = getView().findViewById(R.id.tiObs);
        tvShiftDetails = getView().findViewById(R.id.tvShiftDetails);
        tvShiftInfo = getView().findViewById(R.id.tvShiftInfo);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyboardStateHelper.hideKeyboardFrom(getContext(),getView());
                setShiftDetail();
            }
        });

        remBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurr();
            }
        });

        openShiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openShiftBtn.getText().toString().equals("Open Shift")){
                    Intent intent = new Intent(getContext(), Cash_OpenShiftActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), Cash_CloseShiftActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setShiftDetail(){
        if(tiAmt.getText().toString().equals("")) { CharSequence amtWarn = "Insert amount first"; tiAmt.setError(amtWarn); return;}
        String obs = Objects.requireNonNull(tiObs.getText()).toString();
        if (obs.equals("")) obs = "No observations";

        curr_map = ShiftDetails.getCurr_map();
        String[] values = {tiAmt.getText().toString(),obs};
        curr_map.put(spinner.getSelectedItem().toString(),values);
        ShiftDetails.setCurr_map((HashMap<String, String[]>) curr_map);
        Toast.makeText(getContext(),"Updated details.",Toast.LENGTH_SHORT).show();
        tiAmt.setText(null);
        tiObs.setText(null);
        ConstraintLayout cl = getView().findViewById(R.id.focusablelayout);
        cl.requestFocus();
        setDetailsTxt();
    }


    private void removeCurr(){
        List<String> curr_list = new ArrayList<>();
        for (Map.Entry entry : curr_map.entrySet()) {
            curr_list.add(entry.getKey().toString());
        }
        final String[] currency_list = curr_list.toArray(new String[0]);

        if(curr_list.size()==0) Toast.makeText(getActivity(),"No entries added yet.",Toast.LENGTH_SHORT).show();
        else{
            final String[] currency = {currency_list[0]};
            new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),  R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                    .setTitle("Select the currency to be removed.")
                    .setNegativeButton(R.string.cancel,null)
                    .setPositiveButton(R.string.accept2,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            curr_map.remove(currency[0]);
                            setDetailsTxt();
                        }
                    })
                    .setSingleChoiceItems(currency_list, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currency[0] = currency_list[which];
                        }
                    })
                    .show();
        }
    }

    private void setDetailsTxt(){
        StringBuilder shiftDetailsString = new StringBuilder("My Shift details:\n");

        for (String entry : curr_map.keySet()) {
            shiftDetailsString.append((Objects.requireNonNull(curr_map.get(entry)))[0]).append(" ").append(entry).append("\n");
        }
        tvShiftDetails.setText(shiftDetailsString.toString());

        if(tvShiftDetails.getText().toString().equals("My Shift details:\n")){
            openShiftBtn.setEnabled(false);
        }else{
            openShiftBtn.setEnabled(true);
        }
    }

    private void checkSavedDetails(){
        if(ShiftDetails.getCurr_map() != null){
            curr_map = ShiftDetails.getCurr_map();
            setDetailsTxt();
        }else{
            tvShiftDetails.setText("My Shift Detail:\n");
        }
    }

    private void checkShiftStatus() throws JSONException {
        final String dtk = pd.getDeviceToken();
        final String stk = pd.getSessionToken();
        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONObject s = response.getJSONObject("S");
                if(s.getInt("ECD") == 0){
                    String sid;
                    if(response.getJSONArray("CB").getJSONObject(0).getString("SBT").equals("C")){
                        openShiftBtn.setText("Open Shift");
                        tvShiftInfo.setText("Open Shift when all entries are inserted and verified");
                        sid = "-1";

                    }else{
                        openShiftBtn.setText("Close Shift");
                        tvShiftInfo.setText("Close Shift when all entries are inserted and verified");
                        sid = response.getJSONArray("CB").getJSONObject(0).getString("SID");
                    }
                    File file = getActivity().getApplicationContext().getFileStreamPath("SID");
                    if(file == null || !file.exists()) {
                        String filenameSID = "SID";
                        String fileContentSID = sid;
                        byte[] bSID = fileContentSID.getBytes(StandardCharsets.UTF_8);

                        try (FileOutputStream fos = getActivity().getApplicationContext().openFileOutput(filenameSID, getActivity().getApplicationContext().MODE_PRIVATE)) {
                            fos.write(bSID);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    loadingDialog.dismissDialog();
                }
            }
            @Override
            public void onError(String result) {
                System.out.println(result);
                loadingDialog.dismissDialog();
            }
        };
        WsrCash_Information.wsrCashierShiftList(dtk,stk,callback,requestQueue);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(ShiftDetails.getCurr_map());
        try {
            pd = new PrivateData(getContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        requestQueue = RequestQueueSingleton.getInstance(getContext()).getRequestQueue();
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startingDialog();

        try {
            checkShiftStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkSavedDetails();
    }

    @Override
    public void onPause() {
        super.onPause();

        tiAmt.setText("");
        tiObs.setText("");
    }
}
