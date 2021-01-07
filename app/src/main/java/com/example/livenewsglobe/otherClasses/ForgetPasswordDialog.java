package com.example.livenewsglobe.otherClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.models.ForgetPassword;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordDialog {

    InterfaceApi interfaceApiGetForgetPassword;
    Context context;
    EditText userEmail,newPassword,putCode;
    Button btnSend,btnResertPassword,btnContinue;
    Call<ForgetPassword> call;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    Dialog  dialog;
    Window window;
    String email,password,code;
    SweetAlertDialog sweetAlertDialog;

    public ForgetPasswordDialog(MainActivity context) {
        this.context = context;
    }

    void alertDialogDemo() {
        try
        {
            sweetAlertDialogGeneral = new SweetAlertDialogGeneral(context);

            interfaceApiGetForgetPassword= RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/bdpwr/v1/");

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.foget_password);

            userEmail= (EditText) dialog.findViewById(R.id.et_user_email);
            btnSend = dialog.findViewById(R.id.btn_send);
            btnResertPassword = dialog.findViewById(R.id.reset_password);





            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            window = dialog.getWindow();

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            btnSend.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    sweetAlertDialog  = sweetAlertDialogGeneral.showSweetAlertDialog("progress","loading...");

                    email = userEmail.getText().toString();

                    if (email.length() < 1)
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please Enter Email Address");
                        sweetAlertDialog.dismiss();
                        return;
                    }
                    else
                    {
                        call = interfaceApiGetForgetPassword.passwordForget(email);
                        call.enqueue(new Callback<ForgetPassword>() {
                            @Override
                            public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {
                                if(!response.isSuccessful())
                                {
                                    sweetAlertDialog.dismiss();
                                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","No user found with this email address.");

                                    return;
                                }
                                sweetAlertDialog.dismiss();
                                ForgetPassword forgetPassword = response.body();
                                sweetAlertDialogGeneral.showSweetAlertDialog("success",forgetPassword.getMessage());

                            }

                            @Override
                            public void onFailure(Call<ForgetPassword> call, Throwable t) {
                                sweetAlertDialog.dismiss();
                                sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                            }
                        });
                    }





                }
            });

            btnResertPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.setContentView(R.layout.reset_password);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    window = dialog.getWindow();

                    dialog.show();

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                    userEmail = dialog.findViewById(R.id.et_email);
                    newPassword = dialog.findViewById(R.id.setPassword);
                    putCode = dialog.findViewById(R.id.email_code);

                    btnContinue = dialog.findViewById(R.id.btn_continue);

                    btnContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (userEmail.length() > 1 && newPassword.length() > 1 && putCode.length() > 1 )
                            {

                                sweetAlertDialog  = sweetAlertDialogGeneral.showSweetAlertDialog("progress","loading...");
                                interfaceApiGetForgetPassword= RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/bdpwr/v1/");

                                call = interfaceApiGetForgetPassword.passwordReset(userEmail.getText().toString(),newPassword.getText().toString(),putCode.getText().toString());

                                call.enqueue(new Callback<ForgetPassword>() {
                                    @Override
                                    public void onResponse(Call<ForgetPassword> call, Response<ForgetPassword> response) {
                                        if(!response.isSuccessful())
                                        {
                                            sweetAlertDialog.dismiss();
                                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","You must request a password reset code before you try to set a new password.");
                                            dialog.dismiss();
                                            return;
                                        }

                                        sweetAlertDialog.dismiss();
                                        ForgetPassword forgetPassword = response.body();
                                        sweetAlertDialogGeneral.showSweetAlertDialog("success",forgetPassword.getMessage());
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<ForgetPassword> call, Throwable t) {
                                        sweetAlertDialog.dismiss();
                                        sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                                        dialog.dismiss();
                                    }
                                });
                                return;
                            }
                            else
                            {
                                sweetAlertDialog.dismiss();
                                sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please Fill the fields");
                            }
                        }
                    });






                }
            });
        }
        catch (Exception e)
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }

//        Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.foget_password);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_corner);
//        dialog.setCancelable(false);
//        dialog.show();





    }
}
