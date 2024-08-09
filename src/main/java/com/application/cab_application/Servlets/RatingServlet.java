package com.application.cab_application.Servlets;

import com.application.cab_application.DAO.V1.RatingsDao;
import com.application.cab_application.DAO.V1.RidesDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Rating;
import com.application.cab_application.Models.Ride;
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
        PrintWriter printWriter = response.getWriter();
        try {
            Ride ride = RidesDao.getRide(rating.getRideID());
            if (rating.getRideID() == 0 || ride.getId() == 0) {
                response.setStatus(400);
                printWriter.write("{\"message\":\"Enter Valid RideID\"}");
                return;
            }
            RatingsDao.createRating(rating);
            response.setStatus(200);
            printWriter.write("{\"message\":\"Rating Added Successfully\"}");
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        PrintWriter printWriter = response.getWriter();
        int idInt;
        try {
            idInt = Integer.parseInt(id);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"message\":\"Enter Valid Rating ID\"}");
            return;
        }
        try {
            Rating rating = RatingsDao.getRating(idInt);
            response.setStatus(200);
            printWriter.write(new Gson().toJson(rating));
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }
}
