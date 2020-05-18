package com.example.magentdev;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

     public void startingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
