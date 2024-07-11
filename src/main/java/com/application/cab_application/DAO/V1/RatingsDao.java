package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Rating;

import java.sql.PreparedStatement;

public class RatingsDao {
    public static void createRating(Rating rating) throws DbNotReachableException {
        BaseDao.create(rating.ratingTableMapper(), "ratings");
    }
}
