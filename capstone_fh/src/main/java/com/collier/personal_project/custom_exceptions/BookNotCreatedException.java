package com.collier.personal_project.custom_exceptions;

public class BookNotCreatedException extends RuntimeException{
    public BookNotCreatedException() {
        super("This book object could not be created in database");
    }
}
