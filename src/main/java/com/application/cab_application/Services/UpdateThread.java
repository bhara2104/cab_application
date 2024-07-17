package com.application.cab_application.Services;

import com.application.cab_application.Exception.DbNotReachableException;
import com.application.cab_application.Util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateThread implements Runnable {
    String sql = "Update vehicles set brand = 'Dota' where id = 7";


    public void run(){
        try {
            ConnectionPool connectionPool = ConnectionPool.getConnectionPoolInstance();
            for(int i = 0; i < 10000 ; i++){
                ResultSet resultSet ;
                System.out.println(Thread.currentThread().getName());
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                preparedStatement.close();
                resultSet.close();
                connectionPool.removeConnection(connection);
            }
        } catch (DbNotReachableException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
