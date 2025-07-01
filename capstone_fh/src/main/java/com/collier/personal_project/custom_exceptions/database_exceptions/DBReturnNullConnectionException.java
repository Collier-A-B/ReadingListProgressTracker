package com.collier.personal_project.custom_exceptions.database_exceptions;


/**
    This exception is thrown when a database connection that is requested is null.
    It indicates that an unforseen error has occured
*/
public class DBReturnNullConnectionException extends Exception {
    public DBReturnNullConnectionException() {
        super("Database connection to be returned was null.");
    }
}
