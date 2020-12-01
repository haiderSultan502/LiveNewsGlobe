package com.example.livenewsglobe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityModel {

    @SerializedName("term_id")
    @Expose
    private Integer termId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public CityModel(Integer termId, String name, String description) {
        this.termId = termId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name ;
    }
}
