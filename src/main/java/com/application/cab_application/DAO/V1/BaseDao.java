package com.application.cab_application.DAO.V1;

import com.application.cab_application.Util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {

    static {
        try {
            ConnectionPool connectionPool = ConnectionPool.createConnectionPool();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseDao() throws SQLException, ClassNotFoundException {
    }


    public static int create(){

    }

    public static boolean update(){

    }

    public static ResultSet find_by(){

    }

    public static ResultSet find(){

    }

    public static ResultSet find(){

    }

    public static ResultSet find_by_sql(){

    }

    public static ResultSet select(){

    }
}
