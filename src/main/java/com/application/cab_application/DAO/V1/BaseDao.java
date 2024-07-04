package com.application.cab_application.DAO.V1;

import com.application.cab_application.Util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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


    public static int create(Map<String,Object> fields, String tableName){

    }

    public static boolean update(Map<String,Object> fields, String tableName, int id){

    }

    public static boolean update(Map<String,Object> fields, String tableName,Map<String, Object> whereClause){

    }

    public static ResultSet find_by(Map<String,Object> fields, String tableName){

    }

    public static ResultSet find(int id, String tableName){

    }

    public static ResultSet find_by_sql(String sql, Map<String,Object> fields, String tableName){

    }
}
