package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.ReadJson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String requestBody = ReadJson.convertJsonToString(request.getReader());
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