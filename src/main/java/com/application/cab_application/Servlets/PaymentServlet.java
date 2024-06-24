package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.DAO.BillsDao;
import com.application.cab_application.DAO.PaymentDao;
import com.application.cab_application.Models.Bill;
import com.application.cab_application.Models.Payment;
import com.application.cab_application.Services.PaymentService;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "PaymentServlet ", value = "/PaymentServlet ")
public class PaymentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String billID = request.getParameter("billId");
        int billId = Integer.parseInt(billID);
        String json = ReadJson.convertJsonToString(request.getReader());
        int id = PaymentService.savePayment(json);
        BillsDao.updatePaymentInBill(billId,id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("{\"message\":\"Payment Done Successfully\"}");
    }
}