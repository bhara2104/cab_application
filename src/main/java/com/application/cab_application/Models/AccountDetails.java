package com.application.cab_application.Models;

import com.application.cab_application.DAO.RidesDao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<String> runValidation(AccountDetails accountDetails){
        List<String> errors = new ArrayList<>();
        if(!validateRideID(accountDetails.getCurrentRideID())){
            errors.add("Enter a Valid Ride ID");
        }
        return errors;
    }

    public static boolean validateRideID(int currentRideID){
        Ride ride = RidesDao.getRide(currentRideID);
        return ride.getId() == currentRideID ;
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

    public Map<String , Object> objectMap(){
        Map<String, Object> accountDetailsTableMapping = new HashMap<>();
        accountDetailsTableMapping.put("account_id" , accountId);
        accountDetailsTableMapping.put("name", name);
        accountDetailsTableMapping.put("address",address);
        accountDetailsTableMapping.put("current_ride_id" , currentRideID);
        return accountDetailsTableMapping;
    }
}
