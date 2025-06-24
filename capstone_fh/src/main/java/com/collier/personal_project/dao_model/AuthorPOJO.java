package com.collier.personal_project.dao_model;

import java.sql.Timestamp;

/**
    POJO for ReadingList Author
    This class stores the author information for an book author
 */
public class AuthorPOJO {
    // private fields
    private final int authorId;
    private String authorName;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public AuthorPOJO(int authorId, String authorName, Timestamp createdAt, Timestamp updatedAt) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter for authorId
    public int getAuthorId() {
        return authorId;
    }

    // Getter & Setter for authorName
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // Getter for createdAt
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Getter for updatedAt
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "AuthorPOJO [authorId=" + authorId + ", authorName=" + authorName + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }

    
}
