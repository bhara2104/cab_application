package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.DAO.BillsDao;
import com.application.cab_application.Models.Bill;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "BillServlet", value = "/BillServlet")
public class BillServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rideID = request.getParameter("rideID");
        int rideId = Integer.parseInt(rideID);
        Bill bill = BillsDao.getBill(rideId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write(new Gson().toJson(bill));
    }
}