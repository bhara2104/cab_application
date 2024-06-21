package com.application.cab_application.Models;

import com.application.cab_application.enums.PaymentType;

import java.sql.Timestamp;

public class Payment {
    private int id;
    private PaymentType paymentType;
    private Timestamp paymentDate;

    public Payment(int id, PaymentType paymentType, Timestamp paymentDate) {
        this.id = id;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
    }

    public Payment(PaymentType paymentType, Timestamp paymentDate) {
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }
}
