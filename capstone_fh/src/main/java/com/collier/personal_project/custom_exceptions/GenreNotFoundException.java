package com.collier.personal_project.custom_exceptions;

public class GenreNotFoundException extends RuntimeException{

    public GenreNotFoundException() {
        super("No genre matching this description was found");
    }
    
}
