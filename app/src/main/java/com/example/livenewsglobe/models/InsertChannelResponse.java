package com.example.livenewsglobe.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertChannelResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}