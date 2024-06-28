package com.application.cab_application.Servlets;

import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.DAO.DriverDetailsDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountDetailsService;
import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.enums.AccountType;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AccountDetailsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        int id = CurrentUserHelper.getAccount();
        try {
            String result = AccountDetailsService.getAccountDetailsResponse(id);
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(result);
        } catch (Exception e){
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
