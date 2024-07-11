package com.application.cab_application.Util;

import com.application.cab_application.Exception.DbNotReachableException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 500;
    private static final String URL = "jdbc:postgresql://localhost:5432/cab_booking";
    private static final String USERNAME = "bharathkumar";
    private static final String PASSWORD = "Bharath123";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static ConnectionPool connectionPoolClass = null;

    public static void createConnectionPool() throws ClassNotFoundException, DbNotReachableException {
        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
    }

    public ConnectionPool() throws DbNotReachableException, ClassNotFoundException {
        createConnectionPool();
    }

    public static synchronized ConnectionPool getConnectionPoolInstance() throws DbNotReachableException, ClassNotFoundException {
        if (connectionPoolClass == null)
            return new ConnectionPool();
        else
            return connectionPoolClass;
    }

    public static Connection createConnection() throws ClassNotFoundException, DbNotReachableException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DbNotReachableException("There is an Error in DB", e);
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException, DbNotReachableException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new DbNotReachableException("There is no connection Left in the connection Pool to Reuse");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        if (!connection.isValid(5)) {
            connection = createConnection();
        }
        usedConnections.add(connection);
        return connection;
    }

    public void removeConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public int getConnectionsInUse() {
        return usedConnections.size();
    }

    public int getAvailableConnectionSize() {
        return connectionPool.size();
    }
}
