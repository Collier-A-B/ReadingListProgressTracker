package com.collier.personal_project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.collier.personal_project.custom_exceptions.ui_exceptions.LoginFailedException;
import com.collier.personal_project.custom_exceptions.ui_exceptions.LogoutFailedException;
import com.collier.personal_project.dao.book.BooksDAOClass;
import com.collier.personal_project.dao.user.UsersDAOClass;
import com.collier.personal_project.dao.user_book.UserBookDAOClass;
import com.collier.personal_project.dao_model.BookPOJO;
import com.collier.personal_project.dao_model.ReadingListBookPOJO;
import com.collier.personal_project.dao_model.UserPOJO;
import com.collier.personal_project.enumerators.ui.BookAlphanumericSortEnum;
import com.collier.personal_project.enumerators.ui.BookSortEnum;
import com.collier.personal_project.enumerators.ui.LoginEnum;
import com.collier.personal_project.enumerators.ui.ReadingListUserEnum;

/**
 * Hello world!
 */
public class App {

    private static boolean USER_LOGGED_IN = false;
    private static UserPOJO USER;

    public static void main(String[] args) {

        System.out.println(String.format("""
                ******************************************************
                \tWelcome to the reading list tracker!!!\n
                \tMade by Adam Collier
                ******************************************************
                """).toCharArray());

        final UsersDAOClass usersDAO = new UsersDAOClass();
        final BooksDAOClass booksDAO = new BooksDAOClass();
        final UserBookDAOClass userBookDAO = new UserBookDAOClass();

        try (Scanner scan = new Scanner(System.in)) {

            boolean exitProgram = false;

            while (!exitProgram) {
                if (!USER_LOGGED_IN) {
                    exitProgram = displayLoginOptions(scan, usersDAO);
                }

                if (USER_LOGGED_IN) {
                    // TODO: menu logic for when a user has successfully logged in
                    displayReadinglistOptions(scan, booksDAO, userBookDAO);
                }
            }
        }

    }

    /**
     * Console UI function that handle user login and program termination
     * 
     * @param scan
     * @param userDAO
     * @return true only if user chooses to terminate the program
     */
    private static boolean displayLoginOptions(Scanner scan, UsersDAOClass userDAO) {

        LoginEnum userInput = LoginEnum.NO_SELECTION_MADE;
        while (userInput == LoginEnum.NO_SELECTION_MADE) {
            try {
                // each option coresponds to a value in LoginEnum
                // offset by +1
                System.out.println(String.format("""
                        ******************************************************
                        \tLogin Page\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Login as user
                        \t2) Login as administrator
                        \t3) Exit Program
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 2)
                    throw new InputMismatchException();
                userInput = LoginEnum.values()[bufferInput];

            } catch (InputMismatchException e) {
                System.err.println("Your selection must be an integer value listed above");
            }
        }
        switch (userInput) {
            case EXIT_PROGRAM:
                return true;
            case LOGIN_USER:
            case LOGIN_ADMIN:
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

                boolean loginSuccess;
                if (userInput == LoginEnum.LOGIN_ADMIN)
                    loginSuccess = userLogIn(userDAO, username, password, true);
                else
                    loginSuccess = userLogIn(userDAO, username, password, false);
                
                if (loginSuccess) {
                    System.out.println("Login successful!");
                } 
                break;
            default:
                System.err.println("Invalid input detected");
        }
        System.out.println("******************************************************\n\n");
        return false;
    }

    /**
     * Display method that handles user reading list interaction options
     * 
     * @param scan
     * @param userDAO
     */
    private static void displayReadinglistOptions(Scanner scan, BooksDAOClass bookDAO, UserBookDAOClass userBookDAO) {
        ReadingListUserEnum userInput = ReadingListUserEnum.NO_OPTION_SELECTED;
        while (userInput == ReadingListUserEnum.NO_OPTION_SELECTED) {
            try {
                // each option coresponds to ReadingListUserEnum values, offset by +1
                System.out.println(String.format("""
                        ******************************************************
                        \tReading List Options\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Display books in your reading list
                        \t2) Display books not in your reading list
                        \t3) Display all books available in this app

                        \t4) Add book to your list
                        \t5) Remove book from your list
                        \t6) Update status of book in list

                        \t7) Logout
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 6)
                    throw new InputMismatchException();
                userInput = ReadingListUserEnum.values()[bufferInput];

            } catch (InputMismatchException e) {
                System.err.println("Your selection must be an integer value listed above");
            }

            // TODO: Implement option handling logic
            switch (userInput) {
                case DISPLAY_BOOKS_IN_LIST:
                    displayReadingListBooks(scan, userBookDAO);
                    break;
                case DISPLAY_BOOKS_NOT_IN_LIST:
                case DISPLAY_ALL_BOOKS_IN_APP:
                    displayBooks(scan, bookDAO, userInput);
                    break;
                case ADD_BOOK_TO_LIST:
                    break;
                case REMOVE_BOOK_FROM_LIST:
                    break;
                case UPDATE_STATUS_OF_BOOK_IN_LIST:
                    break;
                case LOGOUT:
                    boolean logoutSuccess = userLogOut();
                    if (logoutSuccess) {
                        System.out.println("\n\tLogout successful!");
                    }
                    break;
                default:
                    System.err.println("\n\tInvalid selection");
                    break;
            }
            System.out.println("******************************************************\n\n");
        }
    }

    private static void displayReadingListBooks(Scanner scan, UserBookDAOClass userBookDAO) {
        BookSortEnum bookSort = BookSortEnum.NO_OPTION_SELECTED;
        BookAlphanumericSortEnum alphaNumSort = BookAlphanumericSortEnum.NO_OPTION_SELECTED;

        while (bookSort == BookSortEnum.NO_OPTION_SELECTED) {
            try {
                // each option coresponds to ReadingListUserEnum values, offset by +1
                System.out.println(String.format("""
                        \tBook filtering options\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Sort by author 
                        \t2) Sort by genre
                        \t3) None
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 2)
                    throw new InputMismatchException();
                bookSort = BookSortEnum.values()[bufferInput];
            } catch (InputMismatchException e) {
                System.err.println("Invalid input");
            }
        }
        while (alphaNumSort == BookAlphanumericSortEnum.NO_OPTION_SELECTED){
            try {
                System.out.println(String.format("""
                        \n\n\tEnable alphanumeric sorting\n
                        \tPlease select from one of the following options:\n
                        \t1) Yes
                        \t2) No
                        """).toCharArray());
                        System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 2)
                    throw new InputMismatchException();
                alphaNumSort = BookAlphanumericSortEnum.values()[bufferInput];
            } catch (InputMismatchException e) {
                System.err.println("Invalid input");
            }
        }

        List<ReadingListBookPOJO> returnList = userBookDAO.getAllBooksInUserList(USER.getUserId());  
        for (ReadingListBookPOJO book : returnList)
        {
            System.out.println(book);
        }
    }

    /**
     * 
     * @param scan
     * @param userDAO
     */
    private static void displayBooks(Scanner scan, BooksDAOClass bookDAO, ReadingListUserEnum displayChoice) {

        BookSortEnum bookSort = BookSortEnum.NO_OPTION_SELECTED;
        BookAlphanumericSortEnum alphaNumSort = BookAlphanumericSortEnum.NO_OPTION_SELECTED;

        while (bookSort == BookSortEnum.NO_OPTION_SELECTED) {
            try {
                // each option coresponds to ReadingListUserEnum values, offset by +1
                System.out.println(String.format("""
                        \tBook filtering options\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Sort by author 
                        \t2) Sort by genre
                        \t3) None
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 2)
                    throw new InputMismatchException();
                bookSort = BookSortEnum.values()[bufferInput];
            } catch (InputMismatchException e) {
                System.err.println("Invalid input");
            }
        }
        while (alphaNumSort == BookAlphanumericSortEnum.NO_OPTION_SELECTED){
            try {
                System.out.println(String.format("""
                        \n\n\tEnable alphanumeric sorting\n
                        \tPlease select from one of the following options:\n
                        \t1) Yes
                        \t2) No
                        """).toCharArray());
                        System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 2)
                    throw new InputMismatchException();
                alphaNumSort = BookAlphanumericSortEnum.values()[bufferInput];
            } catch (InputMismatchException e) {
                System.err.println("Invalid input");
            }
        }
        
        List<BookPOJO> returnList = new ArrayList<>();  //

        switch (displayChoice) {
            case DISPLAY_BOOKS_NOT_IN_LIST:
                // TODO: Call DAO method
                break;
            case DISPLAY_ALL_BOOKS_IN_APP:
                // TODO: Call DAO method
                returnList = bookDAO.getAllBooks();
                break;
            default:
                System.err.println("invalid option selected");
        }

        for (BookPOJO book : returnList)
        {
            System.out.println(book);
        }
    }

    /**
     * display function that handles administrator options
     */
    private static void displayAdminOptions(Scanner scan, UsersDAOClass userDAO) {

    }

    /**
     * helper function that allows user to log in
     * 
     * @return true if login was successful, else false
     */
    private static boolean userLogIn(UsersDAOClass userDAO, String username, String password, boolean adminCheck) {
        try {
            // if a user is logged in
            if (USER_LOGGED_IN) {
                throw new LoginFailedException();
            }

            // TODO: LOGIN LOGIC
            UserPOJO dbReturn = userDAO.getUserByUsername(username);
            if (dbReturn == null || !dbReturn.getPassword().equals(password))
                throw new LoginFailedException();
            if (dbReturn.isAdmin() == false && adminCheck)
                throw new LoginFailedException();
            if (!adminCheck)
                // user is not logging in as admin, block access to that functionality
                dbReturn.revokeAdmin();
            USER = dbReturn;
            USER_LOGGED_IN = true;
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
    /* TODO */
    private static boolean userLogOut() {
        try {
            // if no user has logged in
            if (!USER_LOGGED_IN) {
                throw new LogoutFailedException();
            }

            // TODO: LOGOUT LOGIC
            USER = null;
            USER_LOGGED_IN = false;
            return true;
        } catch (LogoutFailedException e) {
            System.err.println("user logout attempt failed: " + e.getMessage());
        }
        return false;
    }
}
