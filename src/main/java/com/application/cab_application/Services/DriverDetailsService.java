package com.application.cab_application.Services;

import com.application.cab_application.DAO.V1.DriverDetailsDao;
import com.application.cab_application.DAO.V1.LocationDao;
import com.application.cab_application.DAO.V1.VehicleDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Models.Location;
import com.application.cab_application.Util.ConnectionPool;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.VehicleType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DriverDetailsService {
    public static List<String> errors(String jsonBody) throws DbNotReachableException {
        List<String> errors = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject driverDetailsObject = jsonObject.getAsJsonObject("driverDetails");
        JsonObject vehicleObject = jsonObject.getAsJsonObject("vehicle");
        if (!checkValidLocation(driverDetailsObject.get("currentLocationId").getAsString())) {
            errors.add("Enter Valid Location ID");
        }

        if(!checkValidVehicleType(vehicleObject.get("vehicleType").getAsString())){
            errors.add("Enter Valid Vehicle Type");
        }

        if(!checkValidVehicleYear(vehicleObject.get("year").getAsString())){
            errors.add("Enter Valid Year");
        }
        return errors;
    }

    public static boolean checkValidVehicleYear(String year) {
        try {
           int value = Integer.parseInt(year);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean checkValidVehicleType(String vehicleType) {
        try {
            VehicleType.valueOf(vehicleType);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static Boolean checkValidLocation(String id) throws DbNotReachableException {
        int locID;
        try {
            locID = Integer.parseInt(id);
        } catch (Exception e) {
            return false;
        }
        Location location = LocationDao.getLocation(locID);
        return location.getId() != 0;
    }

    public static boolean createDriverDetails(String jsonBody) throws DbNotReachableException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        JsonObject driverDetailsObject = jsonObject.getAsJsonObject("driverDetails");
        JsonObject vehicleObject = jsonObject.getAsJsonObject("vehicle");
        DriverDetails driverDetails = gson.fromJson(driverDetailsObject, DriverDetails.class);
        driverDetails.setAccountID(CurrentUserHelper.getAccount());
        Vehicle vehicle = gson.fromJson(vehicleObject, Vehicle.class);
        try (Connection connection = ConnectionPool.getConnectionPoolInstance().getConnection()) {
            connection.setAutoCommit(false);
            try {
                int id = VehicleDao.createVehicle(vehicle);
                if (id == 0) {
                    return false;
                }
                driverDetails.setVehicleId(id);
                int driverDetail = DriverDetailsDao.createDriverDetails(driverDetails);
                connection.commit();
                if (driverDetail != 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                connection.rollback();
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int value = DriverDetailsDao.createDriverDetails(driverDetails);
        return value != 0;
    }

    public static boolean updateDriverDetails(int currentLocation) throws DbNotReachableException {
        return DriverDetailsDao.updateCurrentLocation(currentLocation, CurrentUserHelper.getAccount());
    }
}
