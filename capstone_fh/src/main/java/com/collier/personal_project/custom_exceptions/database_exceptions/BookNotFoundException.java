package com.collier.personal_project.custom_exceptions.database_exceptions;

public class BookNotFoundException extends Exception{

    public BookNotFoundException() {
        super("No book matching this description was found.");
    }
    
}
