package com.collier.personal_project;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

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

        
        try (Scanner scan = new Scanner(System.in)){
            
            boolean exitProgram = false;
            
            while (!exitProgram) {
                if (!USER_LOGGED_IN) {
                    exitProgram = displayLoginOptions(scan);
                }

                if (USER_LOGGED_IN) {
                    // TODO: menu logic for when a user has successfully logged in
                    displayReadinglistOptions(scan);
                }
            }
        }
        
    }

    /**
     * Console UI function that handle user login/logout
     * 
     * @return
     */
    private static boolean displayLoginOptions(Scanner scan) {

        int userInput = -1;
        while (userInput == -1) {
            try {
                System.out.println(String.format("""
                        ******************************************************
                        \tLogin Page\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Login
                        \t2) Login as administrator
                        \t3) Exit Program
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt();
                if (bufferInput != 1 && bufferInput != 2)
                    throw new InputMismatchException();
                userInput = bufferInput;

            } catch (InputMismatchException e) {
                System.err.println("Your selection must be an integer value listed above");
            }
        }
        if (userInput == 3)
            return true;
        else {
            String username = null;
            String password = null;

            while (username == null || password == null) {
                try {
                    System.out.println(String.format("""
                            \n\n\tPlease enter your username and password.
                            \tYour input must be a valid string with a lenth
                            \tgreater than zero.
                            """).toCharArray());

                    System.out.print("\tUsername: ");
                    username = scan.next();

                    System.out.print("\tPassword: ");
                    password = scan.next();

                    if (username.length() == 0 || password.length() == 0)
                        throw new IOException("Username and password cannot be empty");

                } catch (IOException e) {
                    System.err.println("invalid input detected: " + e.getMessage());
                    username = null;
                    password = null;
                }
            }
            // TODO: Implement actual login logic with db (user and admin)
            System.out.println("username: " + username);
            System.out.println("password: " + password);
            USER_LOGGED_IN = true;

            return false;
        }
    }

    private static void displayReadinglistOptions(Scanner scan) {
        int userInput = -1;
        while (userInput == -1) {
            try {
                System.out.println(String.format("""
                        ******************************************************
                        \tReading List Options\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Display all books in your reading list
                        \t2) Display books not in your reading list
                        \t3) Display all books available in this application

                        \t*) Display all books in list by author
                        \t*) Display all books not in list by author

                        \t*) Display all books in list by genre

                        \t*) Logout
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt();
                if (bufferInput != 1 && bufferInput != 2)
                    throw new InputMismatchException();
                userInput = bufferInput;

            } catch (InputMismatchException e) {
                System.err.println("Your selection must be an integer value listed above");
            }
        }
    }

    /**
     * helper function that allows user to log in
     * 
     * @return true if login was successful, else false
     */
    private static boolean userLogIn(String username, String password) {
        try {
            // if a user is logged in
            if (USER_LOGGED_IN) {
                throw new LoginFailedException();
            }

            // TODO: LOGIN LOGIC

            return true;
        } catch (LoginFailedException e) {
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
        } catch (LogoutFailedException e) {
            System.err.println("user login attempt failed: " + e.getMessage());
        }
        return false;
    }
}
