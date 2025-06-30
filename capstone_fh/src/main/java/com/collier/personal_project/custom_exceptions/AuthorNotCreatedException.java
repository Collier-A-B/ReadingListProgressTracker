package com.collier.personal_project.custom_exceptions;

public class AuthorNotCreatedException extends Exception {

    public AuthorNotCreatedException() {
        super("Unable to add author to the table.");
    }
    
}
