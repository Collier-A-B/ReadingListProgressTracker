package com.collier.personal_project.dao.user;

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
import com.collier.personal_project.custom_exceptions.database_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.custom_exceptions.database_exceptions.UserNotCreatedException;
import com.collier.personal_project.custom_exceptions.database_exceptions.UserNotFoundException;
import com.collier.personal_project.dao_model.UserPOJO;

public class UsersDAOClass implements UsersDAOInterface{

    private Connection dbConnection;

    @Override
    public boolean addUser(String username, String password) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "INSERT INTO users(username, password, is_admin) VALUES(?, ?, ?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBoolean(3, false);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotCreatedException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("addUser threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("addUser threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("addUser threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("addUser threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("addUser threw a ClassNotFoundException: " + e.getMessage());
        } catch (UserNotCreatedException e) {
            System.err.println("addUser threw a UserNotCreatedException" + e.getMessage());
        }
        return false;
    }

    // needs to cascade delete user_books for deleted user
    @Override
    public boolean deleteUserById(int id) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteUserById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteUserById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteUserById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteUserById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteUserById threw a ClassNotFoundException: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("deleteUserById threw a UserNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, username);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteUserById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteUserById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteUserById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteUserById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteUserById threw a ClassNotFoundException: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("deleteUserById threw a UserNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<UserPOJO> getAllUsers() {
        List<UserPOJO> users = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM users";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                UserPOJO user = new UserPOJO(id, username, password, createdAt, updatedAt);
                users.add(user);
            }
            return users;
        } catch (ClassNotFoundException e) {
            System.err.println("getAllUsers threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAllUsers threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAllUsers threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAllUsers threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAllUsers threw a DBReturnNullConnectionException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public UserPOJO getUserById(int id) {
        UserPOJO user;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                user = new UserPOJO(id, 
                        rs.getString("username"), 
                        rs.getString("password"), 
                        rs.getTimestamp("created_at"), 
                        rs.getTimestamp("updated_at"));
            else
                throw new UserNotFoundException();
        } catch (ClassNotFoundException e) {
            System.err.println("getUserById threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getUserById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getUserById threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getUserById threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getUserById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("getUserById threw a UserNotFoundException: " + e.getMessage()); 
        }

        return null;
    }

    @Override
    public UserPOJO getUserByUsername(String username) {
        UserPOJO user;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                user = new UserPOJO(rs.getInt("user_id"), 
                        username, 
                        rs.getString("password"), 
                        rs.getTimestamp("created_at"), 
                        rs.getTimestamp("updated_at"));
            else
                throw new UserNotFoundException();
            return user;
        } catch (ClassNotFoundException e) {
            System.err.println("getUserByUsername threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getUserByUsername threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getUserByUsername threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getUserByUsername threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getUserByUsername threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("getUserByUsername threw a UserNotFoundException: " + e.getMessage()); 
        }

        return null;
    }

    @Override
    public boolean updateUserById(int id, String username, String password) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "UPDATE authors SET username = ?, password = ? WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotFoundException();
            } else {
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("getUserByUsername threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getUserByUsername threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getUserByUsername threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getUserByUsername threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getUserByUsername threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("getUserByUsername threw a UserNotFoundException: " + e.getMessage()); 
        }

        return false;
    }

}
