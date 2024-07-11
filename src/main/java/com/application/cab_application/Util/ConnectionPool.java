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
    private static final int MAX_POOL_SIZE = 50;
    private static final String URL = "jdbc:postgresql://localhost:5432/cab_booking";
    private static final String USERNAME = "bharathkumar";
    private static final String PASSWORD = "Bharath123";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static ConnectionPool connectionPoolClass = null;

    public static synchronized List<Connection> createConnectionPool() throws ClassNotFoundException, DbNotReachableException {
        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return pool;
    }

    public ConnectionPool() throws DbNotReachableException, ClassNotFoundException {
        connectionPool = createConnectionPool();
    }

    public static synchronized ConnectionPool getConnectionPoolInstance() throws DbNotReachableException, ClassNotFoundException {
        if (connectionPoolClass == null) {
            connectionPoolClass = new ConnectionPool();
        }
        return connectionPoolClass;
    }

    public static synchronized Connection createConnection() throws ClassNotFoundException, DbNotReachableException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DbNotReachableException("There is an Error in DB", e);
        }
    }

    public synchronized Connection getConnection() throws SQLException, ClassNotFoundException, DbNotReachableException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new DbNotReachableException("There is no connection left in the connection pool to reuse");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        if (!connection.isValid(5)) {
            connection.close();
            connection = createConnection();
        }
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void removeConnection(Connection connection) {
        try {
            if (!connection.isValid(5)) {
                connection.close();
            } else {
                connectionPool.add(connection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        usedConnections.remove(connection);
    }

    public int getConnectionsInUse() {
        return usedConnections.size();
    }

    public int getAvailableConnectionSize() {
        return connectionPool.size();
    }
}
