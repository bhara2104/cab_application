package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.V1.LocationDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Location;
import com.application.cab_application.Services.LocationService;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class LocationServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        try {
            if (action != null && action.equals("index")) {
                List<Location> locationList = LocationDao.locationsList();
                response.setStatus(200);
                printWriter.write(new Gson().toJson(locationList));
            } else {
                if (id == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                int idValue;
                try {
                    idValue = Integer.parseInt(id);
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Location location = LocationDao.getLocation(idValue);
                String jsonResponse = gson.toJson(location);
                response.setStatus(200);
                printWriter.write(jsonResponse);
            }
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());

        int locationID = LocationService.addLocation(requestBody);

        if (locationID == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"message\":\"There was something unexpected Happened\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Location created successfully\", \"ID:\": " + locationID + "}");
        }

    }

    public void destroy() {
    }
}