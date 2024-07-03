package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.*;
import com.application.cab_application.Models.*;
import com.application.cab_application.Services.PaymentService;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class PaymentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        int currentAccount = CurrentUserHelper.getAccount();
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(currentAccount);
        Ride ride = RidesDao.getRide(accountDetails.getCurrentRideID());
        String billID = request.getParameter("billId");
        int billId;
        try {
            billId = Integer.parseInt(billID);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            printWriter.write("{\"message\":\"Enter Valid Ride ID\"}");
            return;
        }
        Bill bill = BillsDao.getBill(billId);
        if (bill.getId() == 0 || ride.getId() != bill.getRideID()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            printWriter.write("{\"message\":\"Enter Valid Ride ID\"}");
            return;
        }
        if(bill.getPaymentId()!=0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            printWriter.write("{\"message\":\"Payment Already Done \"}");
            return;
        }
        String json = ReadJson.convertJsonToString(request.getReader());
        List<String> error = PaymentService.errors(json);
        if(!error.isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(new Gson().toJson(error));
            return;
        }
        int id = PaymentService.savePayment(json);
        BillsDao.updatePaymentInBill(billId, id);
        response.setStatus(HttpServletResponse.SC_OK);
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getDriverId());
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getRiderId());
        printWriter.write("{\"message\":\"Payment Done Successfully\"}");
    }
}