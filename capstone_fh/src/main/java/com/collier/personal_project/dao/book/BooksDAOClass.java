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
import com.collier.personal_project.custom_exceptions.database_exceptions.AuthorNotFoundException;
import com.collier.personal_project.custom_exceptions.database_exceptions.BookNotCreatedException;
import com.collier.personal_project.custom_exceptions.database_exceptions.BookNotFoundException;
import com.collier.personal_project.custom_exceptions.database_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.custom_exceptions.database_exceptions.GenreNotFoundException;
import com.collier.personal_project.dao.author.AuthorsDAOClass;
import com.collier.personal_project.dao.genre.GenreDAOClass;
import com.collier.personal_project.dao_model.AuthorPOJO;
import com.collier.personal_project.dao_model.BookPOJO;
import com.collier.personal_project.dao_model.GenrePOJO;

public class BooksDAOClass implements BooksDAOInterface{

    // Get a connection to db
    private Connection dbConnection;

    // private helper methods

    private int getBookGenreId(String genreName) throws GenreNotFoundException{
        GenreDAOClass genreDAO = new GenreDAOClass();
        GenrePOJO genre = genreDAO.getGenreByName(genreName);
        if (genre == null)
            throw new GenreNotFoundException();
        return genre.getGenreId();
    }

    private int getBookAuthorId(String authorName) throws AuthorNotFoundException {
        AuthorsDAOClass authorDAO = new AuthorsDAOClass();
        AuthorPOJO author = authorDAO.getAuthorByName(authorName);
        if (author == null)
            throw new AuthorNotFoundException();
        return author.getAuthorId();
    }

    // interface method implementations

    @Override
    public boolean addBook(String title, Date publishDate, String isbn_13, String genre, String author){
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            int genreId = getBookGenreId(genre);
            int authorId = getBookAuthorId(author);

            String sql = "INSERT INTO books(genre_id, author_id, title, publication_date, isbn_13) VALUES(?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, genreId);
            ps.setInt(2, authorId);
            ps.setString(3, title);
            ps.setDate(4, publishDate);
            ps.setString(5, isbn_13);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookNotCreatedException();
            } else {
                return true;
            }
        } catch (GenreNotFoundException e) {
            System.err.println("addBook threw a GenreNotFoundException: " + e.getMessage());
        } catch(AuthorNotFoundException e) {
            System.err.println("addBook threw a AuthorNotFoundException: " + e.getMessage());
        }catch (DBReturnNullConnectionException e) {
            System.err.println("addBook threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("addBook threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("addBook threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("addBook threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("addBook threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotCreatedException e) {
            System.err.println("addBook threw a BookNotCreatedException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBookById(int id) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM books WHERE book_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteBookById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteBookById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteBookById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteBookById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteBookById threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBookByIsbn13(String isbn_13) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM books WHERE isbn_13 = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, isbn_13);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteBookById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteBookById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteBookById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteBookById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteBookById threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        }
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
    public BookPOJO getBookByISBN(String isbn_13) {
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
            System.err.println("getBookByISBN threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getBookByISBN threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getBookByISBN threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getBookByISBN threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getBookByISBN threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public BookPOJO getBookById(int id) {
        BookPOJO book;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM books WHERE book_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                book = new BookPOJO(rs.getInt("author_id"),
                            id,
                            rs.getString("title"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("genre_id"),
                            rs.getString("isbn_13"),
                            rs.getDate("publication_date"),
                            rs.getTimestamp("updated_at")
                        );
            else
                throw new BookNotFoundException();
            return book;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getBookById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getBookById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getBookById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getBookById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getBookById threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<BookPOJO> getBooksByTitle(String title) {
        List<BookPOJO> books = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM books WHERE title = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int authorId = rs.getInt("author_id");
                int bookId = rs.getInt("book_id");
                Timestamp createdAt = rs.getTimestamp("created_at");
                int genreId = rs.getInt("genre_id");
                String isbn13 = rs.getString("isbn_13");
                Date publishDate = rs.getDate("publication_date");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                
                BookPOJO book = new BookPOJO(authorId, bookId, title, 
                                createdAt, genreId, isbn13, publishDate, updatedAt);
                books.add(book);
            }
            
            return books;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getBookByISBN threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getBookByISBN threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getBookByISBN threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getBookByISBN threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getBookByISBN threw a ClassNotFoundException: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateBookById(int id, String title, Date publishDate, String isbn_13, String genre, String author) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            int genreId = getBookGenreId(genre);
            int authorId = getBookAuthorId(author);

            String sql = "UPDATE books SET title = ?, publishDate = ?, isbn_13 = ?, genre_id = ?, author_id = ? WHERE author_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setDate(2, publishDate);
            ps.setString(3, isbn_13);
            ps.setInt(4, genreId);
            ps.setInt(5, authorId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("updateAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("updateAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("updateAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("updateAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("updateAuthorById threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("deleteBookById threw a GenreNotFoundException: " + e.getMessage());
        } catch (AuthorNotFoundException e) {
            System.err.println("deleteBookById threw a AuthorNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateBookByIsbn13(String title, Date publishDate, String isbn_13, String genre, String author) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            int genreId = getBookGenreId(genre);
            int authorId = getBookAuthorId(author);

            String sql = "UPDATE books SET title = ?, publishDate = ?, genre_id = ?, author_id = ? WHERE isbn_13 = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setDate(2, publishDate);
            ps.setInt(3, genreId);
            ps.setInt(4, authorId);
            ps.setString(5, isbn_13);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("updateAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("updateAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("updateAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("updateAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("updateAuthorById threw a ClassNotFoundException: " + e.getMessage());
        } catch (BookNotFoundException e) {
            System.err.println("deleteBookById threw a BookNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("deleteBookById threw a GenreNotFoundException: " + e.getMessage());
        } catch (AuthorNotFoundException e) {
            System.err.println("deleteBookById threw a AuthorNotFoundException: " + e.getMessage());
        }
        return false;
    }

    

}
