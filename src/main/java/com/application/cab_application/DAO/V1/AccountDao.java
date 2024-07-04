package com.application.cab_application.DAO.V1;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.AccountType;
import org.checkerframework.checker.units.qual.A;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static java.util.Map.entry;

public class AccountDao {
    public static Account getByID(int id) {
        ResultSet rs = BaseDao.find(id, "accounts");
        return accountMapper(rs);
    }

    public static int createAccount(Account account) {
        return BaseDao.create(account.objectMap(), "accounts");
    }

    public static Account getAccountByEmail(String email, int type) {
        Map<String, Object> whereObject = Map.ofEntries(
                entry("email", email),
                entry("account_type", type)
        );
        ResultSet resultSet = BaseDao.find_chain("accounts", whereObject);
        return accountMapper(resultSet);
    }

    public static Boolean checkAccountExists(Account account){
        Map<String, Object> whereObject = Map.ofEntries(
                entry("account_type", account.getAccountType()),
                entry("phone_number", account.getPhoneNumber()),
                entry("email", account.getEmail())
        );
        String query = "Select * from accounts where account_type = ? and (phone_number = ? or email = ?)";
        ResultSet resultSet = BaseDao.find_by_sql(query,whereObject);
        try {
            boolean bool = resultSet.next();
            resultSet.close();
            return bool ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Account accountMapper(ResultSet rs) {
        try {
            if (rs.next()) {
                int accountType = rs.getInt("account_type");
                Account account = new Account(rs.getInt("id"), rs.getString("email"), rs.getString("phone_number"), AccountType.fromCode(accountType));
                rs.close();
                return account;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " DataBase Access Error");
            return new Account();
        }
        return new Account();
    }
}
