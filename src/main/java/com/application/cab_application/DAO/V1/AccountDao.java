package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Account;
import com.application.cab_application.enums.AccountType;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AccountDao {
    public static Account getByID(int id) throws DbNotReachableException {
        ResultSet rs = BaseDao.find(id, "accounts");
        return accountMapper(rs);
    }

    public static int createAccount(Account account) throws DbNotReachableException {
        return BaseDao.create(account.objectMap(), "accounts");
    }

    public static Account getAccountByEmail(String email, int type) throws DbNotReachableException {
        Map<String, Object> whereObject = new LinkedHashMap<>();
        whereObject.put("email", email);
        whereObject.put("account_type",type);
        ResultSet resultSet = BaseDao.find_chain("accounts", whereObject);
        return accountMapper(resultSet);
    }

    public static Boolean checkAccountExists(Account account) throws DbNotReachableException {
        Map<String, Object> whereObject = new LinkedHashMap<>();
        whereObject.put("account_type", account.getAccountType().getCode());
        whereObject.put("phone_number", account.getPhoneNumber());
        whereObject.put("email", account.getEmail());
        String query = "Select * from accounts where account_type = ? and (phone_number = ? or email = ?)";
        ResultSet resultSet = BaseDao.find_by_sql(query, whereObject);
        try {
            boolean bool = resultSet.next();
            return bool;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static Account accountMapper(ResultSet rs) {
        try {
            if (rs.next()) {
                int accountType = rs.getInt("account_type");
                Account account = new Account(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("phone_number"), AccountType.fromCode(accountType));
                rs.getStatement().close();
                return account;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " DataBase Access Error");
            return new Account();
        }
        return new Account();
    }
}
// Problem with using do while in Result set

// We have to invoke result set next before using it in do while

// if result set is null it will raise an exception