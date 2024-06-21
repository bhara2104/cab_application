package com.application.cab_application.DAO;

import com.application.cab_application.Models.Location;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LocationDao {
    public static int createLocation(Location location) {
        String query = "Insert into locations(city,latitude,longitude,landmark,pincode) values (?,?,?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, location.getCity());
            preparedStatement.setDouble(2, location.getLatitude());
            preparedStatement.setDouble(3, location.getLongitude());
            preparedStatement.setString(4, location.getLandmark());
            preparedStatement.setInt(5, location.getPinCode());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public static Location getLocation(int id) {
        String query = "select * from locations where id =" + id;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return new Location(resultSet.getInt("id"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("landmark"),
                        resultSet.getString("city"), resultSet.getInt("pincode"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Location();
    }

}
