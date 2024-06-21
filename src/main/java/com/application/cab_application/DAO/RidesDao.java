package com.application.cab_application.DAO;

import com.application.cab_application.Models.Ride;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RidesDao {
    public static int createRide(Ride ride) throws Exception {
        String query = "insert into rides(rider_id,driver_id) values (?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ride.getRiderId());
            preparedStatement.setInt(2, ride.getDriverId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e ;
        }
        return 0;
    }

    private static Ride getRide(int id) {
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
            preparedStatement.setInt(1,driverID);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
