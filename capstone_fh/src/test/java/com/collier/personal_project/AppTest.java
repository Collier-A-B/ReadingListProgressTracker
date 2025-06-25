package com.collier.personal_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao.AuthorsDAOClass;
import com.collier.personal_project.dao_model.AuthorPOJO;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private Map<Integer, AuthorPOJO> init_db_authors = new HashMap<>();

    @BeforeEach
    public void resetDB() {
        init_db_authors.clear();
        try {
            Connection dbConnection = ConnectionManager.getConnection();
            
            StringBuilder sqlBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("init_db.sql"))){
                String line;
                while ((line = reader.readLine()) != null){
                    sqlBuilder.append(line).append("\n");
                }
            }

            String[] sqlStatements = sqlBuilder.toString().split(";");

            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty())
                {
                    try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql.trim())){
                        preparedStatement.execute();
                        System.out.println("executed: " + sql.trim());
                    }
                }
            }
            
            String[] readAllTables = {
                "SELECT * FROM authors",
                "SELECT * FROM books",
                "SELECT * FROM genres",
                "SELECT * FROM users"
            };

        } catch (Exception e) {
        }
    }

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

        AuthorPOJO[] expected = {new AuthorPOJO(1, "George Orwell", new Timestamp(1,1,1,1,1,1,1), new Timestamp(1,1,1,1,1,1,1))};

        System.out.println("\n\n\nPrinting all authors in db\n");
        int iter = 0;
        for (AuthorPOJO author : authorsInDB) {
            System.out.println("result:   " + "name--" + author.getAuthorName() + ", id--" + author.getAuthorId());
            System.out.println("expected: " + "name--" + expected[iter].getAuthorName() + ", id--" + expected[iter].getAuthorId());
            assertTrue(author.getAuthorName().equals(expected[iter].getAuthorName()));
            assertTrue(author.getAuthorId() == expected[iter].getAuthorId());
            iter++;
        }
    }
}
