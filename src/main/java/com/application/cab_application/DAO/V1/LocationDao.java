package com.application.cab_application.DAO.V1;

import com.application.cab_application.Models.Location;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationDao {
    public static Location getLocation(int id) {
        ResultSet resultSet = BaseDao.find(id, "locations");
        return locationMapper(resultSet);
    }

    public static List<Location> locationsList(){
        ResultSet resultSet = BaseDao.all("locations");
        return locationListMapper(resultSet);
    }

    public static Location locationMapper(ResultSet resultSet){
        try {
            Location location;
            if(resultSet.next()){
                location = new Location(resultSet.getInt("id"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("landmark"),
                        resultSet.getString("city"), resultSet.getInt("pincode"));
            }else{
                location = new Location();
            }
            resultSet.getStatement().close();
            return location ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Location();
    }


    public static List<Location> locationListMapper(ResultSet resultSet){
        List<Location> locationList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                locationList.add(new Location(resultSet.getInt("id"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("landmark"),
                        resultSet.getString("city"), resultSet.getInt("pincode")));
            }
            resultSet.getStatement().close();
            return locationList ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return locationList ;
    }
}
