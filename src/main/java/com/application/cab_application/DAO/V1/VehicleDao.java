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
        return BaseDao.update(vehicle.vehicleTableMapper(),"vehicles",vehicle.getId());
    }

    public static Vehicle getVehicle(int id) {
        ResultSet resultSet = BaseDao.find(id, "vehicles");
        return vehicleMapper(resultSet);
    }

    public static Vehicle vehicleMapper(ResultSet resultSet){
        try {
            Vehicle vehicle ;
            if(resultSet.next()){
                vehicle =  new Vehicle(resultSet.getInt("id"), resultSet.getString("model"),
                        VehicleType.fromCode(resultSet.getInt("vehicle_type")),
                        resultSet.getString("vehicle_number"), resultSet.getString("brand"),
                        resultSet.getInt("year"));
            }else{
                vehicle = new Vehicle();
            }
            resultSet.close();
            return vehicle ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Vehicle();
    }
}
