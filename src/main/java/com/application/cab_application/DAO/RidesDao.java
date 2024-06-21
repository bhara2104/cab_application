package com.application.cab_application.DAO;

import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.Util.PrettyPrintHelper;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RidesDao {
    public static int createRide(Ride ride) throws Exception {
        String query = "insert into rides(rider_id) values (?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ride.getRiderId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return 0;
    }

    public static Ride getRide(int id) {
        String query = "select * from rides where id =" + id;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return new Ride(resultSet.getInt("id"), resultSet.getInt("rider_id"), resultSet.getInt("driver_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Ride();
    }

    public Boolean updateDriverID(int rideID, int driverID) {
        String query = "update rides set driver_id = ? where rideID =" + rideID;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, driverID);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static List<JsonObject> getAllRideDetails(int account_id, String account_type) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        String sql;
        if (account_type.equals("DRIVER")) {
            sql = "SELECT \n" +
                    "    r.id, \n" +
                    "    r.driver_id, \n" +
                    "    r.rider_id, \n" +
                    "    rd.id AS ride_detail_id, \n" +
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
                    "    r.driver_id =  " + account_id;
        } else {
            sql = "SELECT \n" +
                    "    r.id, \n" +
                    "    r.driver_id, \n" +
                    "    r.rider_id, \n" +
                    "    rd.id AS ride_detail_id, \n" +
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
                    "    r.rider_id = " + account_id;
        }

        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                Ride ride = new Ride(rs.getInt("id"), rs.getInt("rider_id"), rs.getInt("driver_id"));
                RideDetails rideDetails = new RideDetails(rs.getInt("ride_detail_id"),rs.getInt("from_location_id"),rs.getInt("to_location_id"), RequestStatus.fromCode(rs.getInt("ride_status")),rs.getTimestamp("start_time"), rs.getTimestamp("end_time"), rs.getInt("ride_id"));
                JsonElement rideElement= PrettyPrintHelper.prettyPrintHelper(ride);
                JsonElement rideDetailElement = PrettyPrintHelper.prettyPrintHelper(rideDetails);
                jsonObject.add("ride", rideElement);
                jsonObject.add("rideDetails",rideDetailElement);
                jsonObjects.add(jsonObject);
            }
            return jsonObjects ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
