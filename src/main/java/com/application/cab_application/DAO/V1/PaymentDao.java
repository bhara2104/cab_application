package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Payment;
import com.application.cab_application.enums.PaymentType;

import java.sql.ResultSet;

public class PaymentDao {
    public static int createPayment(Payment payment) throws DbNotReachableException {
        return BaseDao.create(payment.paymentTableMapper(), "payments");
    }

    public static Payment getPayment(int id) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find(id, "payments");
        return paymentMapper(resultSet);
    }

    public static Payment paymentMapper(ResultSet resultSet) {
        try {
            Payment payment;
            if (resultSet.next()) {
                payment = new Payment(resultSet.getInt("id"), PaymentType.fromCode(resultSet.getInt("payment_type")),
                        resultSet.getTimestamp("payment_date"));
            } else {
                payment = new Payment();
            }
            resultSet.getStatement().close();
            return payment;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Payment();
    }
}
