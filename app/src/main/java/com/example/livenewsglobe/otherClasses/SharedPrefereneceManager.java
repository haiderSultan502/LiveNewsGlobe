package com.example.livenewsglobe.otherClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefereneceManager {
    Context context;

    String userEmail,password;

    int userId;

    public SharedPrefereneceManager(Context context) {
        this.context = context;
    }

    public int getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId",0);
    }

    public void setUserId(int userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("userId",userId).commit();
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        return sharedPreferences.getString("userEmail","");
    }

    public void setUserEmail(String userEmail) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("userEmail",userEmail).commit();
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        return sharedPreferences.getString("userPassword","");
    }

    public void setPassword(String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("userPassword",password).commit();
    }



    public void setLoginStatus(Boolean status)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("loginStatus",status).commit();
    }
    public Boolean getLoginStatus()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginFileStatus",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("loginStatus",false);
    }
}
