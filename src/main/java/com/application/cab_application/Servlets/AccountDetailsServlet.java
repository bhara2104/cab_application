package com.application.cab_application.Servlets;

import com.application.cab_application.Exception.DbNotReachableException;
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
        System.out.println(id);
        try {
            String result = AccountDetailsService.getAccountDetailsResponse(id);
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(result);
        } catch (DbNotReachableException e){
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }
}
