package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.AccountDetailsDao;
import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.DAO.RidesDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.AccountDetails;
import com.application.cab_application.Models.DriverDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "DriverRideServlet", value = "/DriverRideServlet")
public class DriverRideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String accountId = request.getParameter("accountID");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        if(!action.isEmpty() && action.equals("getAvailableRides")){
            DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(Integer.parseInt(accountId));
            int locationId = driverDetails.getCurrentLocationId();
            List<JsonObject> objectList = RidesDao.getAvailableRides(locationId);
            String responseValues = gson.toJson(objectList);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(responseValues);
        }else {
            List<JsonObject> objectList = RidesDao.getAllRideDetails(Integer.parseInt(accountId), "DRIVER");
            String responseValues = gson.toJson(objectList);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.write(responseValues);
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void destroy() {
    }
}