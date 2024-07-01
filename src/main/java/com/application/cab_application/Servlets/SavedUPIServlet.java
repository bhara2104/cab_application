package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.UpiDataDao;
import com.application.cab_application.Models.UpiData;
import com.application.cab_application.Util.CurrentUserHelper;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class SavedUPIServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accountId = CurrentUserHelper.getAccount();
        List<UpiData> upiDataList = UpiDataDao.getSavedUPI(accountId);
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        printWriter.write(new Gson().toJson(upiDataList));
    }
}