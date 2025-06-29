package com.collier.personal_project.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.AuthorNotCreatedException;
import com.collier.personal_project.custom_exceptions.AuthorNotFoundException;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao_model.AuthorPOJO;

public class AuthorsDAOClass implements AuthorsDAOInterface {

    // Get a connection to the database
    private Connection dbConnection;

    @Override
    public boolean addAuthor(String name) throws AuthorNotCreatedException {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "INSERT INTO authors(name) VALUES(?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new AuthorNotCreatedException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("updateAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("updateAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("updateAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("updateAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("updateAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAuthorById(int id) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM authors WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new AuthorNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAuthorByName(String name) throws AuthorNotFoundException{
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM authors WHERE name = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new AuthorNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<AuthorPOJO> getAllAuthors() {
        List<AuthorPOJO> authors = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM authors";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("author_id");
                String name = rs.getString("name");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                AuthorPOJO author = new AuthorPOJO(id, name, createdAt, updatedAt);
                authors.add(author);
            }
            return authors;
        } catch (ClassNotFoundException e) {
            System.err.println("getAllAuthors threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAllAuthors threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAllAuthors threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAllAuthors threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAllAuthors threw a DBReturnNullConnectionException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public AuthorPOJO getAuthorById(int id) throws AuthorNotFoundException {
        AuthorPOJO author;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM authors WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                author = new AuthorPOJO(id,
                        rs.getString("name"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"));
            else
                throw new AuthorNotFoundException();
            return author;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public AuthorPOJO getAuthorByName(String name) throws AuthorNotFoundException {
        AuthorPOJO author;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM authors WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                author = new AuthorPOJO(rs.getInt("author_id"),
                        name,
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"));
            else
                throw new AuthorNotFoundException();
            return author;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean updateAuthorById(int id, String name) throws AuthorNotFoundException {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "UPDATE authors SET name = ? WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new AuthorNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("updateAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("updateAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("updateAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("updateAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("updateAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }
        return false;
    }

}
