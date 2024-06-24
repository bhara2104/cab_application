package com.application.cab_application.Models;

public class UpiPayment {
    private int id;
    private int paymentID;
    private String upiID;

    public UpiPayment(int id, int paymentID, String upiID) {
        this.id = id;
        this.paymentID = paymentID;
        this.upiID = upiID;
    }

    public UpiPayment(int paymentID, String upiID) {
        this.paymentID = paymentID;
        this.upiID = upiID;
    }

    public int getId() {
        return id;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public String getUpiID() {
        return upiID;
    }

    public UpiPayment(){

    }
}
