package com.collier.personal_project.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;

/**
 * ConnectionManager is responsible for managing database connections.
 * ConnectionManager is a utility singleton class that provides a method to get a connection to the database.
 */
public class ConnectionManager {
    
    private static Connection connection = null;
    
    private ConnectionManager() {
        // Private constructor to prevent instantiation
    }

    private static void makeConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, DBReturnNullConnectionException {
        Properties properties = new Properties();
        
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties")){
            if (input == null) {
                throw new FileNotFoundException("config.properties not found in classpath");
            }
            properties.load(input);
        } 

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        if (connection == null)
            throw new DBReturnNullConnectionException();
    }

    public static Connection getConnection() throws FileNotFoundException, IOException, 
                                                ClassNotFoundException, SQLException, DBReturnNullConnectionException {
        if (connection == null) {
            makeConnection();
            System.out.println("Connection Created");
        }
        return connection;
    }
}
