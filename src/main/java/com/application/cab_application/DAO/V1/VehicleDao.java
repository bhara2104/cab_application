package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.DatabaseConnector;
import com.application.cab_application.enums.VehicleType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VehicleDao {
    public static int createVehicle(Vehicle vehicle) throws DbNotReachableException {
        return BaseDao.create(vehicle.vehicleTableMapper(), "vehicles");
    }

    public static Boolean updateVehicle(Vehicle vehicle) throws DbNotReachableException {
        return BaseDao.update(vehicle.vehicleTableMapper(),"vehicles",vehicle.getId());
    }

    public static Vehicle getVehicle(int id) throws DbNotReachableException {
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
            return vehicle ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return new Vehicle();
    }
}
