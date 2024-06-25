package com.application.cab_application.Servlets;

import java.io.*;


import com.application.cab_application.DAO.AccountDao;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;


public class AccountServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        if(AccountDao.checkAccountExist(AccountService.returnAccount(requestBody))){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            printWriter.write("{\"message\":\"Account already Exists\"}");
            return;
        }
        Boolean result = AccountService.createAccount(requestBody);
        if (result) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Account created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Account creation not successful\"}");
        }
    }
}