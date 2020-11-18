package com.example.livenewsglobe.models;

import android.content.Context;

import com.example.livenewsglobe.R;
import com.kaopiz.kprogresshud.KProgressHUD;

public class ProgressDialog {

    public KProgressHUD progressDialogVar;
    public String title;

    public ProgressDialog(String title) {
        this.title = title;
    }

    public void startDialog(Context context){
        progressDialogVar = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setCancellable(false)
                .setBackgroundColor(context.getResources().getColor(R.color.spinnerSelectedIconColor))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }


}
