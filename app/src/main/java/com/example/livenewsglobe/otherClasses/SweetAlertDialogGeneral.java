package com.example.livenewsglobe.otherClasses;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.livenewsglobe.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogGeneral {
    SweetAlertDialog sweetDialog;
    Context context;
    Button btn,btn2;
    public SweetAlertDialogGeneral(Context context)
    {
        this.context=context;
    }

    public SweetAlertDialog showSweetAlertDialog(String setDialogType,String setText)
    {
        if(setDialogType.equals("success"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
        }
        else if(setDialogType.equals("warning"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        }
        else if(setDialogType.equals("error"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
        }
        else if(setDialogType.equals("progress"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        }

        sweetDialog.setTitleText(setText);
//        sweetDialog.setContentText("You clicked the button!");
        sweetDialog.setCanceledOnTouchOutside(true);
        sweetDialog.show();

        Button btn = (Button) sweetDialog.findViewById(R.id.confirm_button);
        btn.setVisibility(View.GONE);

        return sweetDialog;
    }

}
