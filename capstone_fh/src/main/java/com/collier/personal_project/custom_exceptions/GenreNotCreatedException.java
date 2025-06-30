package com.collier.personal_project.custom_exceptions;

public class GenreNotCreatedException extends Exception{
    public GenreNotCreatedException(){
        super("Genre could not be created.");
    }
}
