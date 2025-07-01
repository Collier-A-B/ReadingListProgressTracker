package com.collier.personal_project;

import com.collier.personal_project.custom_exceptions.ui_exceptions.LoginFailedException;
import com.collier.personal_project.custom_exceptions.ui_exceptions.LogoutFailedException;

/**
 * Hello world!
 */
public class App {

    private static boolean USER_LOGGED_IN = false;

    public static void main(String[] args) {

        System.out.println(String.format("""
                ******************************************************
                \tWelcome to the reading list tracker!!!\n
                \tMade by Adam Collier
                ******************************************************
                """).toCharArray());

        boolean exitProgram = false;

        while (!exitProgram) {
            if (!USER_LOGGED_IN) {

            }
        }
    }

    /**
     * helper function that allows user to log in
     * 
     * @return
     */
    private static boolean userLogIn(String username, String password) {
        try {
            // if a user is logged in
            if (USER_LOGGED_IN) {
                throw new LoginFailedException();
            } 

            // TODO: LOGIN LOGIC

            return true;
        } catch (LoginFailedException e){
            System.err.println("user login attempt failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * helper function that allows user to log out
     * 
     * @return
     */
    private static boolean userLogOut() {
        try {
            // if a user is logged in
            if (!USER_LOGGED_IN) {
                throw new LogoutFailedException();
            } 

            // TODO: LOGOUT LOGIC

            return true;
        } catch (LogoutFailedException e){
            System.err.println("user login attempt failed: " + e.getMessage());
        }
        return false;
    }
}
