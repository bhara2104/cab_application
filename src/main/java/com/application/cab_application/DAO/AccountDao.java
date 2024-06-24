package com.application.cab_application.DAO;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.AccountType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public static Account getByID(int id) {
        String sql = "select * from accounts where id =" + id;
        ResultSet rs;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            rs = preparedStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                int accountType = rs.getInt("account_type");
                return new Account(rs.getInt("id"), rs.getString("email"), rs.getString("phone_number"), AccountType.fromCode(accountType));
            } else {
                return new Account();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e + "Class not Found Exception aroused");
        } catch (SQLException e) {
            System.out.println(e + " This return an sql error");
        }
        return new Account();
    }

    public static int createAccount(Account account) {
        String sql = "insert into accounts(email,password,phone_number,account_type) values (?,?,?,?)";
        int result;
        ResultSet rs;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getEmail());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getPhoneNumber());
            preparedStatement.setInt(4, account.getAccountType().getCode());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e + " Class Not Found Exception");
        } catch (SQLException e) {
            System.out.println(e + " SQL Exception");
        }
        return 0;
    }

    public static Account getAccountByEmail(String email, int type) {
        String sql = "Select * from accounts where email = ? and account_type = ?";
        ResultSet rs;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, type);
            rs = preparedStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                int accountType = rs.getInt("account_type");
                return new Account(rs.getInt("id"), rs.getString("email"), rs.getString("phone_number"), AccountType.fromCode(accountType));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e + " Class not found initiated here");
        } catch (SQLException e) {
            System.out.println(e + " SQL Exception got Triggered here");
        }
        return new Account();
    }

    public List<Account> getDriverAccounts() {
        String sql = "select * from accounts where account_type = 2";
        ResultSet rs;
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                accounts.add(new Account(id, email, phoneNumber, AccountType.DRIVER));
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e + " SQL Exception took place over here");
        } catch (ClassNotFoundException e) {
            System.out.println(e + " SQL Exception took place here");
        }
        return new ArrayList<>();
    }

    public List<Account> getRiderAccounts() {
        String sql = "select * from accounts where account_type = 1";
        ResultSet rs;
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                accounts.add(new Account(id, email, phoneNumber, AccountType.RIDER));
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e + " SQL Exception took place over here");
        } catch (ClassNotFoundException e) {
            System.out.println(e + " SQL Exception took place here");
        }
        return new ArrayList<>();
    }

    public static boolean checkAccountExist(Account account){
        String query = "Select * from accounts where account_type = ? and (phone_number = ? or email = ?)";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,account.getAccountType().getCode());
            preparedStatement.setString(2, account.getPhoneNumber());
            preparedStatement.setString(3,account.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false ;
    }
}
