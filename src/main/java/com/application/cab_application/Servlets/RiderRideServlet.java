package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.RidesDao;
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

    }

    public void destroy() {
    }
}