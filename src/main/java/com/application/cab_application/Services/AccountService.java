package com.application.cab_application.Services;

import com.application.cab_application.DAO.V1.AccountDao;
import com.application.cab_application.DAO.V1.AccountDetailsDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountService {
    public static int createAccount(String jsonBody) {
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject accountJson = jsonObject.getAsJsonObject("account");
        JsonObject accountDetailsJson = jsonObject.getAsJsonObject("accountDetails");
        Account account = gson.fromJson(accountJson, Account.class);
        String password = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(password);
        AccountDetails accountDetails = gson.fromJson(accountDetailsJson, AccountDetails.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int id = AccountDao.createAccount(account);
                if (id == 0) {
                    return 0;
                }
                accountDetails.setAccountId(id);
                AccountDetailsDao.createAccountDetails(accountDetails);
                connection.commit();
                return id;
            } catch (SQLException e) {
                connection.rollback();
                return 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static Account returnAccount(String jsonBody) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject accountJson = jsonObject.getAsJsonObject("account");
        return gson.fromJson(accountJson, Account.class);
    }

    public static Account authenticateUser(String email, String password, AccountType accountType) {
        Account account = AccountDao.getAccountByEmail(email, accountType.getCode());
        if (account.getId() == 0) {
            return null;
        } else {
            String encryptedPassword = account.getPassword();
            return BCrypt.checkpw(password, encryptedPassword) ? account : null;
        }
    }

    public static List<String> validateAccount(String jsonBody){
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject accountJson = jsonObject.getAsJsonObject("account");
        Account account = gson.fromJson(accountJson, Account.class);
        return Account.checkValidations(account);
    }
}
