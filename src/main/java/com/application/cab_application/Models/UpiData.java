package com.application.cab_application.Models;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> upiDataTableMapper(){
        Map<String, Object> upiDataMapper = new HashMap<>();
        upiDataMapper.put("upi_id", upiID);
        upiDataMapper.put("account_id", accountID);
        return upiDataMapper ;
    }
}
