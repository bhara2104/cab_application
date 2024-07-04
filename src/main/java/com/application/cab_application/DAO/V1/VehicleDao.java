package com.application.cab_application.DAO.V1;

import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.VehicleType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VehicleDao {
    public static int createVehicle(Vehicle vehicle) {
        return BaseDao.create(vehicle.vehicleTableMapper(), "vehicles");
    }

    public static Boolean updateVehicle(Vehicle vehicle) {
        String query = "update vehicles set vehicle_number = ? , vehicle_type = ?, model = ? , year = ?, brand = ? where id = ?";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getVehicleNumber());
            preparedStatement.setInt(2, vehicle.getVehicleType().getCode());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setString(5, vehicle.getBrand());
            preparedStatement.setInt(6, vehicle.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Vehicle getVehicle(int id) {
        String query = "select * from vehicles where id = ?" ;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return new Vehicle(resultSet.getInt("id"), resultSet.getString("model"),
                        VehicleType.fromCode(resultSet.getInt("vehicle_type")),
                        resultSet.getString("vehicle_number"), resultSet.getString("brand"),
                        resultSet.getInt("year"));
            } else {
                return new Vehicle();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Vehicle();
    }
}
