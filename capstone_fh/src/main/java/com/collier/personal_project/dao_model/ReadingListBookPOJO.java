package com.collier.personal_project.dao_model;

import java.sql.Date;

public class ReadingListBookPOJO{
    private final int user_book_id;
    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private String bookIsbn13;
    private  Date startDate;
    private  Date endDate;
    
    public ReadingListBookPOJO(int user_book_id, String bookTitle, String bookAuthor, String bookGenre, String bookIsbn13, Date startDate,
            Date endDate) {
        this.user_book_id = user_book_id;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.bookIsbn13 = bookIsbn13;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getUser_book_id() {
        return user_book_id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public String getBookIsbn13() {
        return bookIsbn13;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setBookIsbn13(String bookIsbn13) {
        this.bookIsbn13 = bookIsbn13;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReadingListBookPOJO{");
        sb.append("user_book_id=").append(user_book_id);
        sb.append(", bookTitle=").append(bookTitle);
        sb.append(", bookAuthor=").append(bookAuthor);
        sb.append(", bookGenre=").append(bookGenre);
        sb.append(", bookIsbn13=").append(bookIsbn13);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append('}');
        return sb.toString();
    }

    
    

    
}
