package com.example.delllaptop.projone.DTO;

/**
 * Created by Aya on 15/03/2018.
 */

public class LocationObject {
    private double longitudes;
    private double latitudes;
    private String address;

    public double getLongitudes() {
        return longitudes;
    }

    public double getLatitudes() {
        return latitudes;
    }

    public String getAddress() {
        return address;
    }

    public LocationObject(double longitudes, double latitudes, String address) {
        this.longitudes = longitudes;
        this.latitudes = latitudes;
        this.address = address;
    }
}
