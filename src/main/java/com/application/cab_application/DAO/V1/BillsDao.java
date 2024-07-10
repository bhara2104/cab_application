package com.application.cab_application.DAO.V1;

import com.application.cab_application.Models.Bill;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillsDao {
    public static int createBill(Bill bill){
        bill.setPaymentId(null);
        return BaseDao.create(bill.billTableObject(), "bills");
    }

    public static Boolean updatePaymentInBill(int billID, int paymentID){
        return BaseDao.updateColumn("payment_id", paymentID, "bills", billID);
    }

    public static Bill getBillRideID(int id){
        ResultSet resultSet = BaseDao.find_by("bills", "ride_id", id);
        return billMapper(resultSet);
    }

    public static Bill getBill(int id){
        ResultSet resultSet = BaseDao.find(id,"bills");
        return billMapper(resultSet);
    }

    public static Bill billMapper(ResultSet resultSet){
        try {
            Bill bill ;
            if(resultSet.next()){
                bill = new Bill(resultSet.getInt("id"), resultSet.getInt("ride_id"), resultSet.getDouble("bill_amount"), resultSet.getInt("payment_id"));
            }else{
                bill = new Bill();
            }
            resultSet.getStatement().close();
            return bill;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Bill();
    }
}
