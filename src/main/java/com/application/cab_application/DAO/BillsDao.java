package com.application.cab_application.DAO;

import com.application.cab_application.Models.Bill;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillsDao {

    public static int createBill(Bill bill){
        String sql = "insert into bills(bill_amount, ride_id) values(?,?)";
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setDouble(1,bill.getBillAmount());
            preparedStatement.setInt(2,bill.getRideID());
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


    public static Boolean updatePaymentInBill(int billID, int paymentID){
        String query = "update bills set payment_id = ? where id = ?";
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,paymentID);
            preparedStatement.setInt(2,billID);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0 ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Bill getBillRideID(int id){
        String query = "Select * from bills where ride_id = ?";
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                return new Bill(resultSet.getInt("id"), resultSet.getInt("ride_id"), resultSet.getDouble("bill_amount"), resultSet.getInt("payment_id"));
            } else {
                return new Bill();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Bill();
    }

    public static Bill getBill(int id){
        String query = "Select * from bills where id = ?";
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                return new Bill(resultSet.getInt("id"), resultSet.getInt("ride_id"), resultSet.getDouble("bill_amount"), resultSet.getInt("payment_id"));
            } else {
                return new Bill();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Bill();
    }
}
