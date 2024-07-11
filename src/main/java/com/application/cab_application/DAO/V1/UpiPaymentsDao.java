package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.UpiPayment;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpiPaymentsDao {
    public static int createUPIPayment(UpiPayment upiPayment) throws DbNotReachableException {
        return BaseDao.create(upiPayment.upiMapper(), "upi_payments");
    }
}
