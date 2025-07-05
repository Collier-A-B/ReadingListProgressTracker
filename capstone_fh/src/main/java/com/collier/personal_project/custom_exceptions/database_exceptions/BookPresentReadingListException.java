package com.collier.personal_project.custom_exceptions.database_exceptions;

public class BookPresentReadingListException extends Exception {
    public BookPresentReadingListException() {
        super("Book is already present in the user's reading list.");
    }
}


