package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.DAO.*;
import com.application.cab_application.Models.*;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Services.PaymentService;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class PaymentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int currentAccount = CurrentUserHelper.getAccount();
        AccountDetails accountDetails = AccountDetailsDao.getAccountDetailsByAccountID(currentAccount);
        Ride ride = RidesDao.getRide(accountDetails.getCurrentRideID());
        String billID = request.getParameter("billId");
        int billId = Integer.parseInt(billID);
        String json = ReadJson.convertJsonToString(request.getReader());
        int id = PaymentService.savePayment(json);
        BillsDao.updatePaymentInBill(billId,id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getDriverId());
        AccountDetailsDao.updateCurrentRideIDAsNUll(ride.getDriverId());
        printWriter.write("{\"message\":\"Payment Done Successfully\"}");
    }
}