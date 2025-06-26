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
import com.collier.personal_project.custom_exceptions.AuthorNotFoundException;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao_model.AuthorPOJO;

public class AuthorsDAOClass implements AuthorsDAOInterface{

    // Get a connection to the database
    private Connection dbConnection;

    @Override
    public boolean addAuthor(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteAuthorById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteAuthorByName(String name) {
        // TODO Auto-generated method stub
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
            while (rs.next()){
                int id = rs.getInt("author_id");
                String name = rs.getString("name");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                AuthorPOJO author = new AuthorPOJO(id, name, createdAt, updatedAt);
                authors.add(author);
            }
            return authors;
        } catch(ClassNotFoundException e) {
            System.err.println("getAllAuthors threw a ClassNotFoundException: " + e.getMessage());
        } catch(FileNotFoundException e){
            System.err.println("getAllAuthors threw a FileNotFoundException: " + e.getMessage());
        }catch(IOException e) {
            System.err.println("getAllAuthors threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAllAuthors threw a SQLException: " + e.getMessage());
        } catch(DBReturnNullConnectionException e) {
            System.err.println("getAllAuthors threw a DBReturnNullConnectionException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public AuthorPOJO getAuthorById(int id) {
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
        }catch (SQLException e) {
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
    public AuthorPOJO getAuthorByName(String name) {
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
        }catch (SQLException e) {
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
    public boolean updateAuthorById(int id, String name) {
        // TODO Auto-generated method stub
        return false;
    }

}
