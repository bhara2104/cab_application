package com.application.cab_application.Models;

public class Ride {
    private int id ;
    private int RiderId ;
    private int DriverId ;
    private int rideDetailsId;

    public Ride(int id, int riderId, int driverId, int rideDetailsId) {
        this.id = id;
        RiderId = riderId;
        DriverId = driverId;
        this.rideDetailsId = rideDetailsId;
    }

    public Ride(){

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

    public int getRideDetailsId() {
        return rideDetailsId;
    }
}
