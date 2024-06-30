package com.application.cab_application.Models;

public class Ride {
    private int id;
    private int RiderId;
    private int DriverId;

    public Ride(int id, int riderId, int driverId) {
        this.id = id;
        RiderId = riderId;
        DriverId = driverId;
    }

    public void setRiderId(int riderId) {
        RiderId = riderId;
    }

    public Ride() {

    }

    public int getId() {
        return id;
    }

    public int getRiderId() {
        return RiderId;
    }

    public int getDriverId() {
        return DriverId;
    }

}
