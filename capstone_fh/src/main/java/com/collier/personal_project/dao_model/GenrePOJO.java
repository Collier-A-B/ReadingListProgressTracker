package com.collier.personal_project.dao_model;

import java.sql.Timestamp;

public class GenrePOJO {
    private final int genreId;
    private String genreName;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public GenrePOJO(int genreId, String genreName, Timestamp createdAt, Timestamp updatedAt){
        this.genreId = genreId;
        this.genreName = genreName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter for genreId
    public int getGenreId() {
        return genreId;
    }

    // Getter & Setter for genreName
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
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
        return "GenrePOJO [genreId=" + genreId + ", genreName=" + genreName + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }

    
}
