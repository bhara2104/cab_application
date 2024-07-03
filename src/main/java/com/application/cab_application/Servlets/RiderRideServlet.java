package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.DAO.RideDetailsDao;
import com.application.cab_application.DAO.RidesDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class RiderRideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        int accountId = CurrentUserHelper.getAccount();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        List<JsonObject> objectList = RidesDao.getAllRideDetails(accountId,"RIDER");
        String responseValues = gson.toJson(objectList);
        response.setStatus(HttpServletResponse.SC_OK);
        writer.write(responseValues);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accountID = CurrentUserHelper.getAccount();
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(accountID);
        RideDetails rideDetails = RideDetailsDao.getRideDetails(accountDetails.getCurrentRideID());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        Ride ride = RidesDao.getRide(accountDetails.getCurrentRideID());
        if(ride.getId() == 0){
            response.setStatus(422);
            printWriter.write("{\"message\":\"Invalid action\"}");
            return;
        }
        if(ride.getRiderId()!=accountID || rideDetails.getRequestStatus() == RequestStatus.CANCELLED || rideDetails.getRequestStatus()== RequestStatus.ENDED){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            printWriter.write("{\"message\":\"There was some error\"}");
            return;
        }
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getDriverId());
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getRiderId());

        RideDetailsDao.updateRideStatus(accountDetails.getCurrentRideID(), RequestStatus.CANCELLED);
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("{\"message\":\"Ride Cancelled Successfully\"}");
    }

    public void destroy() {
    }
}