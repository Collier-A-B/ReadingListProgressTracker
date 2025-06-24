package com.collier.personal_project.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.Properties;

/**
 * ConnectionManager is responsible for managing database connections.
 * ConnectionManager is a utility singleton class that provides a method to get a connection to the database.
 */
public class ConnectionManager {
    
    private static Connection connection = null;
    
    private ConnectionManager() {
        // Private constructor to prevent instantiation
    }

    private static void makeConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(""));

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        Class.forName();
        connection = DriverManager.getConnection(url, user, password);
    }
}
