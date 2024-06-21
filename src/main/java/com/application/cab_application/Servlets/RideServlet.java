package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Services.RideServices;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;

public class RideServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        boolean result = RideServices.createRide(requestBody);
        if (result) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Ride created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Ride creation not successful\"}");
        }
    }

    public void destroy() {
    }
}