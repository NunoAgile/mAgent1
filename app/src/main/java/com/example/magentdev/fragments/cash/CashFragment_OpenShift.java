package com.example.magentdev.fragments.cash;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magentdev.R;
import com.example.magentdev.ShiftDetails;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */

public class CashFragment_OpenShift extends Fragment {
    private Spinner spinner;
    private Button addBtn, remBtn, openShiftBtn;
    private TextInputEditText tiAmt, tiObs;
    private TextView tvShiftDetails;
    private Map<String, String[]> curr_map;

    public CashFragment_OpenShift() {
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

        return inflater.inflate(R.layout.fragment_cash_open_shift, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("Criei");
        spinner = Objects.requireNonNull(getView()).findViewById(R.id.accs_menulist);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()),R.array.curr_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addBtn = getView().findViewById(R.id.addBtn);
        remBtn = getView().findViewById(R.id.remBtn);
        openShiftBtn= getView().findViewById(R.id.openShiftBtn);
        tiAmt = getView().findViewById(R.id.tiAmount);
        tiObs = getView().findViewById(R.id.tiObs);
        tvShiftDetails = getView().findViewById(R.id.tvShiftDetails);


        curr_map = new HashMap<>();
        openShiftBtn.setEnabled(false);



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getContext(), Cash_cashConfirmActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setShiftDetail(){
        if(tiAmt.getText().toString().equals("")) { CharSequence amtWarn = "Insert amount first"; tiAmt.setError(amtWarn); return;}
        String obs = Objects.requireNonNull(tiObs.getText()).toString();
        if (obs.equals("")) obs = "No observations";

        String[] values = {tiAmt.getText().toString(),obs};
        curr_map.put(spinner.getSelectedItem().toString(),values);
        ShiftDetails.setCurr_map((HashMap<String, String[]>) curr_map);
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


}
