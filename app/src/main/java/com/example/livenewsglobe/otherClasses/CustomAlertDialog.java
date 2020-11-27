package com.example.livenewsglobe.otherClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;

import eightbitlab.com.blurview.BlurView;

public class CustomAlertDialog {
    Dialog dialog;
    MainActivity context;
    Button btnLogin,btnSignUp;
    TextView tvLogin,tvSignUp;
    EditText userName,email,password;
    Animation animation;
    View dialogTab3;
    ColorStateList oldColors;

    public CustomAlertDialog(Context context) {
        this.context = (MainActivity) context;
        initialize();
    }
    public void initialize()
    {
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_login_signup_dialog);
        dialog.setCanceledOnTouchOutside(false);

        //Set the background of the dialog's root view to transparent, because Android puts your dialog layout within a root view that hides the corners in your custom layout.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLogin=dialog.findViewById(R.id.login);
        tvSignUp=dialog.findViewById(R.id.signUp);
        btnLogin=dialog.findViewById(R.id.btn_login);
        userName=dialog.findViewById(R.id.username);
        email=dialog.findViewById(R.id.email);
        password=dialog.findViewById(R.id.password);
        btnSignUp=dialog.findViewById(R.id.btn_signup);
        dialogTab3=dialog.findViewById(R.id.dialog_tab);
//        lineAtSignUp=dialog.findViewById(R.id.line_at_signup);
        oldColors =  tvSignUp.getTextColors();



    }
    public void clickListener()
    {
        context.blurView.setVisibility(View.VISIBLE);
        dialog.show();
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO)
                {
                    context.blurView.setVisibility(View.GONE);
                    dialog.dismiss();
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.blurView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.blurView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation = AnimationUtils.loadAnimation(context,
                        R.anim.alert_bar_move_right);
                dialogTab3.setAnimation(animation);

                tvSignUp.setTextColor(Color.parseColor("#103E65"));
                tvLogin.setTextColor(oldColors);

                userName.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = AnimationUtils.loadAnimation(context,
                        R.anim.alert_bar_move_left);
                dialogTab3.setAnimation(animation);

                tvLogin.setTextColor(Color.parseColor("#103E65"));
                tvSignUp.setTextColor(oldColors);

                userName.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });
    }
}
