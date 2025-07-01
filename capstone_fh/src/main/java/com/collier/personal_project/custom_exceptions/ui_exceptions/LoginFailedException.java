package com.collier.personal_project.custom_exceptions.ui_exceptions;

public class LoginFailedException extends Exception{
    public LoginFailedException() {
        super("User login attempt failed.");
    }
}
