package com.collier.personal_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao.AuthorsDAOClass;
import com.collier.personal_project.dao_model.AuthorPOJO;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void connectToDB() {
        // Test DB Connection
        try {
            Connection dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());
        } catch (SQLException e) {
            fail("SQL Exception: " + e.getMessage());
        } 
        catch (FileNotFoundException e) {
            fail("Configuration file not found: " + e.getMessage());
        } 
        catch (ClassNotFoundException e) {
            fail("JDBC Driver class not found: " + e.getMessage());
        } 
        catch (IOException e) {
            fail("I/O Exception: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            fail("Database connection returned null: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllAuthors() {
        AuthorsDAOClass authorDAO = new AuthorsDAOClass();
        List<AuthorPOJO> authorsInDB = authorDAO.getAllAuthors();

        System.out.println("\n\n\nPrinting all authors in db\n");
        for (AuthorPOJO author : authorsInDB) {
            System.out.println("result:   " + "name--" + author.getAuthorName() + ", id--" + author.getAuthorId());
        }
    }

    @Test
    public void getAuthorById() {
        
    }
}
