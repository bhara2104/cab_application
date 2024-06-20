package com.application.cab_application.Models;

import com.application.cab_application.enums.RequestStatus;

public class RideDetails {
    private int id ;
    private int fromLocation;
    private int toLocation;
    private RequestStatus requestStatus;

    public RideDetails(int id, int fromLocation, int toLocation, RequestStatus requestStatus) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.requestStatus = requestStatus;
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
