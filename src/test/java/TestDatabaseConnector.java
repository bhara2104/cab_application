import com.application.cab_application.Util.DatabaseConnector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDatabaseConnector {
    private static Connection connection;

    @BeforeAll
    public static void setConnection() {
        try {
            connection = DatabaseConnector.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkConnection() {
        assertNotNull(connection);
    }
}
