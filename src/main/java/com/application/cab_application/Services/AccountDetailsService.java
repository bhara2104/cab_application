package com.application.cab_application.Services;

import com.application.cab_application.DAO.*;
import com.application.cab_application.Models.*;

import com.application.cab_application.Util.PrettyPrintHelper;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AccountDetailsService {

    public static String getAccountDetailsResponse(int id){
        Account account = AccountDao.getByID(id);
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(id);
        JsonObject accountDetailsObject = PrettyPrintHelper.prettyPrintHelper(accountDetails).getAsJsonObject();
        accountDetailsObject.addProperty("email", account.getEmail());
        if(account.getAccountType() == AccountType.DRIVER){
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(id);
            accountDetailsObject.addProperty("detailsUpdated", driverDetails.getId() != 0);
        }
        Gson gson = new Gson();
        return gson.toJson(accountDetailsObject);
    }
}
