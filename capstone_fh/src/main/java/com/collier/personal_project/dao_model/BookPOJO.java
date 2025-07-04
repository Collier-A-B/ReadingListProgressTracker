package com.collier.personal_project.dao_model;

import java.sql.Date;
import java.sql.Timestamp;

public class BookPOJO {
    private final int bookId;
    private String genre;
    private String AuthorName;
    private String bookTitle;
    private Date publishDate;
    private String isbn_13;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
    
    public BookPOJO(int bookId, String genre, String authorName, String bookTitle, Date publishDate, String isbn_13,
            Timestamp createdAt, Timestamp updatedAt) {
        this.bookId = bookId;
        this.genre = genre;
        AuthorName = authorName;
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.isbn_13 = isbn_13;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getBookId() {
        return bookId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format(
            """
            Book Listing: Title= %s, Author= %s, Genre= %s, ISBN-13= %s, Published= %s        
            """, this.bookTitle, this.AuthorName, this.genre, this.isbn_13, this.publishDate.toString()
        );
    }

    

}