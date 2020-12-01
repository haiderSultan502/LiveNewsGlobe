package com.example.livenewsglobe.models;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import javax.crypto.Cipher;

public class Cities {


    @SerializedName("term_id")
    @Expose
    private Integer termId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public Cities() {
    }

    public Cities(Integer termId, String name, String description) {
        this.termId = termId;
        this.name = name;
        this.description = description;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cities)) return false;
        Cities cities = (Cities) o;
        return getTermId().equals(cities.getTermId()) &&
                getName().equals(cities.getName()) &&
                getDescription().equals(cities.getDescription());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getTermId(), getName(), getDescription());
    }
}





