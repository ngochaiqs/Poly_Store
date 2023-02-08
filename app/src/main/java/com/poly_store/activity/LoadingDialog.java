package com.poly_store.activity;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.poly_store.R;

class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    LoadingDialog (Activity myActivity) {
        activity = myActivity;
    }
    void startLoadingDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder (activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }
    void dismissDialog () {
        dialog.dismiss();
    }
}
