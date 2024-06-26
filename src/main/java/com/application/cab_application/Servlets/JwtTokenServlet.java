package com.application.cab_application.Servlets;

import java.io.*;

import com.application.cab_application.Models.Account;
import com.application.cab_application.Services.AccountService;
import com.application.cab_application.Util.JWTUtil;
import com.application.cab_application.Util.ReadJson;
import com.application.cab_application.enums.AccountType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public class JwtTokenServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = ReadJson.convertJsonToString(request.getReader());
        JsonObject jsonBody = new Gson().fromJson(body, JsonObject.class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        if (jsonBody.get("grant_type") == null) {
            printWriter.write("{\"message\":\"Specify Valid Grant Type\"}");
            response.setStatus(401);
            return;
        }
        String grantType = jsonBody.get("grant_type").getAsString();
        JsonObject jsonObject = new JsonObject();
        if (grantType.equals("password")) {
            String email = jsonBody.get("email").getAsString();
            String password = jsonBody.get("password").getAsString();
            Account loggedAccount = AccountService.authenticateUser(email, password, AccountType.RIDER);
            if (loggedAccount != null) {
                String jwtToken = JWTUtil.generateAccessToken(loggedAccount.getId());
                String refreshToken = JWTUtil.createRefreshToken(loggedAccount.getId());
                jsonObject.addProperty("accessToken", jwtToken);
                jsonObject.addProperty("refreshToken", refreshToken);
                response.setStatus(200);
                printWriter.write(new Gson().toJson(jsonObject));
            } else {
                printWriter.write("{\"message\":\"Invalid Credentials\"}");
                response.setStatus(401);
            }
        } else if (grantType.equals("refresh_token")) {
            String token = jsonBody.get("refreshToken").getAsString();
            if (JWTUtil.verifyRefreshToken(token)) {
                int accountID = JWTUtil.getUserID(token);
                String accessToken = JWTUtil.generateAccessToken(accountID);
                jsonObject.addProperty("accessToken", accessToken);
                printWriter.write(new Gson().toJson(jsonObject));
            } else {
                printWriter.write("{\"message\":\"Invalid Refresh Token\"}");
                response.setStatus(401);
            }
        }
    }
}