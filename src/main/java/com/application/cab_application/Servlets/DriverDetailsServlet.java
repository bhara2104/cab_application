package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Services.DriverDetailsService;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class DriverDetailsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

    public void destroy() {
    }
}