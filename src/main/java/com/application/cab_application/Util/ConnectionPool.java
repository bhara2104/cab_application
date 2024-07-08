package com.application.cab_application.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
public class ConnectionPool {
    private static final int POOL_SIZE = 10 ;
    private static final int MAX_POOL_SIZE = 500 ;
    private static final String URL = "jdbc:postgresql://localhost:5432/cab_booking" ;
    private static final String USERNAME = "bharathkumar" ;
    private static final String PASSWORD = "Bharath123";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    public static ConnectionPool createConnectionPool() throws SQLException, ClassNotFoundException {
        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for(int i = 0 ; i < POOL_SIZE ; i++){
            pool.add(createConnection());
        }
        return new ConnectionPool(pool);
    }

    public ConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connectionPool.isEmpty()){
            if(usedConnections.size() < MAX_POOL_SIZE){
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("There is no connection Left in the connection POOL to Reuse");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size()-1);
        if(!connection.isValid(5)){
            connection = createConnection();
        }
        usedConnections.add(connection);
        return connection;
    }

    public boolean removeConnection(Connection connection){
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getConnectionsInUse(){
        return usedConnections.size();
    }

    public int getAvailableConnectionSize(){
        return connectionPool.size();
    }
}
