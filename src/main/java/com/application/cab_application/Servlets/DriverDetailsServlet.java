package com.application.cab_application.Servlets;

import java.io.*;
import java.sql.Driver;

import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.Models.DriverDetails;
import com.application.cab_application.Models.Vehicle;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Services.DriverDetailsService;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
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
        Gson gson = new Gson();
        String responseString = gson.toJson(driverDetails);
        printWriter.write(responseString);
        response.setStatus(200);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
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
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Driver Details Updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Failed to Update\"}");
        }
    }

    public void destroy() {
    }
}