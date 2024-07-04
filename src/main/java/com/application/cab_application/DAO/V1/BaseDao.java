package com.application.cab_application.DAO.V1;

import com.application.cab_application.Util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BaseDao {
    static ConnectionPool connectionPool;

    static {
        try {
            connectionPool = ConnectionPool.createConnectionPool();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public BaseDao() throws SQLException, ClassNotFoundException {
    }


    public static int create(Map<String, Object> fields, String tableName) {
        String columns = String.join(", ",fields.keySet());
        String params = String.join(",", fields.keySet().stream().map(key -> "?").toArray(String[]::new));
        String sql = "Insert into"+ tableName + "(" + columns + ") values (" + params + ")";
        ResultSet resultSet ;
        try (PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int index = 1 ;
            for(Object object : fields.values()){
                preparedStatement.setObject(index++,object);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int value = resultSet.getInt(1);
                resultSet.close();
                return value;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static boolean update(Map<String, Object> fields, String tableName, int id) {

    }

    public static boolean update(Map<String, Object> fields, String tableName, Map<String, Object> whereClause) {

    }

    public static ResultSet find_by(Map<String, Object> fields, String tableName) {

    }

    public static ResultSet find(int id, String tableName) {

    }

    public static ResultSet find_by_sql(String sql, Map<String, Object> fields, String tableName) {

    }
}
