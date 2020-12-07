package com.example.livenewsglobe.otherClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.models.Register;
import com.example.livenewsglobe.models.RegisterUser;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.github.andreilisun.swipedismissdialog.OnCancelListener;
import com.github.andreilisun.swipedismissdialog.OnSwipeDismissListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDirection;
import com.google.android.material.behavior.SwipeDismissBehavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAlertDialog {
    MainActivity context;
    Button btnLogin,btnSignUp;
    TextView tvLogin,tvSignUp,tvForgotPassword;
    EditText userName,email,password;
    Animation animation;
    View dialogTab3;
    ColorStateList oldColors;
    SwipeDismissDialog swipeDismissDialog;
    static String userNameStr,valid_email,passwordStr;
    View dialog;

    public CustomAlertDialog(Context context) {
        this.context = (MainActivity) context;
    }
    public void initialize()
    {

        tvLogin=dialog.findViewById(R.id.login);
        tvSignUp=dialog.findViewById(R.id.signUp);
        tvForgotPassword=dialog.findViewById(R.id.tv_forgot_password);
        btnLogin=dialog.findViewById(R.id.btn_login);
        userName=dialog.findViewById(R.id.username);
        userName.requestFocus();
        email=dialog.findViewById(R.id.email);
        password=dialog.findViewById(R.id.password);
        btnSignUp=dialog.findViewById(R.id.btn_signup);
        dialogTab3=dialog.findViewById(R.id.dialog_tab);
//        lineAtSignUp=dialog.findViewById(R.id.line_at_signup);
        oldColors =  tvSignUp.getTextColors();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Is_Valid_Email(email); // pass your EditText Obj here.
            }
            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else {
                    valid_email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        });
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userNameStr = userName.getText().toString();
                if(userNameStr.length() < 3)
                {
                    userName.setError("At least Enter 3 characters");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordStr = password.getText().toString();
                if (passwordStr.length() < 4)
                {
                    password.setError("Password must contain at least 4 characters.");
                }
            }
        });


    }
    public void showDialog()
    {

        dialog = LayoutInflater.from(context).inflate(R.layout.custom_login_signup_dialog, null);
        initialize();
        context.blurView.setVisibility(View.VISIBLE);

        swipeDismissDialog = new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(View view) {
                        context.blurView.setVisibility(View.GONE);
                    }
                })
                .setOnSwipeDismissListener(new OnSwipeDismissListener() {
                    @Override
                    public void onSwipeDismiss(View view, SwipeDismissDirection direction) {
                        context.blurView.setVisibility(View.GONE);
                    }
                })
                .setFlingVelocity(0.01f)
                .build()
                .show();

        viewClickListener();

    }
    public void viewClickListener()
    {

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO)
                {
                    context.blurView.setVisibility(View.GONE);
                    swipeDismissDialog.dismiss();
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(email.getText().toString().length() > 11 && password.getText().toString().length() >= 3)
                {
                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/custom-plugin/");
                    Call<Register> call= interfaceApi.loginUsre(valid_email,passwordStr);
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
                                context.blurView.setVisibility(View.GONE);
                                swipeDismissDialog.dismiss();
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    //remove it later
                    context.blurView.setVisibility(View.GONE);
                    swipeDismissDialog.dismiss();


                }
                else
                {
                    Toast.makeText(context, "Please fill the fields correctly", Toast.LENGTH_SHORT).show();
                }

                clearEditText();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(userName.getText().toString().length() >= 2 && email.getText().toString().length() > 11 && password.getText().toString().length() >= 3)
                {
                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/wp/v2/");
                    Call<Register> call= interfaceApi.registerUser(userNameStr,valid_email,passwordStr);
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
                                context.blurView.setVisibility(View.GONE);
                                swipeDismissDialog.dismiss();
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    //remove it later
                    context.blurView.setVisibility(View.GONE);
                    swipeDismissDialog.dismiss();

                }
                else
                {
                    Toast.makeText(context, "Please fill the fields correctly", Toast.LENGTH_SHORT).show();
                }

//                clearEditText();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvForgotPassword.setVisibility(View.GONE);

                animation = AnimationUtils.loadAnimation(context,
                        R.anim.alert_bar_move_right);
                dialogTab3.setAnimation(animation);

                tvSignUp.setTextColor(Color.parseColor("#103E65"));
                tvLogin.setTextColor(oldColors);

                clearEditText();

                userName.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvForgotPassword.setVisibility(View.VISIBLE);

                animation = AnimationUtils.loadAnimation(context,
                        R.anim.alert_bar_move_left);
                dialogTab3.setAnimation(animation);

                tvLogin.setTextColor(Color.parseColor("#103E65"));
                tvSignUp.setTextColor(oldColors);

                clearEditText();

                userName.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });

    }

    private void callWhenLoadDialog() {
//        animation = AnimationUtils.loadAnimation(context,
//                R.anim.alert_bar_move_left);
//        dialogTab3.setAnimation(animation);

        tvLogin.setTextColor(Color.parseColor("#103E65"));
        tvSignUp.setTextColor(oldColors);

        clearEditText();
        userName.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }
    private void clearEditText()
    {
        userName.getText().clear();
        userName.setError(null);
        email.getText().clear();
        email.setError(null);
        password.getText().clear();
        password.setError(null);
    }
}
