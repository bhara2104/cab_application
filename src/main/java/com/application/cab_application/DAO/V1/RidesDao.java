package com.application.cab_application.DAO.V1;

import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.DAO.LocationDao;
import com.application.cab_application.DAO.VehicleDao;
import com.application.cab_application.Models.*;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.Util.PrettyPrintHelper;
import com.application.cab_application.enums.RequestStatus;
import com.application.cab_application.enums.VehicleType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RidesDao {
    public static int createRide(Ride ride) throws Exception {
        Map<String, Object> rideMap = ride.rideTableMapper();
        rideMap.remove("driver_id");
        return BaseDao.create(rideMap, "rides");
    }

    public static Ride getRide(int id) {
        ResultSet resultSet = BaseDao.find(id, "rides");
        return mapRide(resultSet);
    }


    public static Boolean updateDriverID(Integer rideID, Integer driverID) {
        return BaseDao.updateColumn("driver_id", driverID, "rides", rideID);
    }

    public static Ride mapRide(ResultSet resultSet) {
        Ride ride ;
        try {
            if (resultSet.next()) {
                ride = new Ride(resultSet.getInt("id"), resultSet.getInt("rider_id"), resultSet.getInt("driver_id"));
            }else{
                ride = new Ride();
            }
            resultSet.getStatement().close();
            return ride ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Ride();
    }

}

