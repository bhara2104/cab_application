package com.application.cab_application.Services;

import com.application.cab_application.DAO.RidesDao;
import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;

public class RideServices {
    public int createRide(String jsonBody){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject ride = jsonObject.getAsJsonObject("ride");
        JsonObject rideDetails = jsonObject.getAsJsonObject("ride_details");
        Ride rideObject = gson.fromJson(ride, Ride.class);
        RideDetails rideDetails1 = gson.fromJson(rideDetails, RideDetails.class);
        try(Connection connection = DatabaseConnector.getConnection()){
            connection.setAutoCommit(false);
            int id = RidesDao.createRide(rideObject);
            if(id !=0){
                rideDetails1.setRideID(id);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
