package com.application.cab_application.Models;

public class UpiData {
    private int id;
    private int accountID;
    private String upiID;

    public UpiData(int id, int accountID, String upiID) {
        this.id = id;
        this.accountID = accountID;
        this.upiID = upiID;
    }

    public UpiData(int accountID, String upiID) {
        this.accountID = accountID;
        this.upiID = upiID;
    }

    public UpiData() {
    }

    public int getAccountID() {
        return accountID;
    }

    public String getUpiID() {
        return upiID;
    }

    public int getId() {
        return id;
    }
}
