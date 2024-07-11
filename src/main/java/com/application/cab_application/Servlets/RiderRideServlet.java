package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.V1.AccountDetailsDao;
import com.application.cab_application.DAO.V1.RideDetailsDao;
import com.application.cab_application.DAO.V1.RidesDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;

public class RiderRideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        int accountId = CurrentUserHelper.getAccount();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        List<JsonObject> objectList = null;
        try {
            objectList = RidesDao.getAllRideDetails(accountId, "RIDER");
            String responseValues = gson.toJson(objectList);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(responseValues);
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            writer.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accountID = CurrentUserHelper.getAccount();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        try {
            AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(accountID);
            RideDetails rideDetails = RideDetailsDao.getRideDetails(accountDetails.getCurrentRideID());
            Ride ride = RidesDao.getRide(accountDetails.getCurrentRideID());
            if (ride.getId() == 0) {
                response.setStatus(422);
                printWriter.write("{\"message\":\"Invalid action\"}");
                return;
            }
            if (ride.getRiderId() != accountID || rideDetails.getRequestStatus() == RequestStatus.CANCELLED || rideDetails.getRequestStatus() == RequestStatus.ENDED) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                printWriter.write("{\"message\":\"There was some error\"}");
                return;
            }
            AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getDriverId());
            AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getRiderId());

            RideDetailsDao.updateRideStatus(accountDetails.getCurrentRideID(), RequestStatus.CANCELLED);
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write("{\"message\":\"Ride Cancelled Successfully\"}");
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }
}