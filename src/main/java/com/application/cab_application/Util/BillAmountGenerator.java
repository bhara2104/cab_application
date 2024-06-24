package com.application.cab_application.Util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class BillAmountGenerator {
    public static double generateBill(String startTime, String endTime){
        Instant start = Instant.parse(startTime);
        Instant end = Instant.parse(endTime);
        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        return Math.ceil(15 + Math.random() * 40 * minutes);
    }
}
