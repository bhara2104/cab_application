package com.application.cab_application.Models;

public class RiderDetails {
    private int id ;
    private int accountID ; // This Account ID is the Foreign key from the accounts table
    private int primaryPaymentOption;

    public RiderDetails(){

    }

    public RiderDetails(int id, int accountID, int primaryPaymentOption) {
        this.id = id;
        this.accountID = accountID;
        this.primaryPaymentOption = primaryPaymentOption;
    }

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getPrimaryPaymentOption() {
        return primaryPaymentOption;
    }
}
