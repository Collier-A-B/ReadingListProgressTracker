package com.collier.personal_project.dao.user_book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.database_exceptions.BookNotFoundException;
import com.collier.personal_project.custom_exceptions.database_exceptions.BookNotPresentInReadingListException;
import com.collier.personal_project.custom_exceptions.database_exceptions.BookPresentReadingListException;
import com.collier.personal_project.custom_exceptions.database_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao.book.BooksDAOClass;
import com.collier.personal_project.dao_model.BookPOJO;
import com.collier.personal_project.dao_model.ReadingListBookPOJO;

public class UserBookDAOClass implements UserBookDAOInterface {

    private Connection dbConnection;
    private BooksDAOClass booksDAO = new BooksDAOClass();

    @Override
    public List<ReadingListBookPOJO> getAllBooksInUserList(int userId) {
        List<ReadingListBookPOJO> userBooks = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = """
                    SELECT
                        users_books.user_book_id,
                        books.book_id,
                        genres.name as genreName,
                        authors.name as authorName,
                        books.title,
                        books.publication_date,
                        books.isbn_13,
                        users_books.status,
                        users_books.start_date,
                        users_books.end_date
                    FROM users
                    INNER JOIN users_books
                        ON users.user_id = users_books.user_id
                    INNER JOIN books
                        ON users_books.book_id = books.book_id
                    INNER JOIN authors
                        ON books.author_id = authors.author_id
                    INNER JOIN genres
                        ON books.genre_id = genres.genre_id
                    WHERE users.user_id = ?
                    ORDER BY books.title ASC;
                    """;
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ReadingListBookPOJO readingListBook = new ReadingListBookPOJO(
                        rs.getInt("user_book_id"),
                        rs.getInt("book_id"),
                        rs.getString("genreName"),
                        rs.getString("authorName"),
                        rs.getString("title"),
                        rs.getDate("publication_date"),
                        rs.getString("isbn_13"),
                        rs.getString("status"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"));
                userBooks.add(readingListBook);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("getAllBooks threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAllBooks threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAllBooks threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAllBooks threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAllBooks threw a DBReturnNullConnectionException: " + e.getMessage());
        }
        return userBooks;
    }

    @Override
    public boolean addBookToUserListByISBN(int userId, String isbn_13) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            BookPOJO book = booksDAO.getBookByISBN(isbn_13);
            if (book == null) {
                throw new BookNotFoundException();
            }

            if (checkIfBookAddedToUserList(userId, book.getBookId())) {
                throw new BookPresentReadingListException();
            }

            String sql = """
                    INSERT INTO users_books (user_id, book_id)
                    VALUES (?, ?);
                    """;
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, book.getBookId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ISBN " + isbn_13 + " added to user ID " + userId + "'s reading list.");
                return true;
            }
        } catch (BookPresentReadingListException e) {
            System.err.println("addBookToUserListByISBN threw a BookPresentReadingListException: " + e.getMessage());
        } catch(BookNotFoundException e) {
            System.err.println("addBookToUserListByISBN threw a BookNotFoundException: " + e.getMessage());
        }catch (ClassNotFoundException e) {
            System.err.println("addBookToUserListByISBN threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("addBookToUserListByISBN threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("addBookToUserListByISBN threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("addBookToUserListByISBN threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("addBookToUserListByISBN threw a DBReturnNullConnectionException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeBookFromUserListByISBN(int userId, String isbn_13) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            BookPOJO book = booksDAO.getBookByISBN(isbn_13);
            if (book == null) {
                throw new BookNotFoundException();
            }

            if (!checkIfBookAddedToUserList(userId, book.getBookId())) {
                throw new BookNotPresentInReadingListException();
            }

            String sql = """
                    DELETE FROM users_books
                    WHERE user_id = ? AND book_id = ?;
                    """;
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, book.getBookId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ISBN " + isbn_13 + " removed from user ID " + userId + "'s reading list.");
                return true;
            } else {
                System.err.println("unable to remove book with ISBN " + isbn_13 + " from user's reading list.");
            }
        } catch(BookNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a BookNotFoundException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("removeBookFromUserListByISBN threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("removeBookFromUserListByISBN threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("removeBookFromUserListByISBN threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (BookNotPresentInReadingListException e) {
            System.err.println("removeBookFromUserListByISBN threw a BookNotPresentInReadingListException: " + e.getMessage());
        }
        return false;
    }

    
    @Override
    public boolean updateBookStatusByISBN(int userId, String isbn_13, String status) {
        
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            BookPOJO book = booksDAO.getBookByISBN(isbn_13);
            if (book == null) {
                throw new BookNotFoundException();
            }

            if (!checkIfBookAddedToUserList(userId, book.getBookId())) {
                throw new BookNotPresentInReadingListException();
            }

            String sql = """
                UPDATE users_books
                SET status = ?
                WHERE user_id = ? AND book_id = ?;
                    """;
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, book.getBookId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ISBN " + isbn_13 + " removed from user ID " + userId + "'s reading list.");
                return true;
            } else {
                System.err.println("unable to remove book with ISBN " + isbn_13 + " from user's reading list.");
            }
        } catch(BookNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a BookNotFoundException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("removeBookFromUserListByISBN threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("removeBookFromUserListByISBN threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("removeBookFromUserListByISBN threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("removeBookFromUserListByISBN threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (BookNotPresentInReadingListException e) {
            System.err.println("removeBookFromUserListByISBN threw a BookNotPresentInReadingListException: " + e.getMessage());
        }
        return false;
    }

    // Helper method to check if a book is already added to the user's reading list
    private boolean checkIfBookAddedToUserList(int userId, int bookId) throws SQLException {
            String checkIfBookAddedToUserListSQL = """
                SELECT user_book_id
                FROM users_books
                WHERE user_id = ? AND book_id = ?;
                """;
            PreparedStatement checkPs = dbConnection.prepareStatement(checkIfBookAddedToUserListSQL);
            checkPs.setInt(1, userId);
            checkPs.setInt(2, bookId);
            ResultSet checkRs = checkPs.executeQuery();
            return checkRs.next();

    }

}
