package com.application.cab_application.DAO;

import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDetailsDao {
    public DriverDetails getDriverDetailsByAccountID(int accountID){
        String query = "select * from driver_details where account_id ="+ accountID ;
        ResultSet rs ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)) {
            rs = preparedStatement.executeQuery();
            if(rs.isBeforeFirst()){
                rs.next();
                return new DriverDetails(rs.getInt("id"), rs.getInt("account_id"),rs.getString("license_number"),
                        rs.getBoolean("availability"), rs.getInt("current_location_id"), rs.getInt("vehicle_id"));
            }else{
                return new DriverDetails();
            }
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return new DriverDetails();
    }

    public int createDriverDetails(DriverDetails driverDetails){
        String query = "insert into driver_details(account_id,license_number,availability,vehicle_id,current_location_id) values (?,?,?,?,?)";
        ResultSet rs ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,driverDetails.getAccountID());
            preparedStatement.setString(2,driverDetails.getLicenseNumber());
            preparedStatement.setBoolean(3,true);
            preparedStatement.setInt(4,driverDetails.getVehicleId());
            preparedStatement.setInt(5,driverDetails.getCurrentLocationId());
            int result = preparedStatement.executeUpdate() ;
            if(result > 0){
                rs = preparedStatement.getGeneratedKeys();
                return rs.getInt(1);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean updateDriverDetails(DriverDetails driverDetails, int accountID){
        String query = "update driver_details set license_number = ?, current_location_id = ? where account_id = " + accountID ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setString(1,driverDetails.getLicenseNumber());
            preparedStatement.setInt(2,driverDetails.getCurrentLocationId());
            int rows = preparedStatement.executeUpdate();
            return rows > 0 ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false ;
    }

    public Boolean updateDriverAvailability(int accountID, Boolean value){
        String query = "update driver_details set availability = ? where account_id =" + accountID ;
        try(PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(query)){
            preparedStatement.setBoolean(1,value);
            int rows = preparedStatement.executeUpdate() ;
            return rows > 0 ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false ;
    }
}
