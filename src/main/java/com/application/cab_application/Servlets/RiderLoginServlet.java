package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.enums.AccountType;
import jakarta.servlet.http.*;

public class RiderLoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account loggedAccount = AccountService.authenticateUser(email,password, AccountType.RIDER);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(loggedAccount != null){
            HttpSession session = request.getSession(true);
            session.setAttribute("userID",loggedAccount.getId());
            session.setMaxInactiveInterval(7 * 60 * 60);
            response.addCookie(new Cookie("JSESSIONID", session.getId()));
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}