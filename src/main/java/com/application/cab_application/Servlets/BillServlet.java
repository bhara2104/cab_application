package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.DAO.V1.BillsDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.Bill;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class BillServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rideID = request.getParameter("rideID");
        int rideId = Integer.parseInt(rideID);
        Bill bill = null;
        PrintWriter printWriter = response.getWriter();
        try {
            bill = BillsDao.getBillRideID(rideId);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(new Gson().toJson(bill));
        } catch (DbNotReachableException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }
}