package com.application.cab_application.DAO;

import com.application.cab_application.Models.UpiPayment;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpiPaymentsDao {
    public static int createUPIPayment(UpiPayment upiPayment){
        String sql = "insert into upi_payments(payment_id,upi_id) values (?,?)";
        ResultSet resultSet ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1,upiPayment.getPaymentID());
            preparedStatement.setString(2,upiPayment.getUpiID());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
