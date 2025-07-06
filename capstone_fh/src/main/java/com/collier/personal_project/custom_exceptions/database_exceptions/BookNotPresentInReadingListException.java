package com.collier.personal_project.custom_exceptions.database_exceptions;

public class BookNotPresentInReadingListException extends Exception {
    public BookNotPresentInReadingListException() {
        super("The book is not present in the user's reading list.");
    }
}
