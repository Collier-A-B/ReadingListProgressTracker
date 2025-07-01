package com.collier.personal_project.custom_exceptions.ui_exceptions;

public class LogoutFailedException extends Exception{
    public LogoutFailedException() {
        super("unable to logout user");
    }
}
