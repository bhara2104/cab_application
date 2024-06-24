package com.application.cab_application.Services;

import com.application.cab_application.DAO.PaymentDao;
import com.application.cab_application.DAO.UpiDataDao;
import com.application.cab_application.DAO.UpiPaymentsDao;
import com.application.cab_application.Models.Payment;
import com.application.cab_application.Models.UpiData;
import com.application.cab_application.Models.UpiPayment;
import com.application.cab_application.enums.PaymentType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PaymentService {
    public static int savePayment(String jsonBody) {
        JsonObject jsonObject = new Gson().fromJson(jsonBody, JsonObject.class);
        JsonObject paymentJson = jsonObject.getAsJsonObject("payment");
        Payment payment = new Gson().fromJson(paymentJson, Payment.class);
        int id = PaymentDao.createPayment(payment);
        if (payment.getPaymentType() == PaymentType.UPI) {
            if (!UpiDataDao.checkUPIDateExist(jsonObject.get("upiID").toString())) {
                UpiData upiData = new UpiData(Integer.parseInt(jsonObject.get("accountID").toString()), jsonObject.get("upiID").toString());
                UpiDataDao.createUPIData(upiData);
            }
            UpiPayment upiPayment = new UpiPayment(id, jsonObject.get("upiID").toString());
            UpiPaymentsDao.createUPIPayment(upiPayment);
        }
        return id ;
    }
}
