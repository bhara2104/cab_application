package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;


import com.application.cab_application.DAO.V1.AccountDao;
import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.ReadJson;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class AccountServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        List<String> errors = AccountService.validateAccount(requestBody);
        if (!errors.isEmpty()) {
            response.setStatus(422);
            printWriter.write(new Gson().toJson(errors));
            return;
        }
        if (AccountDao.checkAccountExists(AccountService.returnAccount(requestBody))) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            printWriter.write("{\"message\":\"Account already Exists\"}");
            return;
        }
        int result = AccountService.createAccount(requestBody);

        if (result != 0) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userID", result);
            Account account = AccountDao.getByID(result);
            session.setAttribute("accountType", account.getAccountType());
            session.setMaxInactiveInterval(7 * 60 * 60);
            response.addCookie(new Cookie("JSESSIONID", session.getId()));
            response.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.write("{\"message\":\"Account created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            printWriter.write("{\"message\":\"Account creation not successful\"}");
        }
    }
}