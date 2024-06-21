package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Services.LocationService;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class LocationServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int idValue = Integer.parseInt(id);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());

        int locationID = LocationService.addLocation(requestBody);

        if(locationID !=0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"message\":\"There was something unexpected Happened\"}");
        }else{
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Driver Details created successfully\", \"ID:\": "+ locationID + "}");
        }

    }

    public void destroy() {
    }
}