package com.application.cab_application.Models;

import com.application.cab_application.enums.AccountType;

import java.sql.Timestamp;

public class Account {
    private int id;
    private String email;
    private String password;
    private String phoneNumber;
    private AccountType accountType;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Account(int id, String email, String password, String phoneNumber, AccountType accountType, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.createdAt = createdAt ;
        this.updatedAt = updatedAt ;
    }

    public Account() {

    }

    public int getId() {
        return id;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
