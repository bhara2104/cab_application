package com.application.cab_application.Models;

import java.sql.Timestamp;

public class AccountDetails {
    private int id;
    private int accountId; //It is mapped to account in order to fetch the account details like name and other details
    private String name;
    private String address;
    private int currentRideID; // This ID is mapped as the current ride which the rider or driver is travelling

    public AccountDetails(int id, int accountId, String name, String address, int currentRideID) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.currentRideID = currentRideID;
    }

    public AccountDetails(int accountId, String name, String address, int currentRideID) {
        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.currentRideID = currentRideID;
    }

    public AccountDetails() {

    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrentRideID(int currentRideID) {
        this.currentRideID = currentRideID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCurrentRideID() {
        return currentRideID;
    }
}
