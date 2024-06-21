package com.application.cab_application.Services;

import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.DAO.VehicleDao;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.DatabaseConnector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;

public class DriverDetailsService {
    public static boolean createDriverDetails(String jsonBody){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject driverDetailsObject = jsonObject.getAsJsonObject("driverDetails");
        JsonObject vehicleObject = jsonObject.getAsJsonObject("vehicle");
        DriverDetails driverDetails = gson.fromJson(driverDetailsObject, DriverDetails.class);
        Vehicle vehicle = gson.fromJson(vehicleObject, Vehicle.class);
        try(Connection connection = DatabaseConnector.getConnection()){
            connection.setAutoCommit(false);
            try {
                int id = VehicleDao.createVehicle(vehicle);
                if(id == 0){
                    return false ;
                }
                driverDetails.setVehicleId(id);
                int driverDetail = DriverDetailsDao.createDriverDetails(driverDetails);
                connection.commit();
                if(driverDetail != 0){
                    return true ;
                } else {
                    return false ;
                }
            } catch (Exception e){
                connection.rollback();
                System.out.println(e.getMessage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        int value = DriverDetailsDao.createDriverDetails(driverDetails);
        return value != 0 ;
    }
}
