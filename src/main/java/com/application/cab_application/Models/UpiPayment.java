package com.application.cab_application.Models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public Map<String ,Object> upiMapper(){
        Map<String, Object> upiPaymentMapper = new LinkedHashMap<>();
        upiPaymentMapper.put("upi_id", upiID);
        upiPaymentMapper.put("payment_id", paymentID);
        return upiPaymentMapper;
    }
}
