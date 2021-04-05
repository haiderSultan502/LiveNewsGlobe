package com.webscare.livenewsglobe.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertChannelResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}