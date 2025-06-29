package com.collier.personal_project.custom_exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("No book matching this description was found.");
    }
    
}
