package com.application.cab_application.Services;

import com.application.cab_application.DAO.*;
import com.application.cab_application.Models.*;
import com.application.cab_application.Util.DatabaseConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Connection;

public class RideServices {
    public static boolean createRide(String jsonBody) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject ride = jsonObject.getAsJsonObject("ride");
        JsonObject rideDetails = jsonObject.getAsJsonObject("ride_details");
        Ride rideObject = gson.fromJson(ride, Ride.class);
        int riderID = rideObject.getRiderId();
        RideDetails rideDetails1 = gson.fromJson(rideDetails, RideDetails.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int id = RidesDao.createRide(rideObject);
                if (id == 0) {
                    return false;
                }
                AccountDetailsDao.updateCurrentRideID(riderID, id);
                rideDetails1.setRideID(id);
                RideDetailsDao.createRideDetail(rideDetails1);
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static JsonObject rideDetails(int rideID) {
        Ride ride = RidesDao.getRide(rideID);
        RideDetails rideDetails = RideDetailsDao.getRideDetails(rideID);
        Gson gson = new GsonBuilder().setPrettyPrinting().create() ;
        JsonElement rideJsonElement = gson.toJsonTree(ride);
        JsonElement rideDetailsJsonElement = gson.toJsonTree(rideDetails);
        JsonObject responseObject = new JsonObject();
        if(ride.getDriverId() != 0){
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(ride.getDriverId());
            AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(ride.getDriverId());
            Vehicle vehicle = VehicleDao.getVehicle(driverDetails.getVehicleId());
            JsonObject rideJsonObject = rideJsonElement.getAsJsonObject();
            rideJsonObject.addProperty("driver_name", accountDetails.getName());
            JsonElement vehicleJson = gson.toJsonTree(vehicle);
            rideJsonObject.add("vehicle", vehicleJson);
            rideDetailsJsonElement = gson.toJsonTree(rideJsonObject);
        }
        responseObject.add("ride", rideJsonElement);
        Location fromLocation = LocationDao.getLocation(rideDetails.getFromLocation());
        Location toLocation = LocationDao.getLocation(rideDetails.getToLocation());
        responseObject.add("rideDetails", rideDetailsJsonElement);

        return responseObject;
    }
}
