package tools;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private Connection connection;

    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_vehicle?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
