package com.application.cab_application.Services;

import com.application.cab_application.DAO.RideDetailsDao;
import com.application.cab_application.DAO.RidesDao;
import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
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
        RideDetails rideDetails1 = gson.fromJson(rideDetails, RideDetails.class);
        try (Connection connection = DatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int id = RidesDao.createRide(rideObject);
                if (id == 0) {
                    return false;
                }
                rideDetails1.setRideID(id);
                RideDetailsDao.createRiderDetail(rideDetails1);
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create() ;
        RideDetails rideDetails = RideDetailsDao.getRideDetails(rideID);
        JsonElement rideJsonElement = gson.toJsonTree(ride);
        JsonElement rideDetailsJsonElement = gson.toJsonTree(rideDetails);

        JsonObject responseObject = new JsonObject();
        responseObject.add("ride", rideJsonElement);
        responseObject.add("rideDetails", rideDetailsJsonElement);

        return responseObject;
    }
}
