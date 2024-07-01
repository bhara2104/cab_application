package com.application.cab_application.DAO;

import com.application.cab_application.Models.UpiData;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UpiDataDao {
    public static void createUPIData(UpiData upiData) {
        String sql = "insert into upi_datas(upi_id,account_id) values(?,?)";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, upiData.getUpiID());
            preparedStatement.setInt(2, upiData.getAccountID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<UpiData> getSavedUPI(int accountID) {
        String sql = "select * from upi_datas where account_id = ? order by id DESC limit 5";
        List<UpiData> upiDataList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1,accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                upiDataList.add(new UpiData(resultSet.getInt("id"), resultSet.getInt("account_id"), resultSet.getString("upi_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return upiDataList;
    }

    public static boolean checkUPIDateExist(String upiID) {
        String sql = "select * from upi_datas where upi_id = ?" ;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1,upiID);
            System.out.println(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
