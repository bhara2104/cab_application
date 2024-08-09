package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Rating;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RatingsDao {
    public static void createRating(Rating rating) throws DbNotReachableException {
        BaseDao.create(rating.ratingTableMapper(), "ratings");
    }

    public static Rating getRating(int id) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find(id, "ratings");
        return ratingMapper(resultSet);
    }

    public static Rating ratingMapper(ResultSet resultSet) {
        Rating rating = null;
        try {
            while (resultSet.next()) {
                rating = new Rating(resultSet.getInt("id"), resultSet.getDouble("rating_value"), resultSet.getString("comments"));
            }
            return rating;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
        return rating;
    }
}
