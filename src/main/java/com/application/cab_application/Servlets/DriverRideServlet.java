package com.application.cab_application.Servlets;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

import com.application.cab_application.DAO.V1.*;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.*;
import com.application.cab_application.Services.RideServices;
import com.application.cab_application.Util.BillAmountGenerator;
import com.application.cab_application.Util.CurrentUserHelper;
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
        int driverID = CurrentUserHelper.getAccount();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            if (action != null && action.equals("getAvailableRides")) {
                DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(driverID);
                int locationId = driverDetails.getCurrentLocationId();
                Vehicle vehicle = VehicleDao.getVehicle(driverDetails.getVehicleId());
                List<JsonObject> objectList = RidesDao.getAvailableRides(locationId, vehicle.getVehicleType());
                String responseValues = gson.toJson(objectList);
                response.setStatus(HttpServletResponse.SC_OK);
                writer.write(responseValues);
            } else {
                List<JsonObject> objectList = RidesDao.getAllRideDetails(driverID, "DRIVER");
                String responseValues = gson.toJson(objectList);
                response.setStatus(HttpServletResponse.SC_OK);
                writer.write(responseValues);
            }
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            writer.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int DriverID = CurrentUserHelper.getAccount();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        try {
            AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(DriverID);
            String actionName = request.getParameter("action");
            System.out.println(actionName);
            if (actionName != null) {
                Ride ride = RidesDao.getRide(accountDetails.getCurrentRideID());
                RideDetails rideDetails = RideDetailsDao.getRideDetails(ride.getId());
                if (ride.getDriverId() != DriverID) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    printWriter.write("{\"message\":\"Not Authorized to Perform this action\"}");
                    return;
                }
                if (actionName.equals("startRide")) {
                    rideDetails.setRequestStatus(RequestStatus.STARTED);
                    rideDetails.setStartTime(new Timestamp(System.currentTimeMillis()));
                    RideDetailsDao.updateRideDetails(rideDetails);
                    response.setStatus(HttpServletResponse.SC_OK);
                    printWriter.write("{\"message\":\"Ride Started Successfully\"}");
                } else if (actionName.equals("endRide")) {
                    rideDetails.setRequestStatus(RequestStatus.ENDED);
                    rideDetails.setEndTime(new Timestamp(System.currentTimeMillis()));
                    DriverDetailsDao.updateCurrentLocation(rideDetails.getToLocation(), accountDetails.getAccountId());
                    RideDetailsDao.updateRideDetails(rideDetails);
                    int billID = BillsDao.createBill(new Bill(BillAmountGenerator.generateBill(rideDetails.getStartTime(), rideDetails.getEndTime()), ride.getId()));
                    System.out.println(billID);
                    Bill bill = BillsDao.getBill(billID);
                    printWriter.write(new Gson().toJson(bill));
                }
            } else {
                String rideID = request.getParameter("RideID");
                List<String> errors = RideServices.runValidation(Integer.parseInt(rideID));
                Ride ride = RidesDao.getRide(Integer.parseInt(rideID));
                if (!errors.isEmpty()) {
                    response.setStatus(422);
                    printWriter.write(new Gson().toJson(errors));
                    return;
                }
                if (ride.getDriverId() != 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    printWriter.write("{\"Success: false\",\"message\":\"There was an Error\"}");
                    return;
                }
                boolean success = RidesDao.updateDriverID(Integer.parseInt(rideID), DriverID);
                if (success) {
                    RideDetails rideDetails = RideDetailsDao.getRideDetails(Integer.parseInt(rideID));
                    rideDetails.setRequestStatus(RequestStatus.ACCEPTED);
                    boolean updateRideStatus = RideDetailsDao.updateRideDetails(rideDetails);
                    boolean check = AccountDetailsDao.updateCurrentRideID(DriverID, Integer.parseInt(rideID));
                    System.out.println(check + " " + DriverID);
                    response.setStatus(HttpServletResponse.SC_OK);
                    printWriter.write("{\"message\":\"Ride Accepted Successfully\"}");

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    printWriter.write("{\"message\":\"There is an error while accepting Ride\"}");
                }
            }
        }catch (DbNotReachableException e){
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }

    public void destroy() {
    }
}