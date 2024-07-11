package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.*;
import com.application.cab_application.Util.PrettyPrintHelper;

import com.application.cab_application.enums.RequestStatus;
import com.application.cab_application.enums.VehicleType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.sql.ResultSet;
import java.util.*;

public class RidesDao {
    public static int createRide(Ride ride) throws DbNotReachableException {
        Map<String, Object> rideMap = ride.rideTableMapper();
        rideMap.remove("driver_id");
        return BaseDao.create(rideMap, "rides");
    }

    public static Ride getRide(int id) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find(id, "rides");
        return mapRide(resultSet);
    }


    public static Boolean updateDriverID(Integer rideID, Integer driverID) throws DbNotReachableException {
        return BaseDao.updateColumn("driver_id", driverID, "rides", rideID);
    }

    public static List<JsonObject> getAllRideDetails(int account_id, String account_type) throws DbNotReachableException {
        String sql;
        if (account_type.equals("DRIVER")) {
            sql = "SELECT \n" +
                    "    r.id, \n" +
                    "    r.driver_id, \n" +
                    "    r.rider_id, \n" +
                    "    rd.id AS ride_detail_id, \n" +
                    "    rd.vehicle_type,\n" +
                    "    rd.from_location_id, \n" +
                    "    rd.to_location_id, \n" +
                    "    rd.ride_status, \n" +
                    "    rd.start_time, \n" +
                    "    rd.end_time, \n" +
                    "    rd.ride_id\n" +
                    "FROM \n" +
                    "    rides AS r\n" +
                    "INNER JOIN \n" +
                    "    ride_details AS rd \n" +
                    "ON \n" +
                    "    r.id = rd.ride_id\n" +
                    "WHERE \n" +
                    "    r.driver_id = ? ";
        } else {
            sql = "SELECT \n" +
                    "    r.id, \n" +
                    "    r.driver_id, \n" +
                    "    r.rider_id, \n" +
                    "    rd.id AS ride_detail_id, \n" +
                    "    rd.vehicle_type,\n" +
                    "    rd.from_location_id, \n" +
                    "    rd.to_location_id, \n" +
                    "    rd.ride_status, \n" +
                    "    rd.start_time, \n" +
                    "    rd.end_time, \n" +
                    "    rd.ride_id\n" +
                    "FROM \n" +
                    "    rides AS r\n" +
                    "INNER JOIN \n" +
                    "    ride_details AS rd \n" +
                    "ON \n" +
                    "    r.id = rd.ride_id\n" +
                    "WHERE \n" +
                    "    r.rider_id = ? ";
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("checkableId", account_id);
        ResultSet resultSet = BaseDao.find_by_sql(sql, hashMap);
        return RidesListMapper(resultSet, account_type);

    }

    public static ArrayList<JsonObject> RidesListMapper(ResultSet rs, String account_type) {
        ArrayList<JsonObject> jsonObjects = new ArrayList<>();
        try {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                Ride ride = new Ride(rs.getInt("id"), rs.getInt("rider_id"), rs.getInt("driver_id"));
                RideDetails rideDetails = new RideDetails(rs.getInt("ride_detail_id"), rs.getInt("from_location_id"), rs.getInt("to_location_id"), RequestStatus.fromCode(rs.getInt("ride_status")), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"), rs.getInt("ride_id"), VehicleType.fromCode(rs.getInt("vehicle_type")));
                JsonElement rideElement = PrettyPrintHelper.prettyPrintHelper(ride);
                JsonObject jsonObjectRide = rideElement.getAsJsonObject();
                if (account_type.equals("RIDER") && rideDetails.getRequestStatus() != RequestStatus.CANCELLED && ride.getDriverId() != 0) {
                    DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(ride.getDriverId());
                    AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(ride.getDriverId());
                    Bill bill = BillsDao.getBillRideID(ride.getId());
                    if (rideDetails.getRequestStatus() == RequestStatus.ENDED && bill.getId() != 0) {
                        jsonObjectRide.addProperty("bill_amount", bill.getBillAmount());
                    }
                    jsonObjectRide.addProperty("driver_name", accountDetails.getName());
                    jsonObjectRide.addProperty("driver_license", driverDetails.getLicenseNumber());
                    rideElement = new Gson().toJsonTree(jsonObjectRide);
                }
                Location fromLocation = LocationDao.getLocation(rideDetails.getFromLocation());
                Location toLocation = LocationDao.getLocation(rideDetails.getToLocation());
                JsonElement rideDetailElement = PrettyPrintHelper.prettyPrintHelper(rideDetails);
                JsonObject rideDetailsObj = rideDetailElement.getAsJsonObject();
                rideDetailsObj.addProperty("fromLocation", fromLocation.getLandmark() + " " + fromLocation.getCity());
                rideDetailsObj.addProperty("toLocation", toLocation.getLandmark() + " " + toLocation.getCity());
                rideDetailElement = new Gson().toJsonTree(rideDetailsObj);
                jsonObject.add("ride", rideElement);
                jsonObject.add("rideDetails", rideDetailElement);
                jsonObjects.add(jsonObject);
            }
            rs.getStatement().close();
            return jsonObjects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static List<JsonObject> getAvailableRides(int locationID, VehicleType vehicleType) throws DbNotReachableException {
        String query = "SELECT \n" +
                "    r.id, \n" +
                "    r.driver_id, \n" +
                "    r.rider_id, \n" +
                "    rd.id AS ride_detail_id, \n" +
                "    rd.vehicle_type,\n" +
                "    rd.from_location_id, \n" +
                "    rd.to_location_id, \n" +
                "    rd.ride_status, \n" +
                "    rd.start_time, \n" +
                "    rd.end_time, \n" +
                "    rd.ride_id\n" +
                "FROM \n" +
                "    rides AS r\n" +
                "INNER JOIN \n" +
                "    ride_details AS rd \n" +
                "ON \n" +
                "    r.id = rd.ride_id\n" +
                "Where r.driver_id IS NULL and rd.vehicle_type = ? and rd.ride_status <> 5 and rd.from_location_id =  ?";
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("vehicle_type", vehicleType.getCode());
        map.put("location_id", locationID);
        return availableRideMapper(BaseDao.find_by_sql(query, map));
    }

    public static ArrayList<JsonObject> availableRideMapper(ResultSet rs) {
        ArrayList<JsonObject> jsonObjects = new ArrayList<>();
        try {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                Ride ride = new Ride(rs.getInt("id"), rs.getInt("rider_id"), rs.getInt("driver_id"));
                RideDetails rideDetails = new RideDetails(rs.getInt("ride_detail_id"), rs.getInt("from_location_id"), rs.getInt("to_location_id"), RequestStatus.fromCode(rs.getInt("ride_status")), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"), rs.getInt("ride_id"), VehicleType.fromCode(rs.getInt("vehicle_type")));
                Location fromLocation = com.application.cab_application.DAO.LocationDao.getLocation(rideDetails.getFromLocation());
                Location toLocation = com.application.cab_application.DAO.LocationDao.getLocation(rideDetails.getToLocation());
                JsonElement rideElement = PrettyPrintHelper.prettyPrintHelper(ride);
                JsonElement rideDetailElement = PrettyPrintHelper.prettyPrintHelper(rideDetails);
                JsonObject rideDetailsObj = rideDetailElement.getAsJsonObject();
                rideDetailsObj.addProperty("fromLocation", fromLocation.getLandmark() + " " + fromLocation.getCity());
                rideDetailsObj.addProperty("toLocation", toLocation.getLandmark() + " " + toLocation.getCity());
                rideDetailElement = new Gson().toJsonTree(rideDetailsObj);
                jsonObject.add("ride", rideElement);
                jsonObject.add("rideDetails", rideDetailElement);
                jsonObjects.add(jsonObject);
            }
            rs.getStatement().close();
            return jsonObjects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }


    public static Ride mapRide(ResultSet resultSet) {
        Ride ride;
        try {
            if (resultSet.next()) {
                ride = new Ride(resultSet.getInt("id"), resultSet.getInt("rider_id"), resultSet.getInt("driver_id"));
            } else {
                ride = new Ride();
            }
            resultSet.getStatement().close();
            return ride;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Ride();
    }

}

