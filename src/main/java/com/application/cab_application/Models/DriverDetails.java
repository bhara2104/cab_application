package com.application.cab_application.Models;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DriverDetails {
    private int id;
    private int accountID;
    private String licenseNumber;
    private boolean availability;
    private int currentLocationId;
    private int vehicleId;

    public DriverDetails(int id, int accountID, String licenseNumber, boolean availability, int currentLocationId, int vehicleId) {
        this.id = id;
        this.accountID = accountID;
        this.licenseNumber = licenseNumber;
        this.availability = availability;
        this.currentLocationId = currentLocationId;
        this.vehicleId = vehicleId;
    }

    public DriverDetails() {

    }

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Map<String, Object> driverDetailsObject(){
        Map<String , Object> driverDetailsMapping = new LinkedHashMap<>();
        driverDetailsMapping.put("account_id",accountID);
        driverDetailsMapping.put("license_number", licenseNumber) ;
        driverDetailsMapping.put("availability", availability);
        driverDetailsMapping.put("vehicle_id", vehicleId);
        driverDetailsMapping.put("current_location_id", currentLocationId);
        return driverDetailsMapping;
    }
}
