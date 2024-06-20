package com.application.cab_application.Models;

import com.application.cab_application.enums.PaymentType;

import java.sql.Timestamp;

public class Payment {
    private int id ;
    private PaymentType paymentType ;
    private Timestamp paymentDate ;
}
