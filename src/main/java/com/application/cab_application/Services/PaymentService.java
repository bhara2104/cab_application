package com.application.cab_application.Services;

import com.application.cab_application.DAO.PaymentDao;
import com.application.cab_application.DAO.UpiDataDao;
import com.application.cab_application.DAO.UpiPaymentsDao;
import com.application.cab_application.Models.Payment;
import com.application.cab_application.Models.UpiData;
import com.application.cab_application.Models.UpiPayment;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.enums.PaymentType;
import com.google.gson.Gson;

import com.google.gson.JsonObject;

import java.sql.Timestamp;



public class PaymentService {
    public static int savePayment(String jsonBody) {
        JsonObject jsonObject = new Gson().fromJson(jsonBody, JsonObject.class);
        JsonObject paymentJson = jsonObject.getAsJsonObject("payment");
        Payment payment = new Gson().fromJson(paymentJson, Payment.class);
        System.out.println(jsonBody);
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        int id = PaymentDao.createPayment(payment);
        if (payment.getPaymentType() == PaymentType.UPI) {
            if (!UpiDataDao.checkUPIDateExist(jsonObject.get("upiID").getAsString())) {
                UpiData upiData = new UpiData(CurrentUserHelper.getAccount(), jsonObject.get("upiID").getAsString());
                UpiDataDao.createUPIData(upiData);
            }
            UpiPayment upiPayment = new UpiPayment(id, jsonObject.get("upiID").getAsString());
            UpiPaymentsDao.createUPIPayment(upiPayment);
        }
        return id ;
    }
}
