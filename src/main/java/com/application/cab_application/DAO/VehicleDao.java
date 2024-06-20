package com.application.cab_application.DAO;

import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VehicleDao {
    public int createVehicle(Vehicle vehicle) {
        String query = "insert into vehicles(vehicle_type,vehicle_number,brand,year,model) values (?,?,?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
