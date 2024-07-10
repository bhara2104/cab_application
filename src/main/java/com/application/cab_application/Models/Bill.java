package com.application.cab_application.Models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bill {
    private int id;
    private int rideID;
    private double billAmount;
    private Integer paymentId;

    public Bill(int id, int rideID, double billAmount, int paymentId) {
        this.id = id;
        this.rideID = rideID;
        this.billAmount = billAmount;
        this.paymentId = paymentId;
    }

    public Bill(double billAmount, int rideID){
        this.rideID = rideID ;
        this.billAmount = billAmount ;
    }

    public void setPaymentId(Integer paymentId) {
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

    public Integer getPaymentId() {
        return paymentId;
    }

    public Map<String, Object> billTableObject(){
        Map<String, Object> billObject = new LinkedHashMap<>() ;
        billObject.put("ride_id",rideID);
        billObject.put("bill_amount", billAmount);
        billObject.put("payment_id", paymentId);
        return billObject;
    }
}
