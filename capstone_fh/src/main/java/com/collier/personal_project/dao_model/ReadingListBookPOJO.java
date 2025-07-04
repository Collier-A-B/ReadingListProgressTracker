package com.collier.personal_project.dao_model;

import java.sql.Date;

public class ReadingListBookPOJO extends BookPOJO {
    private final int userBookId;
    private final String status;
    private final Date startDate;
    private final Date endDate;

    public ReadingListBookPOJO(int userBookId, int bookId, String genre, String authorName, String bookTitle,
            Date publishDate, String isbn_13, String status, Date startDate, Date endDate) {
        super(bookId, genre, authorName, bookTitle, publishDate, isbn_13, null, null);
        this.userBookId = userBookId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    

    public int getUserBookId() {
        return userBookId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + 
        """
        status= %s, startDate= %s, endDate= %s
        """, this.status, this.startDate, this.endDate);
    }
    
}
