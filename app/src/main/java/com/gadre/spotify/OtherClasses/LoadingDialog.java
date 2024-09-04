package com.gadre.spotify.OtherClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.gadre.spotify.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    // Constructor to initialize the activity
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    // Method to start and show the AlertDialog
    public void startAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    // Method to dismiss the AlertDialog
    public void closeAlertDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
