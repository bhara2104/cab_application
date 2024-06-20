package com.application.cab_application.DAO;

import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.VehicleType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VehicleDao {
    public int createVehicle(Vehicle vehicle) {
        String query = "insert into vehicles(vehicle_type,vehicle_number,brand,year,model) values (?,?,?,?,?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1,vehicle.getVehicleType().getCode());
            preparedStatement.setString(2,vehicle.getVehicleNumber());
            preparedStatement.setString(3,vehicle.getBrand());
            preparedStatement.setInt(4,vehicle.getYear());
            preparedStatement.setString(5, vehicle.getModel());
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

    public Boolean updateVehicle(Vehicle vehicle){
        String query = "update vehicles set vehicle_number = ? , vehicle_type = ?, model = ? , year = ?, brand = ? where id = " + vehicle.getId();
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setString(1,vehicle.getVehicleNumber());
            preparedStatement.setInt(2,vehicle.getVehicleType().getCode());
            preparedStatement.setString(3,vehicle.getModel());
            preparedStatement.setInt(4,vehicle.getYear());
            preparedStatement.setString(5,vehicle.getBrand());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0 ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Vehicle getVehicle(int id){
        String query = "select * from vehicle where id = "+ id ;
        ResultSet resultSet ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();
                return new Vehicle(resultSet.getInt("id"),resultSet.getString("model"), VehicleType.fromCode(resultSet.getInt("vehicle_type")),resultSet.getString("vehicle_number"),resultSet.getString("brand"), resultSet.getInt("year")) ;
            } else {
                return new Vehicle() ;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Vehicle();
    }
}
