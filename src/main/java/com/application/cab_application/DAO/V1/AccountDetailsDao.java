package com.application.cab_application.DAO.V1;

import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Util.DatabaseConnector;
import org.checkerframework.checker.units.qual.A;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDetailsDao {
    public static void createAccountDetails(AccountDetails details) {
        details.setCurrentRideID(null);
        BaseDao.create(details.objectMap(), "account_details");
    }

    public static AccountDetails getAccountDetailsByAccountID(int id) {
        ResultSet resultSet = BaseDao.find_by("account_details","account_id", id);
        return AccountDetailsObjectMapper(resultSet);
    }

    public static boolean updateCurrentRideID(int accountId, int currentRideID){
        return BaseDao.updateColumn("current_ride_id", currentRideID, "account_details", "account_id", accountId);
    }

    public static boolean updateCurrentRideIDAsNUll(int accountId){
        return BaseDao.updateColumn("current_ride_id", null, "account_details", "account_id", accountId);
    }

    public static AccountDetails AccountDetailsObjectMapper(ResultSet resultSet) {
        try {
            AccountDetails accountDetails ;
            if (resultSet.next()) {
                accountDetails = new AccountDetails(resultSet.getInt("id"), resultSet.getInt("account_id"),
                        resultSet.getString("name"), resultSet.getString("address"),
                        resultSet.getInt("current_ride_id"));
            }else{
                accountDetails = new AccountDetails();
            }
            resultSet.getStatement().close();
            return accountDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new AccountDetails();
    }
}
