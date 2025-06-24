package com.collier.personal_project.dao_model;

import java.sql.Timestamp;

/**
 * POJO for ReadingList User.
 * This class stores the user information for registered users of the reading list.
 */
public class UserPOJO {
    // private fields of a user
    private final int userId;
    private String username;
    private String password;
    private boolean isAdmin;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    // Constructor
    public UserPOJO(int userId, String username, String password, 
                                 Timestamp createdAt, Timestamp updatedAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt; 
    }

    // Getter for userId
    public int getUserId() {
        return this.userId;
    }

    // Getter & Setter for username
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for Password
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for admin flag
    public boolean isAdmin() {
        return this.isAdmin;
    }

    // Setters for adminFlag
    public void grantAdmin() {
        this.isAdmin = true;
    }

    public void revokeAdmin() {
        this.isAdmin = false;
    }

    // Getter for createdAt timestamp
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    // Getter for updatedAt timestamp
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String toString() {
        return "UserPOJO [userId=" + userId + ", username=" + username + ", password=" + password + ", isAdmin="
                + isAdmin + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    
}
