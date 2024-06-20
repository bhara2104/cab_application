package com.application.cab_application.Models;

public class Bill {
    private int id ;
    private int rideID ;
    private double billAmount ;
    private int paymentId ;

    public Bill(int id, int rideID, double billAmount, int paymentId) {
        this.id = id;
        this.rideID = rideID;
        this.billAmount = billAmount;
        this.paymentId = paymentId;
    }

    public Bill(int rideID, double billAmount, int paymentId) {
        this.rideID = rideID;
        this.billAmount = billAmount;
        this.paymentId = paymentId;
    }

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public int getRideID() {
        return rideID;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public int getPaymentId() {
        return paymentId;
    }
}
