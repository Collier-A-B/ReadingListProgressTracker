package com.collier.personal_project.custom_exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException() {
        super("No author matching this description was found.");
    }
}
