package com.application.cab_application.DAO;

import com.application.cab_application.Models.Payment;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.PaymentType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDao {
    public static int createPayment(Payment payment) {
        String sql = "insert into payments(payment_type, payment_date) values(?,?)";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, payment.getPaymentType().getCode());
            preparedStatement.setTimestamp(2, payment.getPaymentDate());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static Payment getPayment(int id) {
        String sql = "select * from payments where id = "+id ;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                return new Payment(resultSet.getInt("id"), PaymentType.fromCode(resultSet.getInt("payment_type")),
                        resultSet.getTimestamp("payment_date"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Payment();
    }
}
