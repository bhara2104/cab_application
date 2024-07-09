package com.application.cab_application.DAO.V1;

import com.application.cab_application.Util.ConnectionPool;

import java.sql.Connection;
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


    public static int create(Map<String, Object> fields, String tableName) {
        String columns = String.join(" , ", fields.keySet());
        String params = String.join(" , ", fields.keySet().stream().map(key -> "?").toArray(String[]::new));
        String sql = "Insert into " + tableName + "(" + columns + ") values (" + params + ")";
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int idx = 1;
            for (Object object : fields.values()) {
                preparedStatement.setObject(idx++, object);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int value = resultSet.getInt(1);
                resultSet.close();
                return value;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return 0;
    }

    public static boolean update(Map<String, Object> fields, String tableName, int id) {
        String updateStatement = String.join(" , ", fields.keySet().stream().map(key -> key + "= ? ").toArray(String[]::new));
        String sql = "Update " + tableName + " set " + updateStatement + " where id = ?";
        System.out.println(sql);
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int idx = 1;
            for (Object object : fields.values()) {
                preparedStatement.setObject(idx++, object);
            }
            preparedStatement.setInt(idx, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return false;
    }

    public static boolean updateColumn(String columnName, Object value, String tableName, Integer id) {
        String sql = "update " + tableName + " set " + columnName + " = ? where id = ?";
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return false;
    }

    public static boolean updateColumn(String columnName, Object value, String tableName, String whereColumn, Object whereValue) {
        String sql = "update " + tableName + " set " + columnName + " = ? where " + whereColumn + " = ?";
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, whereValue);
            int affectedRows = preparedStatement.executeUpdate();
            connectionPool.removeConnection(connection);
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return false;
    }

    public static boolean update(Map<String, Object> fields, String tableName, Map<String, Object> whereClause) {
        String updateStatement = String.join(" , ", fields.keySet().stream().map(key -> key + "= ? ").toArray(String[]::new));
        String whereQuery = String.join(" and ", whereClause.keySet().stream().map(key -> key + "= ? ").toArray(String[]::new));
        String sql = "Update " + tableName + " set " + updateStatement + " where " + whereQuery;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int idx = 1;
            for (Object object : fields.values()) {
                preparedStatement.setObject(idx++, object);
            }
            for (Object object : whereClause.values()) {
                preparedStatement.setObject(idx++, object);
            }
            int affectedRows = preparedStatement.executeUpdate();
            connectionPool.removeConnection(connection);
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return false;
    }

    public static ResultSet find_by(String tableName, String fieldName, Object value) {
        String sql = "select * from " + tableName + " where " + fieldName + " = ?";
        ResultSet resultSet;
        System.out.println(sql);
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, value);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return null;
    }

    public static ResultSet find(int id, String tableName) {
        String sql = "select * from " + tableName + " where id = ?";
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            connectionPool.removeConnection(connection);
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                connectionPool.removeConnection(connection);
            }
        }
        return null;
    }

    public static ResultSet find_chain(String tableName, Map<String, Object> whereChain) {
        String whereChainQuery = String.join(" and ", whereChain.keySet().stream().map(key -> key + "= ? ").toArray(String[]::new));
        ResultSet resultSet;
        String sql = "select * from " + tableName + " where " + whereChainQuery;
        Connection connection = null;
        System.out.println(sql);
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int inc = 1;
            for (Object object : whereChain.values()) {
                preparedStatement.setObject(inc++, object);
            }
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) connectionPool.removeConnection(connection);
        }
        return null;
    }

    public static ResultSet find_by_sql(String sql, Map<String, Object> fields) {
        ResultSet resultSet;
        Connection connection;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int idx = 1;
            for (Object object : fields.values()) {
                preparedStatement.setObject(idx++, object);
            }
            resultSet = preparedStatement.executeQuery();
            connectionPool.removeConnection(connection);
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) connectionPool.removeConnection(connection);
        }
        return null;
    }

    public static ResultSet all(String tableName) {
        String sql = "select * from " + tableName;
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            connectionPool.removeConnection(connection);
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.removeConnection(connection);
        }
        return null;
    }
}
