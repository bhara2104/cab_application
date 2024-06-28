package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.ReadJson;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;



public class DriverLoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = ReadJson.convertJsonToString(request.getReader());
        JsonObject jsonObject = new Gson().fromJson(requestBody, JsonObject.class);
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        Account loggedAccount = AccountService.authenticateUser(email,password, AccountType.DRIVER);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(loggedAccount != null){
            HttpSession session = request.getSession(true);
            session.setAttribute("userID",loggedAccount.getId());
            session.setAttribute("accountType", "DRIVER");
            session.setMaxInactiveInterval(7 * 60 * 60);
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}