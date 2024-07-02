package com.application.cab_application.DAO;

import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDetailsDao {
    public static DriverDetails getDriverDetailsByAccountID(int accountID) {
        String query = "select * from driver_details where account_id = ?" ;
        ResultSet rs;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1,accountID);
            rs = preparedStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                return new DriverDetails(rs.getInt("id"), rs.getInt("account_id"), rs.getString("license_number"),
                        rs.getBoolean("availability"), rs.getInt("current_location_id"), rs.getInt("vehicle_id"));
            } else {
                return new DriverDetails();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return new DriverDetails();
    }

    public static int createDriverDetails(DriverDetails driverDetails) {
        String query = "insert into driver_details(account_id,license_number,availability,vehicle_id,current_location_id) values (?,?,?,?,?)";
        ResultSet rs;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, driverDetails.getAccountID());
            preparedStatement.setString(2, driverDetails.getLicenseNumber());
            preparedStatement.setBoolean(3, true);
            preparedStatement.setInt(4, driverDetails.getVehicleId());
            preparedStatement.setInt(5, driverDetails.getCurrentLocationId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                rs = preparedStatement.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static boolean updateCurrentLocation(int locationID, int accountID) {
        String query = "update driver_details set current_location_id = ? where account_id = ?" ;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, locationID););
            preparedStatement.setInt(2,accountID);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Boolean updateDriverAvailability(int accountID, Boolean value) {
        String query = "update driver_details set availability = ? where account_id = ?";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setBoolean(1, value);
            preparedStatement.setInt(2,accountID);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
