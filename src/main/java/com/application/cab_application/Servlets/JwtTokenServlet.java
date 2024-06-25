package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.JWTUtil;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class JwtTokenServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account loggedAccount = AccountService.authenticateUser(email,password, AccountType.DRIVER);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonObject = new JsonObject();
        PrintWriter printWriter = response.getWriter();
        if(loggedAccount != null){
            String jwtToken = JWTUtil.generateAccessToken(loggedAccount.getId());
            String refreshToken = JWTUtil.generateRefreshToken(loggedAccount.getId());
            jsonObject.addProperty("accessToken", jwtToken);
            jsonObject.addProperty("refreshToken", refreshToken);
            response.setStatus(200);
            printWriter.write(new Gson().toJson(jsonObject));
        } else {
            response.setStatus(401);
        }
    }
}