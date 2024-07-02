package com.application.cab_application.Servlets;

import java.io.*;
import java.sql.Driver;
import java.util.List;

import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.DAO.LocationDao;
import com.application.cab_application.DAO.VehicleDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Models.Location;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Services.DriverDetailsService;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.ReadJson;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class DriverDetailsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String ID = request.getParameter("account_id");
        int id = Integer.parseInt(ID);
        DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(id);
        Vehicle vehicle = VehicleDao.getVehicle(driverDetails.getVehicleId());
        Location location = LocationDao.getLocation(driverDetails.getCurrentLocationId());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement driverJson = gson.toJsonTree(driverDetails);
        JsonObject jsonObject = driverJson.getAsJsonObject();
        jsonObject.add("vehicle",gson.toJsonTree(vehicle));
        jsonObject.add("current_location",gson.toJsonTree(location));
        String responseString = gson.toJson(jsonObject);
        printWriter.write(responseString);
        response.setStatus(200);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        DriverDetails driverDetailsCheck = DriverDetailsDao.getDriverDetailsByAccountID(CurrentUserHelper.getAccount());
        Account account = AccountDao.getByID(CurrentUserHelper.getAccount());
        if(driverDetailsCheck.getId()!=0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            printWriter.write("{\"message\":\"Driver Details Already Exists\"}");
            return;
        }
        if(account.getAccountType()!= AccountType.DRIVER)
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        List<String> errors = DriverDetailsService.errors(requestBody);
        if(!errors.isEmpty()){
            response.setStatus(422);
            printWriter.write(new Gson().toJson(errors));
            return;
        }
        boolean result = DriverDetailsService.createDriverDetails(requestBody);
        if (result) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Driver Details created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Driver Details creation not successful\"}");
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
            boolean result = DriverDetailsService.updateDriverDetails(requestBody);
            if (result) {
                response.setStatus(HttpServletResponse.SC_OK);
                printWriter.write("{\"message\":\"Driver Location Updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                printWriter.write("{\"message\":\"Failed to Update\"}");
            }
    }
}