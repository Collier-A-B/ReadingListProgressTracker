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
import com.collier.personal_project.custom_exceptions.database_exceptions.BookPresentReadingListException;
import com.collier.personal_project.custom_exceptions.database_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao_model.ReadingListBookPOJO;

public class UserBookDAOClass implements UserBookDAOInterface{

    private Connection dbConnection;

    @Override
    public List<ReadingListBookPOJO> getAllBooksInUserList(int userId) {
        List<ReadingListBookPOJO> userBooks = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = 
                """ 
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
                    rs.getDate("end_date")
                );
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

            String checkBookAlreadyAddedSQL = 
                """
                SELECT COUNT(*) FROM users_books 
                INNER JOIN books ON users_books.book_id = books.book_id
                WHERE users_books.user_id = ? AND books.isbn_13 = ?;
                """;
                PreparedStatement checkPs = dbConnection.prepareStatement(checkBookAlreadyAddedSQL);
                checkPs.setInt(1, userId);
                checkPs.setString(2, isbn_13);
                ResultSet checkRs = checkPs.executeQuery();
                if (checkRs.next() && checkRs.getInt(1) > 0)
                {
                    throw new BookPresentReadingListException();
                }
        } catch (BookPresentReadingListException e) {
            System.err.println("addBookToUserListByISBN threw a BookPresentReadingListException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeBookFromUserListByISBN'");
    }

    @Override
    public boolean updateBookStatusByISBN(int userId, String isbn_13, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBookStatusByISBN'");
    }

    @Override
    public boolean addBookToUserListByBookTitle(int userId, String bookTitle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBookToUserListByBookTitle'");
    }

    @Override
    public boolean removeBookFromUserListByBookTitle(int userId, String bookTitle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeBookFromUserListByBookTitle'");
    }

    @Override
    public boolean updateBookStatusByBookTitle(int userId, String bookTitle, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBookStatusByBookTitle'");
    }

}
