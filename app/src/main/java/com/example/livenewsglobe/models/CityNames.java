package com.example.livenewsglobe.models;

public class CityNames {
    String cityName;

    public CityNames(String cityName)
    {
        this.cityName=cityName;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return cityName ;
    }
}
