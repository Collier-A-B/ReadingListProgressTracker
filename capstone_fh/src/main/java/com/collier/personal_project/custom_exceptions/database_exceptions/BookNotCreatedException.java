package com.collier.personal_project.custom_exceptions.database_exceptions;

public class BookNotCreatedException extends Exception{
    public BookNotCreatedException() {
        super("This book object could not be created in database");
    }
}
