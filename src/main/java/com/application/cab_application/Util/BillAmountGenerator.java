package com.application.cab_application.Util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class BillAmountGenerator {
    public static double generateBill(Timestamp startTime, Timestamp endTime){
        long minutes = endTime.getTime() - startTime.getTime();
        System.out.println(minutes);
        return Math.ceil(15 + Math.random() * 40 * minutes);
    }
}
