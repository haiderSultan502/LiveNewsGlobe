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
import android.widget.Toast;

import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.models.Register;
import com.example.livenewsglobe.models.RegisterUser;
import com.example.livenewsglobe.models.States;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAlertDialog {
    Dialog dialog;
    MainActivity context;
    Button btnLogin,btnSignUp;
    TextView tvLogin,tvSignUp;
    EditText userName,email,password;
    Animation animation;
    View dialogTab3;
    ColorStateList oldColors;
    public static Boolean alertDialogState=false;

    String userNameStr,emailStr,passwordStr;

    public CustomAlertDialog(Context context) {
        this.context = (MainActivity) context;
        initialize();
    }
    public void initialize()
    {
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.custom_login_signup_dialog);
        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);

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
        alertDialogState=true;

        context.blurView.setVisibility(View.VISIBLE);
        dialog.show();
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO)
                {
                    context.blurView.setVisibility(View.GONE);
                    dialog.dismiss();
                    alertDialogState=false;
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.blurView.setVisibility(View.GONE);
                dialog.dismiss();
                alertDialogState=false;
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.blurView.setVisibility(View.GONE);
                userNameStr = userName.getText().toString();
                emailStr = email.getText().toString();
                passwordStr = password.getText().toString();

//                if(userNameStr.length() == 0 || emailStr.length() == 0 || passwordStr.length() == 0)
//                {
//                    Toast.makeText(context, "Please fill the fields", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/wp/v2/");
                    Call<Register> call= interfaceApi.registerUser(userNameStr,emailStr,passwordStr);
                    call.enqueue(new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if(!response.isSuccessful())
                            {

                                Toast.makeText(context, "Code  "+response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            {
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    dialog.dismiss();
                alertDialogState=false;
//                }

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
