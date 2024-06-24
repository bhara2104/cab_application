package com.application.cab_application.Util;

import java.sql.Timestamp;

public class BillAmountGenerator {
    public static double generateBill(Timestamp startTime, Timestamp endTime){
        long minutes = endTime.getTime() - startTime.getTime();
        minutes = (minutes / 1000) / 60 ;
        minutes = minutes > 0 ? minutes : 1 ;
        return Math.ceil(15 + Math.random() * 40 * minutes);
    }
}
