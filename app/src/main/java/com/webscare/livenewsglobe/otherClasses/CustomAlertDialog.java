package com.webscare.livenewsglobe.otherClasses;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.webscare.livenewsglobe.Interface.InterfaceApi;
import com.webscare.livenewsglobe.MainActivity;
import com.webscare.livenewsglobe.R;
import com.webscare.livenewsglobe.models.Login.LoginModel;
import com.webscare.livenewsglobe.models.RegisterUser;
import com.github.andreilisun.swipedismissdialog.OnCancelListener;
import com.github.andreilisun.swipedismissdialog.OnSwipeDismissListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDirection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAlertDialog extends AppCompatActivity {
    MainActivity context;
    Button btnLogin,btnSignUp;
    TextView tvLogin,tvSignUp,tvForgotPassword,clearpassword;
    EditText userName,email,password;
    Animation animation;
    View dialogTab3;
    ColorStateList oldColors;
    SwipeDismissDialog swipeDismissDialog;
    static String userNameStr,valid_email,passwordStr;
    String passwordStr2,passwordStr3;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    SweetAlertDialog sweetAlertDialog;
    View dialog;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public CustomAlertDialog(Context context) {
        this.context = (MainActivity) context;
    }


    public void initialize()
    {

        tvLogin=dialog.findViewById(R.id.login);
//        clearpassword = dialog.findViewById(R.id.clear_password);
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
        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(context);

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
                }
                else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                }
                else {
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
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                passwordStr = password.getText().toString();
//
//                if (passwordStr.length() < 4)
//                {
//                    password.setError("Password must contain at least 4 characters.");
//                }
//            }
//        });

    }

    public void showDialog()
    {
        try {
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
        catch (Exception e)
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }



    }

    public void viewClickListener()
    {
        try {
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

//            clearpassword.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    passwordStr2 = password.getText().toString();
//
//                    if (passwordStr2.length() > 0) {
//                        passwordStr3 = passwordStr2.substring(0,passwordStr2.length() - 1);
//                        password.setText(passwordStr3);
//                        password.setSelection(password.getText().length());
//                    }
//                    else
//                    {
//
//                    }
//
//                }
//            });



            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
////                pDialog.getProgressHelper().setBarColor(Color.parseColor("#103E65"));
//                pDialog.setTitleText("Loading");
//                pDialog.setCancelable(true);
//                pDialog.setCanceledOnTouchOutside(true);
//                pDialog.show();

                    //from there .. .. .

                    sweetAlertDialog = sweetAlertDialogGeneral.showSweetAlertDialog("progress","Loading");

                    if(email.getText().toString().length() > 11 && password.getText().toString().length() >= 3)
                    {
                        InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/custom-plugin/");
                        passwordStr = password.getText().toString();
                        Call<LoginModel> call= interfaceApi.loginUsre(valid_email,passwordStr);
                        call.enqueue(new Callback<LoginModel>() {
                            @Override
                            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                if(!response.isSuccessful())
                                {

                                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try again later");
                                    return;
                                }
                                else
                                {
                                    LoginModel loginModel = response.body();

                                    SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(context);
                                    sharedPrefereneceManager.setLoginStatus(true);
                                    sharedPrefereneceManager.setUserName(loginModel.getData().getUserNicename());
                                    sharedPrefereneceManager.setUserEmail(loginModel.getData().getUserEmail());
                                    sharedPrefereneceManager.setPassword(password.getText().toString());
                                    sharedPrefereneceManager.setUserId(loginModel.getID());

                                    context.setUserNameAndEmail(sharedPrefereneceManager.getUserName(),sharedPrefereneceManager.getUserEmail());

                                    context.replaceFragment();

                                    int id = loginModel.getID();
                                    context.user_id= loginModel.getID();
                                    context.userEmail = loginModel.getData().getUserEmail();
                                    context.checkLoginStatus=true;

                                    context.blurView.setVisibility(View.GONE);
                                    swipeDismissDialog.dismiss();

                                    sweetAlertDialog.dismissWithAnimation();

                                    sweetAlertDialog = sweetAlertDialogGeneral.showSweetAlertDialog("success","Successfully Login");

                                    MainActivity.tvSignIn.setText("Sign out");

//                                    context.checkGetandSetUserprofile();



//                                SweetAlertDialog pDialogs = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
//                                pDialogs.setTitleText("Successfully Login");
//                                pDialogs.setCustomImage(R.drawable.loading);
//                                pDialogs.setCancelable(true);
//                                pDialogs.setCanceledOnTouchOutside(true);
//                                pDialogs.show();


//                                Toast.makeText(context, "Successfulyy Login", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<LoginModel> call, Throwable t) {
                                sweetAlertDialogGeneral.showSweetAlertDialog("error","user not exist");
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });

                        //remove it later
//                    context.blurView.setVisibility(View.GONE);
//                    swipeDismissDialog.dismiss();


                    }
                    else
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please fill the fields correctly");
                        sweetAlertDialog.dismissWithAnimation();
                    }

                    clearEditText();



                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sweetAlertDialog = sweetAlertDialogGeneral.showSweetAlertDialog("progress","Loading");
                    if(userName.getText().toString().trim().length() >= 2 && email.getText().toString().trim().length() > 11 && password.getText().toString().trim().length() >= 3)
                    {
                        InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/wp/v2/");
                        passwordStr = password.getText().toString();
                        Call<RegisterUser> call= interfaceApi.registerUser(userNameStr,valid_email,passwordStr);
                        call.enqueue(new Callback<RegisterUser>() {
                            //                        onResponce Invoked for a received HTTP response.
                            @Override
                            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
//                            Note: An HTTP response may still indicate an application-level failure such as a 404 or 500. Call Response.isSuccessful() to determine if the response indicates success.
                                if(!response.isSuccessful())
                                {
                                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","code "+ response.code());
                                    sweetAlertDialog.dismiss();
                                    return;
                                }
                                else
                                {
                                    RegisterUser registerUser =  response.body();
                                    SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(context);
                                    sharedPrefereneceManager.setUserName(registerUser.getMessage());

                                    context.blurView.setVisibility(View.GONE);
                                    swipeDismissDialog.dismiss();
                                    sweetAlertDialog.dismissWithAnimation();
                                    SweetAlertDialogGeneral sweetAlertDialogGeneral= new SweetAlertDialogGeneral(context);
                                    sweetAlertDialogGeneral.showSweetAlertDialog("success","User create successfully");
//                                Toast.makeText(context, "User Successfully creata ", Toast.LENGTH_SHORT).show();
                                }
                            }
                            //                      onFailure Invoked when a network exception occurred  talking to the server or when an unexpected exception occurred creating the request or processing the response.
                            @Override
                            public void onFailure(Call<RegisterUser> call, Throwable t) {
                                sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                                sweetAlertDialog.dismiss();
                            }
                        });

//                    //remove it later
//                    context.blurView.setVisibility(View.GONE);
//                    swipeDismissDialog.dismiss();

                    }
                    else
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please fill the fields correctly");
                        sweetAlertDialog.dismiss();
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
            tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForgetPasswordDialog forgetPasswordDialog = new ForgetPasswordDialog(context);
                    forgetPasswordDialog.alertDialogDemo();
                }
            });
        }
        catch (Exception e)
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }



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
