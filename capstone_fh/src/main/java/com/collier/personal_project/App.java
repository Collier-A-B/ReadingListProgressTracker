package com.collier.personal_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // Test DB Connection
        try {
            Connection dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());
            
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } 
        catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: " + e.getMessage());
        } 
        catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver class not found: " + e.getMessage());
        } 
        catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("Database connection returned null: " + e.getMessage());
        }
        catch (Exception e) {
        }
    }
}
