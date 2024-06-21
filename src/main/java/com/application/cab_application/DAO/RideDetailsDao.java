package com.application.cab_application.DAO;

import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.RequestStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RideDetailsDao {
    public static int createRiderDetail(RideDetails rideDetails) {
        String query = "insert into ride_details(from_location_id, to_location_id, ride_status, start_time, end_time, ride_id) values (?,?,?,?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, rideDetails.getFromLocation());
            preparedStatement.setInt(2, rideDetails.getToLocation());
            preparedStatement.setInt(3, rideDetails.getRequestStatus().getCode());
            preparedStatement.setTimestamp(4, rideDetails.getStartTime());
            preparedStatement.setTimestamp(5, rideDetails.getEndTime());
            preparedStatement.setInt(6,rideDetails.getRideID());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public static RideDetails getRideDetails(int id) {
        String query = "select * from ride_details where ride_id = " + id;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return new RideDetails(resultSet.getInt("id"), resultSet.getInt("from_location_id"),
                        resultSet.getInt("to_location_id"), RequestStatus.fromCode(resultSet.getInt("request_status")),
                        resultSet.getTimestamp("start_time"), resultSet.getTimestamp("end_time"), resultSet.getInt("ride_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RideDetails();
    }

    public static boolean updateRideDetails(RideDetails rideDetails) {
        String query = "update ride_details set ride_status = ? , start_time = ? , end_time = ? where id ="+rideDetails.getId();
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,rideDetails.getRequestStatus().getCode());
            preparedStatement.setTimestamp(2,rideDetails.getStartTime());
            preparedStatement.setTimestamp(3,rideDetails.getEndTime());
            int result = preparedStatement.executeUpdate();
            return result > 0 ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false ;
    }
}
