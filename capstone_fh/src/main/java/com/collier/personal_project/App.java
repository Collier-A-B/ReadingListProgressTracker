package com.collier.personal_project;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.collier.personal_project.custom_exceptions.ui_exceptions.LoginFailedException;
import com.collier.personal_project.custom_exceptions.ui_exceptions.LogoutFailedException;
import com.collier.personal_project.dao.author.AuthorsDAOClass;
import com.collier.personal_project.dao.book.BooksDAOClass;
import com.collier.personal_project.dao.genre.GenreDAOClass;
import com.collier.personal_project.dao.user.UsersDAOClass;
import com.collier.personal_project.dao.user_book.UserBookDAOClass;
import com.collier.personal_project.dao_model.AuthorPOJO;
import com.collier.personal_project.dao_model.BookPOJO;
import com.collier.personal_project.dao_model.GenrePOJO;
import com.collier.personal_project.dao_model.ReadingListBookPOJO;
import com.collier.personal_project.dao_model.UserPOJO;
import com.collier.personal_project.enumerators.ui.LoginEnum;
import com.collier.personal_project.enumerators.ui.ReadingListAdminEnum;
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
        final GenreDAOClass genreDao = new GenreDAOClass();
        final AuthorsDAOClass authorDAO = new AuthorsDAOClass();

        try (Scanner scan = new Scanner(System.in)) {

            boolean exitProgram = false;

            while (!exitProgram) {
                if (!USER_LOGGED_IN) {
                    exitProgram = displayLoginOptions(scan, usersDAO);
                }

                if (USER_LOGGED_IN) {
                    if (USER.isAdmin()) {
                        // if user is admin, display admin options
                        System.out.println("\n\tWelcome " + USER.getUsername() + "!");
                        System.out.println("\tYou are logged in as an administrator.");
                        System.out.println("\tNOTE: you cannot access user optionswhile logged in as an admin.");
                        displayAdminOptions(scan, genreDao, authorDAO, booksDAO);
                    } else {
                        // if user is not admin, display reading list options
                        System.out.println("\tWelcome " + USER.getUsername() + "!");
                        System.out.println("\tYou are logged in as a regular user.");
                        System.out.println("\tNOTE: if you are an admin, you cannot access administrator options\nwhile logged in as a regular user.\n\n");
                        displayReadinglistOptions(scan, booksDAO, userBookDAO);
                    }
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
                        \t3) Create a user account
                        \t4) Exit Program
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 3)
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
            case CREATE_USER:
                String newUsername = null;
                String newPassword = null;

                while (newUsername == null || newPassword == null) {
                    try {
                        System.out.println(String.format("""
                                \n\n\tPlease enter your desired username and password.
                                \tYour input must be a valid string with a length
                                \tgreater than zero.
                                """).toCharArray());

                        System.out.print("\tUsername: ");
                        newUsername = scan.next();

                        System.out.print("\tPassword: ");
                        newPassword = scan.next();

                        if (newUsername.length() == 0 || newPassword.length() == 0)
                            throw new IOException("Username and password cannot be empty");

                    } catch (IOException e) {
                        System.err.println("invalid input detected: " + e.getMessage());
                        newUsername = null;
                        newPassword = null;
                    }
                }

                boolean createSuccess = userDAO.addUser(newUsername, newPassword);
                if (createSuccess) {
                    System.out.println("User created successfully!");
                } else {
                    System.err.println("Failed to create user. ");
                }
                break;
            default:
                System.err.println("Invalid input detected");
        }
        System.out.println("******************************************************\n\n");
        return false;
    }

    /**
     * Display method that handles administrator options
     * @param scan
     * @param userDAO
     */
    private static void displayAdminOptions(Scanner scan, GenreDAOClass genreDAO, 
                                            AuthorsDAOClass authorDAO, BooksDAOClass bookDAO) {
        ReadingListAdminEnum userInput = ReadingListAdminEnum.NO_OPTION_SELECTED;
        while (userInput == ReadingListAdminEnum.NO_OPTION_SELECTED) {
            try {
                // each option coresponds to ReadingListAdminEnum values, offset by +1
                System.out.println(String.format("""
                        \n******************************************************
                        \tAdministrator Options\n\n
                        \tPlease select from one of the following options:\n
                        \t1) Add a genre
                        \t2) Add an author
                        \t3) Add a book

                        \t4) Update a genre
                        \t5) Update an author
                        \t6) Update a book

                        \t7) Delete a genre
                        \t8) Delete an author
                        \t9) Delete a book

                        \t10) Logout
                        """).toCharArray());
                System.out.print("\tYour Input: ");
                int bufferInput = scan.nextInt() - 1;
                if (bufferInput < 0 || bufferInput > 9)
                    throw new InputMismatchException();
                userInput = ReadingListAdminEnum.values()[bufferInput];

            } catch (InputMismatchException e) {
                System.err.println("Your selection must be an integer value listed above");
            }
        }
        switch (userInput) {
            case UPDATE_GENRE:
                updateGenre(scan, genreDAO);
                break;
            case ADD_GENRE:
                addGenre(scan, genreDAO);
                break;
            case DELETE_GENRE:
                deleteGenre(scan, genreDAO);
                break;
            case UPDATE_AUTHOR:
                updateAuthor(scan, authorDAO);
                break;
            case ADD_AUTHOR:
                addAuthor(scan, authorDAO);
                break;
            case DELETE_AUTHOR:
                deleteAuthor(scan, authorDAO);
                break;
            case ADD_BOOK:  
                addBook(scan, bookDAO);
                break;
            case UPDATE_BOOK:
                updateBook(scan, bookDAO);
                break;
            case DELETE_BOOK:
                deleteBook(scan, bookDAO);
                break;
            case LOGOUT:
                boolean logoutSuccess = userLogOut();
                if (logoutSuccess) {
                    System.out.println("\n\tLogout successful!");
                } else {
                    System.err.println("\n\tLogout failed. Please try again.");
                }
                break;
            default:
                throw new AssertionError();
        }
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
                    addBookToReadingList(scan, userBookDAO, bookDAO);
                    break;
                case REMOVE_BOOK_FROM_LIST:
                    removeBookFromReadingList(scan, userBookDAO);
                    break;
                case UPDATE_STATUS_OF_BOOK_IN_LIST:
                    updateBookStatusInReadingList(scan, userBookDAO);
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

    /**
     * Display method that handles displaying books in the user's reading list
     * 
     * @param scan
     * @param userBookDAO
     */
    private static void displayReadingListBooks(Scanner scan, UserBookDAOClass userBookDAO) {
        // BookSortEnum bookSort = BookSortEnum.NO_OPTION_SELECTED;
        // BookAlphanumericSortEnum alphaNumSort =
        // BookAlphanumericSortEnum.NO_OPTION_SELECTED;

        /*
         * aditional sorting options can be added here
         * 
         * while (bookSort == BookSortEnum.NO_OPTION_SELECTED) {
         * try {
         * // each option coresponds to ReadingListUserEnum values, offset by +1
         * System.out.println(String.format("""
         * \tBook filtering options\n\n
         * \tPlease select from one of the following options:\n
         * \t1) Sort by author
         * \t2) Sort by genre
         * \t3) None
         * """).toCharArray());
         * System.out.print("\tYour Input: ");
         * int bufferInput = scan.nextInt() - 1;
         * if (bufferInput < 0 || bufferInput > 2)
         * throw new InputMismatchException();
         * bookSort = BookSortEnum.values()[bufferInput];
         * } catch (InputMismatchException e) {
         * System.err.println("Invalid input");
         * }
         * }
         * while (alphaNumSort == BookAlphanumericSortEnum.NO_OPTION_SELECTED){
         * try {
         * System.out.println(String.format("""
         * \n\n\tEnable alphanumeric sorting\n
         * \tPlease select from one of the following options:\n
         * \t1) Yes
         * \t2) No
         * """).toCharArray());
         * System.out.print("\tYour Input: ");
         * int bufferInput = scan.nextInt() - 1;
         * if (bufferInput < 0 || bufferInput > 2)
         * throw new InputMismatchException();
         * alphaNumSort = BookAlphanumericSortEnum.values()[bufferInput];
         * } catch (InputMismatchException e) {
         * System.err.println("Invalid input");
         * }
         * }
         */

        List<ReadingListBookPOJO> returnList = userBookDAO.getAllBooksInUserList(USER.getUserId());
        for (ReadingListBookPOJO book : returnList) {
            System.out.println(book);
        }
    }

    /**
     * Display method that handles book display options
     * 
     * @param scan
     * @param userDAO
     */
    private static void displayBooks(Scanner scan, BooksDAOClass bookDAO, ReadingListUserEnum displayChoice) {

        // BookSortEnum bookSort = BookSortEnum.NO_OPTION_SELECTED;
        // BookAlphanumericSortEnum alphaNumSort =
        // BookAlphanumericSortEnum.NO_OPTION_SELECTED;

        /*
         * Aditional sorting options can be added here
         * 
         * while (bookSort == BookSortEnum.NO_OPTION_SELECTED) {
         * try {
         * // each option coresponds to ReadingListUserEnum values, offset by +1
         * System.out.println(String.format("""
         * \tBook filtering options\n\n
         * \tPlease select from one of the following options:\n
         * \t1) Sort by author
         * \t2) Sort by genre
         * \t3) None
         * """).toCharArray());
         * System.out.print("\tYour Input: ");
         * int bufferInput = scan.nextInt() - 1;
         * if (bufferInput < 0 || bufferInput > 2)
         * throw new InputMismatchException();
         * bookSort = BookSortEnum.values()[bufferInput];
         * } catch (InputMismatchException e) {
         * System.err.println("Invalid input");
         * }
         * }
         * while (alphaNumSort == BookAlphanumericSortEnum.NO_OPTION_SELECTED){
         * try {
         * System.out.println(String.format("""
         * \n\n\tEnable alphanumeric sorting\n
         * \tPlease select from one of the following options:\n
         * \t1) Yes
         * \t2) No
         * """).toCharArray());
         * System.out.print("\tYour Input: ");
         * int bufferInput = scan.nextInt() - 1;
         * if (bufferInput < 0 || bufferInput > 2)
         * throw new InputMismatchException();
         * alphaNumSort = BookAlphanumericSortEnum.values()[bufferInput];
         * } catch (InputMismatchException e) {
         * System.err.println("Invalid input");
         * }
         * }
         */

        List<BookPOJO> returnList = new ArrayList<>(); //

        switch (displayChoice) {
            case DISPLAY_BOOKS_NOT_IN_LIST:
                returnList = bookDAO.getBooksNotInReadingList(USER.getUserId());
                break;
            case DISPLAY_ALL_BOOKS_IN_APP:
                returnList = bookDAO.getAllBooks();
                break;
            default:
                System.err.println("invalid option selected");
        }

        for (BookPOJO book : returnList) {
            System.out.println(book);
        }
    }

    /**
     * Method that allows user to add a book to their reading list
     * 
     * @param scan Scanner used for user input
     * @param userBookDAO UserBookDAOClass instance used to interact with user_book data
     * @param bookDAO BooksDAOClass instance used to interact with book data
     */
    private static void addBookToReadingList(Scanner scan, UserBookDAOClass userBookDAO, BooksDAOClass bookDAO) {

        List<BookPOJO> bookList = bookDAO.getBooksNotInReadingList(USER.getUserId());
        if (bookList.isEmpty()) {
            System.out.println("\tThere are no books available to add to your reading list.");
        } else {
            System.out.println("\tPlease select a book to add to your reading list:");
            for (int i = 0; i < bookList.size(); i++) {
                System.out.println("\t" + (i + 1) + ") " + bookList.get(i));
            }
            int bookChoice = -1;
            while (bookChoice < 0 || bookChoice > bookList.size()) {
                try {
                    System.out.print("\tEnter the number of the book you want to add: ");
                    bookChoice = scan.nextInt() - 1; // Adjust for zero-based index
                    if (bookChoice < 0 || bookChoice >= bookList.size()) {
                        throw new InputMismatchException("Invalid book selection. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(e.getMessage());
                    bookChoice = -1;
                }
            }
            boolean addSuccess = userBookDAO.addBookToUserListByISBN(USER.getUserId(),
                    bookList.get(bookChoice).getIsbn_13());
            if (addSuccess) {
                System.out.println("Book added to your reading list successfully!");
            } else {
                System.err.println("Failed to add book to your reading list.");
            }
        }

    }

    private static void updateBookStatusInReadingList(Scanner scan, UserBookDAOClass userBookDAO) {
        List<ReadingListBookPOJO> readingList = userBookDAO.getAllBooksInUserList(USER.getUserId());
        if (readingList.isEmpty()) {
            System.out.println("\tYour reading list is empty. Please add books before updating their status.");
        } else {
            System.out.println("\tPlease select a book to update in your reading list:");
            for (int i = 0; i < readingList.size(); i++) {
                System.out.println("\t" + (i + 1) + ") " + readingList.get(i));
            }
            int bookChoice = -1;
            while (bookChoice < 0 || bookChoice > readingList.size()) {
                try {
                    System.out.print("Enter the number of the book you want to update: ");
                    bookChoice = scan.nextInt() - 1; // Adjust for zero-based index
                    if (bookChoice < 0 || bookChoice >= readingList.size()) {
                        throw new InputMismatchException("Invalid book selection. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(e.getMessage());
                    bookChoice = -1;
                }
            }
            System.out.println("""
                    \n\n\tPlease select the new status for the book:\n
                    \t1) Not Started
                    \t2) In Progress
                    \t3) Completed
                    """);
            int statusChoice = -1;
            String newStatus = "";
            while (statusChoice < 0 || statusChoice > 2) {
                try {
                    System.out.print("Enter the number of the new status: ");
                    statusChoice = scan.nextInt() - 1; // Adjust for zero-based index

                    switch (statusChoice) {
                        case 0:
                            newStatus = "NOT_READ";
                            break;
                        case 1:
                            newStatus = "IN_PROGRESS";
                            break;
                        case 2:
                            newStatus = "COMPLETED";
                            break;
                        default:
                            throw new InputMismatchException("Invalid status selection. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(e.getMessage());
                    statusChoice = -1;
                }
            }
            Date startDate = null;
            Date endDate = null;
            if ("IN_PROGRESS".equals(newStatus) || "COMPLETED".equals(newStatus)) {
                while (startDate == null) {
                    try {
                        System.out.println("""
                                \n\n\tPlease enter the date you started reading this book
                                \n\tFormat: YYYY-MM-DD
                                \n\tExample: 2023-10-01
                                """);
                        System.out.print("\tStart Date: ");
                        String startDateInput = scan.next();
                        startDate = java.sql.Date.valueOf(startDateInput);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid date format. Please try again.");
                    }
                }
            }

            if ("COMPLETED".equals(newStatus)) {
                while (endDate == null) {
                    try {
                        System.out.println("""
                                \n\n\tPlease enter the date you finished reading this book
                                \n\tFormat: YYYY-MM-DD
                                \n\tExample: 2023-10-01
                                """);
                        System.out.print("\tend Date: ");
                        String startDateInput = scan.next();
                        endDate = java.sql.Date.valueOf(startDateInput);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid date format. Please try again.");
                    }
                }
            }

            boolean updateSuccess = userBookDAO
                    .updateBookStatusByISBN(USER.getUserId(),
                            readingList.get(bookChoice).getIsbn_13(),
                            newStatus, startDate, endDate);
            if (updateSuccess) {
                System.out.println("\tBook status updated successfully!");
            } else {
                System.err.println("\tFailed to update book status in your reading list.");
            }
        }
    }

    private static void removeBookFromReadingList(Scanner scan, UserBookDAOClass userBookDAO) {
        List<ReadingListBookPOJO> readingList = userBookDAO.getAllBooksInUserList(USER.getUserId());
        if (readingList.isEmpty()) {
            System.out.println("\tYour reading list is empty. Please add books before attempting to remove any.");
        } else {
            System.out.println("\tPlease select a book to remove from your reading list:\n");
            for (int i = 0; i < readingList.size(); i++) {
                System.out.println("\t" + (i + 1) + ") " + readingList.get(i));
            }
            int bookChoice = -1;
            while (bookChoice < 0 || bookChoice > readingList.size()) {
                try {
                    System.out.print("\n\n\tEnter the number of the book you want to remove: ");
                    bookChoice = scan.nextInt() - 1; // Adjust for zero-based index
                    if (bookChoice < 0 || bookChoice >= readingList.size()) {
                        throw new InputMismatchException("Invalid book selection. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.err.println(e.getMessage());
                    bookChoice = -1;
                }
            }
            boolean addSuccess = userBookDAO.removeBookFromUserListByISBN(USER.getUserId(),
                    readingList.get(bookChoice).getIsbn_13());
            if (addSuccess) {
                System.out.println("\tBook removed from your reading list successfully!");
            } else {
                System.err.println("\tFailed to remove book from your reading list.");
            }
        }
    }

    private static void addGenre(Scanner scan, GenreDAOClass genreDAO) {
        String genreName = null;
        while (genreName == null) {
            try {
                System.out.println("\n\n\tPlease enter the name of the genre you want to add:");
                System.out.print("\tGenre Name: ");
                genreName = scan.next();
                if (genreName.length() == 0) {
                    throw new IOException("Genre name cannot be empty");
                }
            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
                genreName = null;
            }
        }

        boolean addSuccess = genreDAO.addGenre(genreName);
        if (addSuccess) {
            System.out.println("Genre added successfully!");
        } else {
            System.err.println("Failed to add genre.");
        }
    }

    private static void updateGenre(Scanner scan, GenreDAOClass genreDAO) {
        List<GenrePOJO> genreList = genreDAO.getAllGenres();
        if (genreList.isEmpty()) {
            System.out.println("\tThere are no genres available to update.");
            return;
        }

        System.out.println("\tPlease select a genre to update:");
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + genreList.get(i));
        }
        int genreChoice = -1;
        while (genreChoice < 0 || genreChoice > genreList.size()) {
            try {
                System.out.print("\tEnter the number of the genre you want to update: ");
                genreChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (genreChoice < 0 || genreChoice >= genreList.size()) {
                    throw new InputMismatchException("Invalid genre selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                genreChoice = -1;
            }
        }

        String newGenreName = null;
        while (newGenreName == null) {
            try {
                System.out.print("\n\tEnter the new name for the selected genre: ");
                newGenreName = scan.next();
                if (newGenreName.length() == 0) {
                    throw new IOException("Genre name cannot be empty");
                }
            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
                newGenreName = null;
            }
        }

        boolean updateSuccess = genreDAO.updateGenreById(genreList.get(genreChoice).getGenreId(), newGenreName);
        if (updateSuccess) {
            System.out.println("Genre updated successfully!");
        } else {
            System.err.println("Failed to update genre.");
        }
    }

    private static void deleteGenre(Scanner scan, GenreDAOClass genreDAO) {
        List<GenrePOJO> genreList = genreDAO.getAllGenres();
        if (genreList.isEmpty()) {
            System.out.println("\tThere are no genres available to delete.");
            return;
        }

        System.out.println("\tPlease select a genre to delete:");
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + genreList.get(i));
        }
        int genreChoice = -1;
        while (genreChoice < 0 || genreChoice > genreList.size()) {
            try {
                System.out.print("\tEnter the number of the genre you want to delete: ");
                genreChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (genreChoice < 0 || genreChoice >= genreList.size()) {
                    throw new InputMismatchException("Invalid genre selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                genreChoice = -1;
            }
        }

        boolean deleteSuccess = genreDAO.deleteGenreById(genreList.get(genreChoice).getGenreId());
        if (deleteSuccess) {
            System.out.println("Genre deleted successfully!");
        } else {
            System.err.println("Failed to delete genre.");
        }
    }

    private static void addAuthor(Scanner scan, AuthorsDAOClass authorDAO) {
        String authorName = null;
        while (authorName == null) {
            try {
                System.out.println("\n\n\tPlease enter the name of the author you want to add:");
                System.out.print("\tAuthor Name: ");
                authorName = scan.next();
                if (authorName.length() == 0) {
                    throw new IOException("Author name cannot be empty");
                }
            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
                authorName = null;
            }
        }

        boolean addSuccess = authorDAO.addAuthor(authorName);
        if (addSuccess) {
            System.out.println("Author added successfully!");
        } else {
            System.err.println("Failed to add author.");
        }
    }

    private static void updateAuthor(Scanner scan, AuthorsDAOClass authorDAO) {
        List<AuthorPOJO> authorList = authorDAO.getAllAuthors();
        if (authorList.isEmpty()) {
            System.out.println("\tThere are no authors available to update.");
            return;
        }

        System.out.println("\tPlease select an author to update:");
        for (int i = 0; i < authorList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + authorList.get(i));
        }
        int authorChoice = -1;
        while (authorChoice < 0 || authorChoice > authorList.size()) {
            try {
                System.out.print("\tEnter the number of the author you want to update: ");
                authorChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (authorChoice < 0 || authorChoice >= authorList.size()) {
                    throw new InputMismatchException("Invalid author selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                authorChoice = -1;
            }
        }

        String newAuthorName = null;
        while (newAuthorName == null) {
            try {
                System.out.print("\n\tEnter the new name for the selected author: ");
                newAuthorName = scan.next();
                if (newAuthorName.length() == 0) {
                    throw new IOException("Author name cannot be empty");
                }
            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
                newAuthorName = null;
            }
        }

        boolean updateSuccess = authorDAO.updateAuthorById(authorList.get(authorChoice).getAuthorId(), newAuthorName);
        if (updateSuccess) {
            System.out.println("Author updated successfully!");
        } else {
            System.err.println("Failed to update author.");
        }
    }

    private static void deleteAuthor(Scanner scan, AuthorsDAOClass authorDAO) {
        List<AuthorPOJO> authorList = authorDAO.getAllAuthors();
        if (authorList.isEmpty()) {
            System.out.println("\tThere are no authors available to delete.");
            return;
        }

        System.out.println("\tPlease select an author to delete:");
        for (int i = 0; i < authorList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + authorList.get(i));
        }
        int authorChoice = -1;
        while (authorChoice < 0 || authorChoice > authorList.size()) {
            try {
                System.out.print("\tEnter the number of the author you want to delete: ");
                authorChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (authorChoice < 0 || authorChoice >= authorList.size()) {
                    throw new InputMismatchException("Invalid author selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                authorChoice = -1;
            }
        }

        boolean deleteSuccess = authorDAO.deleteAuthorById(authorList.get(authorChoice).getAuthorId());
        if (deleteSuccess) {
            System.out.println("Author deleted successfully!");
        } else {
            System.err.println("Failed to delete author.");
        }
    }

    private static void addBook(Scanner scan, BooksDAOClass bookDAO) {
        String title = null;
        String isbn13 = null;
        String genreName = null;
        String authorName = null;
        Date publicationDate = null;



        while (title == null || isbn13 == null || genreName == null || 
                authorName == null || publicationDate == null) {
            try {
                System.out.println("\n\n\tPlease enter the details of the book you want to add:");
                System.out.print("\tTitle: ");
                title = scan.next();
                if (title.length() == 0) {
                    throw new IOException("Title cannot be empty");
                }

                System.out.print("\tISBN-13: ");
                isbn13 = scan.next();
                if (isbn13.length() != 0) {
                    throw new IOException("ISBN-13 must be exactly 13 characters long");
                }

                System.out.print("\tgenre: ");
                genreName = scan.next();
                if (genreName.length() != 10) {
                    throw new IOException("Genre cannot be empty");
                }

                System.out.print("\tAuthor: ");
                authorName = scan.next();
                if (authorName.length() == 0) {
                    throw new IOException("Author cannot be empty");
                }

                System.out.print("\tPublication date: ");
                publicationDate = Date.valueOf(scan.next());

            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
            }
        }

        boolean addSuccess = bookDAO.addBook(title, publicationDate, isbn13, genreName, authorName);
        if (addSuccess) {
            System.out.println("Book added successfully!");
        } else {
            System.err.println("Failed to add book.");
        }
    }

    private static void updateBook(Scanner scan, BooksDAOClass bookDAO) {
        List<BookPOJO> bookList = bookDAO.getAllBooks();
        if (bookList.isEmpty()) {
            System.out.println("\tThere are no books available to update.");
            return;
        }

        System.out.println("\tPlease select a book to update:");
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + bookList.get(i));
        }
        int bookChoice = -1;
        while (bookChoice < 0 || bookChoice > bookList.size()) {
            try {
                System.out.print("\tEnter the number of the book you want to update: ");
                bookChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (bookChoice < 0 || bookChoice >= bookList.size()) {
                    throw new InputMismatchException("Invalid book selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                bookChoice = -1;
            }
        }
        
        String newTitle = null;
        String newIsbn13 = null;
        String newGenreName = null;
        String newAuthorName = null;
        Date newPublicationDate = null;

        while (newTitle == null || newIsbn13 == null || newGenreName == null || 
                newAuthorName == null || newPublicationDate == null) {
            try {
                System.out.print("\n\tEnter the new title for the selected book: ");
                newTitle = scan.next();
                if (newTitle.length() == 0) {
                    throw new IOException("Title cannot be empty");
                }

                System.out.print("\tEnter the new ISBN-13 for the selected book: ");
                newIsbn13 = scan.next();
                if (newIsbn13.length() != 13) {
                    throw new IOException("ISBN-13 must be exactly 13 characters long");
                }

                System.out.print("\tEnter the new genre for the selected book: ");
                newGenreName = scan.next();
                if (newGenreName.length() == 0) {
                    throw new IOException("Genre cannot be empty");
                }

                System.out.print("\tEnter the new author for the selected book: ");
                newAuthorName = scan.next();
                if (newAuthorName.length() == 0) {
                    throw new IOException("Author cannot be empty");
                }

                System.out.print("\tEnter the new publication date for the selected book (YYYY-MM-DD): ");
                newPublicationDate = Date.valueOf(scan.next());

            } catch (IOException e) {
                System.err.println("Invalid input detected: " + e.getMessage());
            }
        }
        boolean updateSuccess = bookDAO.updateBookById(
                bookList.get(bookChoice).getBookId(),
                newTitle, newPublicationDate, newIsbn13, newGenreName, newAuthorName);
        if (updateSuccess) {
            System.out.println("Book updated successfully!");
        } else {
            System.err.println("Failed to update book.");
        }
    }        

    private static void deleteBook(Scanner scan, BooksDAOClass bookDAO) {
        List<BookPOJO> bookList = bookDAO.getAllBooks();
        if (bookList.isEmpty()) {
            System.out.println("\tThere are no books available to delete.");
            return;
        }

        System.out.println("\tPlease select a book to delete:");
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + bookList.get(i));
        }
        int bookChoice = -1;
        while (bookChoice < 0 || bookChoice > bookList.size()) {
            try {
                System.out.print("\tEnter the number of the book you want to delete: ");
                bookChoice = scan.nextInt() - 1; // Adjust for zero-based index
                if (bookChoice < 0 || bookChoice >= bookList.size()) {
                    throw new InputMismatchException("Invalid book selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                bookChoice = -1;
            }
        }

        boolean deleteSuccess = bookDAO.deleteBookById(bookList.get(bookChoice).getBookId());
        if (deleteSuccess) {
            System.out.println("Book deleted successfully!");
        } else {
            System.err.println("Failed to delete book.");
        }
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
     * @return true if logout was successful, else false
     */
    private static boolean userLogOut() {
        try {
            // if no user has logged in
            if (!USER_LOGGED_IN) {
                throw new LogoutFailedException();
            }

            USER = null;
            USER_LOGGED_IN = false;
            return true;
        } catch (LogoutFailedException e) {
            System.err.println("user logout attempt failed: " + e.getMessage());
        }
        return false;
    }
}
