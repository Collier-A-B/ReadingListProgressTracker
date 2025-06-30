package com.collier.personal_project.custom_exceptions;

public class UserNotCreatedException extends Exception{
    public UserNotCreatedException(){
        super("User was not created.");
    }
}
