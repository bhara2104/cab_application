package com.application.cab_application.Models;

import java.sql.Timestamp;

public class AccountDetails {
    private int id ;
    private int accountId ; //It is mapped to account in order to fetch the account details like name and other details
    private String name ;
    private String address ;
    private int currentRideID ; // This ID is mapped as the current ride which the rider or driver is travelling
    private Timestamp createdAt ;
    private Timestamp updateAt ;
    private Timestamp lastLoginAt;

    public AccountDetails(int id, int accountId, String name, String address, int currentRideID, Timestamp createdAt, Timestamp updateAt, Timestamp lastLoginAt) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.currentRideID = currentRideID;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.lastLoginAt = lastLoginAt;
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public void setLastLoginAt(Timestamp lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public Timestamp getLastLoginAt() {
        return lastLoginAt;
    }
}
