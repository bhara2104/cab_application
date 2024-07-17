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
            ConnectionPool connectionPool = ConnectionPool.getConnectionPoolInstance();
            for(int i = 0; i < 10 ; i++){
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                System.out.println(Thread.currentThread().getName() + " Sleeper");
                connectionPool.removeConnection(connection);
            }
        } catch (DbNotReachableException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
