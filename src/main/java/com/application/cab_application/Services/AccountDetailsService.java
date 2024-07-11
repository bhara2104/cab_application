package com.application.cab_application.Services;

import com.application.cab_application.DAO.V1.*;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.*;

import com.application.cab_application.Util.PrettyPrintHelper;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AccountDetailsService {

    public static String getAccountDetailsResponse(int id) throws DbNotReachableException {
        Account account = AccountDao.getByID(id);
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(id);
        JsonObject accountDetailsObject = PrettyPrintHelper.prettyPrintHelper(accountDetails).getAsJsonObject();
        accountDetailsObject.addProperty("email", account.getEmail());
        accountDetailsObject.addProperty("accountType", account.getAccountType().name());
        if(account.getAccountType() == AccountType.DRIVER){
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(id);
            accountDetailsObject.addProperty("detailsUpdated", driverDetails.getId() != 0);
            if(driverDetails.getId()!=0){
                Location location = LocationDao.getLocation(driverDetails.getCurrentLocationId());
                accountDetailsObject.addProperty("currentLocation", location.getLandmark() + " " + location.getCity());
            }
        }
        Gson gson = new Gson();
        return gson.toJson(accountDetailsObject);
    }
}
