package com.collier.personal_project.dao.book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.BookNotFoundException;
import com.collier.personal_project.custom_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.dao_model.BookPOJO;

public class BooksDAOClass implements BooksDAOInterface{

    // Get a connection to db
    private Connection dbConnection;

    @Override
    public boolean addBook(String title, Date publishDate, String isbn_13, String genre, String author) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteBookById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteBookByIsbn13(String isbn_13) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<BookPOJO> getAllBooks() {
        List<BookPOJO> books = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM books";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                int authorId = rs.getInt("author_id");
                int genreId = rs.getInt("genre_id");
                String bookTitle = rs.getString("title");
                String isbn13 = rs.getString("isbn_13");
                Date publishDate = rs.getDate("publication_date");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
            
                BookPOJO book = new BookPOJO(authorId, bookId, bookTitle, createdAt, genreId, isbn13, publishDate, updatedAt);
                books.add(book);
            }
            return books;

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
        return null;
    }

    @Override
    public BookPOJO getBookByISBN(String isbn_13) throws BookNotFoundException{
        BookPOJO book;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM books WHERE isbn_13 = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, isbn_13);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                book = new BookPOJO(rs.getInt("author_id"),
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("genre_id"),
                            isbn_13,
                            rs.getDate("publication_date"),
                            rs.getTimestamp("updated_at")
                        );
            else
                throw new BookNotFoundException();
            return book;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getAuthorById threw a ClassNotFoundException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public BookPOJO getBookById() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BookPOJO> getBooksByTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateBookById(int id, String title, Date publishDate, String isbn_13, String genre, String author) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateBookByIsbn13(String title, Date publishDate, String isbn_13, String genre, String author) {
        // TODO Auto-generated method stub
        return false;
    }

}
