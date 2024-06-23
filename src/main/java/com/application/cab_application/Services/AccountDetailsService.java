package com.application.cab_application.Services;

import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Models.Vehicle;
import com.google.gson.Gson;

public class AccountDetailsService {

    public static String getAccountDetailsResponse(int id){
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(id);
        Gson gson = new Gson();
        return gson.toJson(accountDetails);
    }
}
