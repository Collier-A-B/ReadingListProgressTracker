package com.collier.personal_project.custom_exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User was not found.");
    }
}
