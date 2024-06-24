package com.application.cab_application.Models;

import com.application.cab_application.enums.RequestStatus;
import com.application.cab_application.enums.VehicleType;

import java.sql.Timestamp;

public class RideDetails {
    private int id;
    private int fromLocation;
    private int toLocation;
    private int rideID;
    private RequestStatus requestStatus;
    private VehicleType vehicleType;
    private Timestamp startTime ;
    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public RideDetails(int id, int fromLocation, int toLocation, RequestStatus requestStatus, Timestamp startTime, Timestamp endTime, int rideID, VehicleType vehicleType) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.requestStatus = requestStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rideID = rideID ;
        this.vehicleType =  vehicleType ;
    }

    public int getRideID() {
        return rideID;
    }

    public void setRideID(int rideID) {
        this.rideID = rideID;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public RideDetails() {
    }

    public int getId() {
        return id;
    }

    public int getFromLocation() {
        return fromLocation;
    }

    public int getToLocation() {
        return toLocation;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
