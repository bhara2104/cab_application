package com.application.cab_application.Models;

import com.application.cab_application.enums.AccountType;

import java.sql.Timestamp;

public class Account {
    private int id;
    private String email;
    private String password;
    private String phoneNumber;
    private AccountType accountType;

    public Account(int id, String email, String password, String phoneNumber, AccountType accountType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
    }


    public Account(String email, String password, String phoneNumber, AccountType accountType) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
    }

    public Account(int id, String email, String phoneNumber, AccountType accountType) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
    }

    public Account() {

    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
