package com.application.cab_application.Services;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateThread1 implements Runnable{
    String sql = "Update vehicles set brand = 'Dota' where id = 7";


    public void run(){
        try {
            ConnectionPool connectionPool = new ConnectionPool();
            for(int i = 0; i < 10 ; i++){
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
        } catch (DbNotReachableException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        // It will update for the first time and connection pool become full so there will be no connection to reuse
        // connection creating
        //connection creating
        //1
        //0
        //There is no connection left in the connection pool to reuse
        //1
        //0
        //There is no connection left in the connection pool to reuse

        // This is after changing connection pool size
        // connection creating
        //connection creating
        //connection creating
        //connection creating
        //connection creating
        //5
        //connection creating
        //4
        //3
        //2
        //1
        //0
        //connection creating
        //0
        //connection creating
        //connection creating
        //0
        //connection creating
        //0
        //5
        //4
        //0
        //3
        //2
        //1
        //0
        //0
        //0
        //0
        //0
    }
}
