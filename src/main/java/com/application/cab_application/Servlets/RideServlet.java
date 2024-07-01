package com.application.cab_application.Servlets;

import java.io.*;

import java.util.List;

import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Services.RideServices;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.ReadJson;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
public class RideServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Account account = AccountDao.getByID(CurrentUserHelper.getAccount());
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(CurrentUserHelper.getAccount());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        int rideId = accountDetails.getCurrentRideID();
        if(rideId == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"message\":\"No Ride is Currently Associated with this account\"}");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        JsonObject jsonObject;
        if(account.getAccountType() == AccountType.RIDER) {
            jsonObject = RideServices.rideDetailsRider(rideId);
        }else{
            jsonObject = RideServices.rideDetailsDriver(rideId);
        }
        String op = gson.toJson(jsonObject);
        printWriter.write(op);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        int accountID = CurrentUserHelper.getAccount();
        Account account = AccountDao.getByID(accountID);
        if(account.getAccountType() == AccountType.DRIVER){
            response.setStatus(403);
            printWriter.write("{\"message\":\"You are not authorized to perform this action\"}");
            return;
        }
        boolean result = RideServices.createRide(requestBody);
        List<String> errors = RideServices.getRideErrors();
        if(!errors.isEmpty()){
            response.setStatus(422);
            printWriter.write(new Gson().toJson(errors));
            RideServices.clearErrors();
            return;
        }
        if (result) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Ride created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Ride creation not successful\"}");
        }
    }
}