package com.webscare.livenewsglobe.models;

public class Favourites {

    String networkName;
    int netwokImage;

    public String getNetworkName() {
        return networkName;
    }

    public int getNetwokImage() {
        return netwokImage;
    }

    public Favourites(String networkName, int netwokImage) {
        this.networkName = networkName;
        this.netwokImage = netwokImage;
    }
}
