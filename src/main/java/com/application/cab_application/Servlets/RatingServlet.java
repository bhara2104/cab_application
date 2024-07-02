package com.application.cab_application.Servlets;

import com.application.cab_application.DAO.RatingsDao;
import com.application.cab_application.Models.Rating;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RatingServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String reqBody = ReadJson.convertJsonToString(request.getReader());
        Rating rating = new Gson().fromJson(reqBody, Rating.class);
        RatingsDao.createRating(rating);
        PrintWriter printWriter = response.getWriter();
        response.setStatus(200);
        printWriter.write("{\"message\":\"Rating Added Successfully\"}");
    }
}
