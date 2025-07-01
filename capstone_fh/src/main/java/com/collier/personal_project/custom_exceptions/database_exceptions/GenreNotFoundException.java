package com.collier.personal_project.custom_exceptions.database_exceptions;

public class GenreNotFoundException extends Exception{

    public GenreNotFoundException() {
        super("No genre matching this description was found");
    }
    
}
