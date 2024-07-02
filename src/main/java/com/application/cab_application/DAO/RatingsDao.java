package com.application.cab_application.DAO;

import com.application.cab_application.Models.Rating;
import com.application.cab_application.Util.DatabaseConnector;

import java.sql.PreparedStatement;

public class RatingsDao {
    public static void createRating(Rating rating) {
        String sql = "insert into ratings(ride_id, rating_value, comments) values (?,?,?)";
        try (PreparedStatement preparedStatement = DatabaseConnector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, rating.getRideID());
            preparedStatement.setDouble(2, rating.getRatingValue());
            preparedStatement.setString(3, rating.getComments());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
