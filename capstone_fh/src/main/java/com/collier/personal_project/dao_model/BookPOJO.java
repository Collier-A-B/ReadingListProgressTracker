package com.collier.personal_project.dao_model;

import java.sql.Date;
import java.sql.Timestamp;

public class BookPOJO {
    private final int bookId;
    private int genreId;
    private int authorId;
    private String bookTitle;
    private Date publishDate;
    private String isbn_13;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public BookPOJO(int authorId, int bookId, String bookTitle, Timestamp createdAt, 
                                int genreId, String isbn_13, Date publishDate, Timestamp updatedAt) {
        this.authorId = authorId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.createdAt = createdAt;
        this.genreId = genreId;
        this.isbn_13 = isbn_13;
        this.publishDate = publishDate;
        this.updatedAt = updatedAt;
    }

    // Getter for bookId
    public int getBookId() {
        return this.bookId;
    }

    // Getter & Setter for genreId
    public int getGenreId() {
        return this.genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    // Getter & Setter for authorId
    public int getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    // Getter & Setter for bookTitle
    public String getBookTitle() {
        return this.bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    // Getter & Setter for publishDate
    public Date getPublishDate() {
        return this.publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    // Getter & Setter for isbn_13
    public String getIsbn_13() {
        return this.isbn_13;
    }
    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    // Getter for createdAt
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    // Getter for updatedAt
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String toString() {
        return "BookPOJO [bookId=" + bookId + ", genreId=" + genreId + ", authorId=" + authorId + ", bookTitle="
                + bookTitle + ", publishDate=" + publishDate + ", isbn_13=" + isbn_13 + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }

    
}
