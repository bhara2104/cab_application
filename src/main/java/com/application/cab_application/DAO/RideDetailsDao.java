package com.application.cab_application.DAO;

import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.RequestStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RideDetailsDao {
    public int createRiderDetail(RideDetails rideDetails) {
        String query = "insert into ride_details(from_location_id, to_location_id, ride_status, start_time, end_time) values (?,?,?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
        String query = "select * from ride_details where id = " + id;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                return new RideDetails(resultSet.getInt("id"),resultSet.getInt("from_location_id"),
                        resultSet.getInt("to_location_id"), RequestStatus.fromCode(resultSet.getInt("request_status")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RideDetails();
    }
}
