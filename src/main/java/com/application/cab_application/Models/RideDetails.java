package com.application.cab_application.Models;

import com.application.cab_application.enums.RequestStatus;

import java.sql.Timestamp;

public class RideDetails {
    private int id;
    private int fromLocation;
    private int toLocation;
    private RequestStatus requestStatus;
    private Timestamp startTime ;
    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public RideDetails(int id, int fromLocation, int toLocation, RequestStatus requestStatus, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.requestStatus = requestStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public RideDetails(int fromLocation, int toLocation, RequestStatus requestStatus) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.requestStatus = requestStatus;
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
}
