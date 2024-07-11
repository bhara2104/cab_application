package com.application.cab_application.Servlets;

import java.io.*;
import java.util.List;

import com.application.cab_application.DAO.V1.UpiDataDao;
import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.UpiData;
import com.application.cab_application.Util.CurrentUserHelper;
import com.google.gson.Gson;
import jakarta.servlet.http.*;


public class SavedUPIServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accountId = CurrentUserHelper.getAccount();
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            List<UpiData> upiDataList = UpiDataDao.getSavedUPI(accountId);
            response.setStatus(200);
            printWriter.write(new Gson().toJson(upiDataList));
        }catch (DbNotReachableException e){
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            printWriter.write("{\"message\":\"We are very Sorry It's not You It's us, Try Reloading the Page\"}");
        }
    }
}