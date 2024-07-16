package com.application.cab_application.DAO.V1;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Models.UpiData;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UpiDataDao {
    public static void createUPIData(UpiData upiData) throws DbNotReachableException {
        BaseDao.create(upiData.upiDataTableMapper(), "upi_datas");
    }

    public static List<UpiData> getSavedUPI(int accountID) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find_by("upi_datas", "account_id", accountID);
        return upiDataListMapper(resultSet);
    }

    public static List<UpiData> upiDataListMapper(ResultSet resultSet){
        List<UpiData> upiDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                upiDataList.add(new UpiData(resultSet.getInt("id"), resultSet.getInt("account_id"), resultSet.getString("upi_id")));
            }
            resultSet.getStatement().close();
            return upiDataList ;
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    public static boolean checkUPIDateExist(String upiID) throws DbNotReachableException {
        ResultSet resultSet = BaseDao.find_by("upi_datas", "upi_id", upiID);
        try {
            boolean bool = resultSet.next();
            resultSet.getStatement().close();
            return bool;
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
// While using same connection for multiple threads whenever closing a result set will close all the currently opened results sets too