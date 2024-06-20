package com.application.cab_application.DAO;

import com.application.cab_application.Models.Ride;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RidesDao {
    private int createRide(Ride ride) {
        String query = "insert into rides(rider_id,driver_id,ride_details_id) values (?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ride.getRiderId());
            preparedStatement.setInt(2, ride.getDriverId());
            preparedStatement.setInt(3, ride.getRideDetailsId());
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

    private Ride getRide(int id) {
        String query = "select * from rides where id =" + id;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return new Ride(resultSet.getInt("id"), resultSet.getInt("rider_id"), resultSet.getInt("driver_id"), resultSet.getInt("ride_details_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Ride();
    }
}
