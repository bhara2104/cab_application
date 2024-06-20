package com.application.cab_application.Models;

public class Location {
    private int id;
    private double latitude;
    private double longitude;
    private String landmark;
    private String city;
    private int pinCode;

    public Location(int id, double latitude, double longitude, String landmark, String city, int pinCode) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.landmark = landmark;
        this.city = city;
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }

    public Location() {

    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return city;
    }
}
