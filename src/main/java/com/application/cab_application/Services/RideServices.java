package com.application.cab_application.Services;

import com.application.cab_application.DAO.V1.*;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.*;
import com.application.cab_application.Util.ConnectionPool;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RideServices {
    private static final List<String> rideErrors = new ArrayList<>();

    public static boolean createRide(String jsonBody) throws DbNotReachableException {
        Gson gson = new Gson();
        Ride rideObject = new Ride();
        rideObject.setRiderId(CurrentUserHelper.getAccount());
        int riderID = rideObject.getRiderId();
        RideDetails rideDetails1 = gson.fromJson(jsonBody, RideDetails.class);
        if (LocationService.validateRideLocation(rideDetails1.getFromLocation())) {
            rideErrors.add("Enter Valid From Location ID");
            return false;
        }
        if (LocationService.validateRideLocation(rideDetails1.getToLocation())) {
            rideErrors.add("Enter Valid To Location");
            return false;
        }
        if (rideDetails1.getFromLocation() == rideDetails1.getToLocation()) {
            rideErrors.add("From and To Location Can't be Same");
            return false;
        }
        int id = RidesDao.createRide(rideObject);
        if (id == 0) {
            return false;
        }
        AccountDetailsDao.updateCurrentRideID(riderID, id);
        rideDetails1.setRideID(id);
        rideDetails1.setRequestStatus(RequestStatus.WAITING);
        int rideDetail = RideDetailsDao.createRideDetail(rideDetails1);
        return rideDetail != 0;
    }

    public static List<String> getRideErrors() {
        return rideErrors;
    }


    public static JsonObject rideDetailsRider(int rideID) throws DbNotReachableException {
        Ride ride = RidesDao.getRide(rideID);
        RideDetails rideDetails = RideDetailsDao.getRideDetails(rideID);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement rideJsonElement = gson.toJsonTree(ride);
        JsonElement rideDetailsJsonElement = gson.toJsonTree(rideDetails);
        JsonObject responseObject = new JsonObject();
        if (ride.getDriverId() != 0) {
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(ride.getDriverId());
            AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(ride.getDriverId());
            Account account = AccountDao.getByID(ride.getDriverId());
            Vehicle vehicle = VehicleDao.getVehicle(driverDetails.getVehicleId());
            JsonObject rideJsonObject = rideJsonElement.getAsJsonObject();
            rideJsonObject.addProperty("driver_name", accountDetails.getName());
            rideJsonObject.addProperty("driver_license", driverDetails.getLicenseNumber());
            rideJsonObject.addProperty("driver_number", account.getPhoneNumber());
            JsonElement vehicleJson = gson.toJsonTree(vehicle);
            rideJsonObject.add("vehicle", vehicleJson);
            rideJsonElement = gson.toJsonTree(rideJsonObject);
        }
        responseObject.add("ride", rideJsonElement);
        Location fromLocation = LocationDao.getLocation(rideDetails.getFromLocation());
        Location toLocation = LocationDao.getLocation(rideDetails.getToLocation());
        JsonObject rideDetail = rideDetailsJsonElement.getAsJsonObject();
        rideDetail.addProperty("from_location", fromLocation.getLandmark() + " " + fromLocation.getCity());
        rideDetail.addProperty("to_location", toLocation.getLandmark() + " " + toLocation.getCity());

        responseObject.add("rideDetails", rideDetailsJsonElement);

        return responseObject;
    }

    public static JsonObject rideDetailsDriver(int rideID) throws DbNotReachableException {
        Ride ride = RidesDao.getRide(rideID);
        RideDetails rideDetails = RideDetailsDao.getRideDetails(rideID);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement rideJsonElement = gson.toJsonTree(ride);
        JsonElement rideDetailsJsonElement = gson.toJsonTree(rideDetails);
        JsonObject responseObject = new JsonObject();
        Account rider = AccountDao.getByID(ride.getRiderId());
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(CurrentUserHelper.getAccount());
        responseObject.add("ride", rideJsonElement);
        Location fromLocation = LocationDao.getLocation(rideDetails.getFromLocation());
        Location toLocation = LocationDao.getLocation(rideDetails.getToLocation());
        JsonObject rideDetail = rideDetailsJsonElement.getAsJsonObject();
        rideDetail.addProperty("rider_name", accountDetails.getName());
        rideDetail.addProperty("rider_phone", rider.getPhoneNumber());
        rideDetail.addProperty("from_location", fromLocation.getLandmark() + " " + fromLocation.getCity());
        rideDetail.addProperty("to_location", toLocation.getLandmark() + " " + toLocation.getCity());

        responseObject.add("rideDetails", rideDetailsJsonElement);

        return responseObject;
    }


    public static List<String> runValidation(int rideID) throws DbNotReachableException {
        List<String> errors = new ArrayList<>();
        Ride ride = RidesDao.getRide(rideID);
        if (ride.getId() == 0) {
            errors.add("Enter Valid Ride ID to continue");
        }
        return errors;
    }

    public static void clearErrors() {
        rideErrors.clear();
    }
}
