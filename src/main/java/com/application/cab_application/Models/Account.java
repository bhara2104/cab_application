package com.application.cab_application.Models;

import com.application.cab_application.enums.AccountType;
import org.apache.commons.collections.map.HashedMap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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


    public static List<String> checkValidations(Account account){
        List<String> errors = new ArrayList<>();
        if(!checkEmailValidation(account.getEmail())){
            errors.add("Email is not Valid");
        }
        if(!checkPasswordValidation(account.getPassword())){
            errors.add("Password is not valid");
        }

        if(!checkValidAccountType(account.getAccountType())){
            errors.add("Enter Valid Account Type");
        }
        if(!checkValidPhoneNumber(account.getPhoneNumber())){
            errors.add("Enter Valid Phone Number");
        }
        return errors;
    }

    private static boolean checkValidPhoneNumber(String phoneNumber) {
        String phoneRegx = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pat = Pattern.compile(phoneRegx);
        if(phoneNumber == null){
            return false;
        }else{
            return pat.matcher(phoneNumber).matches();
        }
    }

    public static Boolean checkValidAccountType(AccountType accountType){
        if(accountType == null){
            return false;
        }else{
            return true;
        }
    }

    public static Boolean checkEmailValidation(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static Boolean checkPasswordValidation(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        return p.matcher(password).matches();
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

    public Map<String, Object> objectMap(){
        Map<String, Object> accountTableMapping = new HashMap<>();
        accountTableMapping.put("email",email);
        accountTableMapping.put("password", password);
        accountTableMapping.put("account_type", accountType.getCode());
        accountTableMapping.put("phone_number",phoneNumber);
        return accountTableMapping ;
    }
}
