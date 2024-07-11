package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDetailsDao {
    public static DriverDetails getDriverDetailsByAccountID(int accountID) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find_by("driver_details", "account_id", accountID);
        return driverDetailsMapper(resultSet);
    }

    public static int createDriverDetails(DriverDetails driverDetails) throws DbNotReachableException {
        return BaseDao.create(driverDetails.driverDetailsObject(), "driver_details");
    }

    public static boolean updateCurrentLocation(int locationID, int accountID) throws DbNotReachableException {
        return BaseDao.updateColumn("current_location_id", locationID, "driver_details","account_id",accountID);
    }

    public static DriverDetails driverDetailsMapper(ResultSet rs){
        try {
            DriverDetails driverDetails ;
            if(rs.next()){
                driverDetails = new DriverDetails(rs.getInt("id"), rs.getInt("account_id"), rs.getString("license_number"),
                        rs.getBoolean("availability"), rs.getInt("current_location_id"), rs.getInt("vehicle_id"));
            }else{
                driverDetails = new DriverDetails();
            }
            rs.getStatement().close();
            return driverDetails;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new DriverDetails();
    }
}
