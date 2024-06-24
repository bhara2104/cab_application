package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.DAO.RideDetailsDao;
import com.application.cab_application.DAO.RidesDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Models.Ride;
import com.application.cab_application.Models.RideDetails;
import com.application.cab_application.enums.RequestStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RiderRideServlet", value = "/RiderRideServlet")
public class RiderRideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        String accountId = request.getParameter("accountID");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        List<JsonObject> objectList = RidesDao.getAllRideDetails(Integer.parseInt(accountId),"RIDER");
        String responseValues = gson.toJson(objectList);
        response.setStatus(HttpServletResponse.SC_OK);
        writer.write(responseValues);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rideID = request.getParameter("rideID");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        Ride ride = RidesDao.getRide(Integer.parseInt(rideID));
        AccountDetailsDao.updateCurrentRideID(ride.getDriverId(),null);
        AccountDetailsDao.updateCurrentRideID(ride.getRiderId(),null);

        RideDetailsDao.updateRideStatus(Integer.parseInt(rideID), RequestStatus.CANCELLED);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        printWriter.write("{\"message\":\"Ride Cancelled Successfully\"}");
    }

    public void destroy() {
    }
}