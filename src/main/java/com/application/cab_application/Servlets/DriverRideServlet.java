package com.application.cab_application.Servlets;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

import com.application.cab_application.DAO.*;
import com.application.cab_application.Models.*;
import com.application.cab_application.Util.BillAmountGenerator;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class DriverRideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String accountId = request.getParameter("accountID");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        if (action != null && action.equals("getAvailableRides")) {
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(Integer.parseInt(accountId));
            int locationId = driverDetails.getCurrentLocationId();
            List<JsonObject> objectList = RidesDao.getAvailableRides(locationId);
            String responseValues = gson.toJson(objectList);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(responseValues);
        } else {
            List<JsonObject> objectList = RidesDao.getAllRideDetails(Integer.parseInt(accountId), "DRIVER");
            String responseValues = gson.toJson(objectList);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(responseValues);
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rideID = request.getParameter("RideID");
        String driverID = request.getParameter("DriverID");
        String actionName = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        if (actionName != null) {
            RideDetails rideDetails = RideDetailsDao.getRideDetails(Integer.parseInt(rideID));
            if (actionName.equals("startRide")) {
                rideDetails.setRequestStatus(RequestStatus.STARTED);
                rideDetails.setStartTime(new Timestamp(System.currentTimeMillis()));
                RideDetailsDao.updateRideDetails(rideDetails);
                response.setStatus(HttpServletResponse.SC_OK);
                printWriter.write("{\"message\":\"Ride Started Successfully\"}");
            } else if (actionName.equals("endRide")) {
                rideDetails.setRequestStatus(RequestStatus.ENDED);
                rideDetails.setEndTime(new Timestamp(System.currentTimeMillis()));
                RideDetailsDao.updateRideDetails(rideDetails);
                int billID = BillsDao.createBill(new Bill(BillAmountGenerator.generateBill(rideDetails.getStartTime().toString(), rideDetails.getEndTime().toString()), Integer.parseInt(rideID)));
                Bill bill = BillsDao.getBill(billID);
                printWriter.write(new Gson().toJson(bill));
            }
        } else {
            boolean success = RidesDao.updateDriverID(Integer.parseInt(rideID), Integer.parseInt(driverID));
            if (success) {
                RideDetails rideDetails = RideDetailsDao.getRideDetails(Integer.parseInt(rideID));
                rideDetails.setRequestStatus(RequestStatus.ACCEPTED);
                boolean updateRideStatus = RideDetailsDao.updateRideDetails(rideDetails);
                boolean check = AccountDetailsDao.updateCurrentRideID(Integer.parseInt(driverID), Integer.parseInt(rideID));
                System.out.println(check + " " + driverID);
                response.setStatus(HttpServletResponse.SC_OK);
                printWriter.write("{\"message\":\"Ride Accepted Successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                printWriter.write("{\"message\":\"There is an error while accepting Ride\"}");
            }
        }
    }

    public void destroy() {
    }
}