package com.application.cab_application.Services;

import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {
    public static Boolean createAccount(String jsonBody) {
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject accountJson = jsonObject.getAsJsonObject("account");
        JsonObject accountDetailsJson = jsonObject.getAsJsonObject("accountDetails");
        Account account = gson.fromJson(accountJson, Account.class);
        String password = BCrypt.hashpw(account.getPassword(),BCrypt.gensalt());
        account.setPassword(password);
        AccountDetails accountDetails = gson.fromJson(accountDetailsJson, AccountDetails.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int id = AccountDao.createAccount(account);
                if (id == 0) {
                    return false;
                }
                accountDetails.setAccountId(id);
                AccountDetailsDao.createAccountDetails(accountDetails);
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Account returnAccount(String jsonBody){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject accountJson = jsonObject.getAsJsonObject("account");
        return gson.fromJson(accountJson, Account.class);
    }
}
