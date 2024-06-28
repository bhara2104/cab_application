package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.DAO.VehicleDao;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class VehicleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int currentAccountID = CurrentUserHelper.getAccount();
        DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(currentAccountID);
        Vehicle vehicle = VehicleDao.getVehicle(driverDetails.getVehicleId());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        if (driverDetails.getId() == 0 || vehicle.getId() == 0) {
            response.setStatus(404);
            return;
        }
        response.setStatus(200);
        printWriter.write(new Gson().toJson(vehicle));
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int currentAccountID = CurrentUserHelper.getAccount();
        DriverDetails driverDetails = DriverDetailsDao.getDriverDetailsByAccountID(currentAccountID);
        int vehicleID = driverDetails.getVehicleId();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String jsonBody = ReadJson.convertJsonToString(request.getReader());
        Vehicle updateVehicle = new Gson().fromJson(jsonBody, Vehicle.class);
        if (driverDetails.getId() == 0 || vehicleID == 0) {
            response.setStatus(404);
            return;
        }
        if (vehicleID != updateVehicle.getId()) {
            response.setStatus(403);
            return;
        }
        boolean result = VehicleDao.updateVehicle(updateVehicle);
        if(result){
            response.setStatus(200);
            printWriter.write("{\"Success: true\",\"message\":\"Updated Successfully\"}");
        }else{
            response.setStatus(200);
            printWriter.write("{\"Success: true\",\"message\":\"Failed to Update\"}");
        }
    }

}