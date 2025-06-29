package com.collier.personal_project.dao.book;

import java.sql.Date;
import java.util.List;

import com.collier.personal_project.dao_model.BookPOJO;

public interface BooksDAOInterface {
    /**
     * Crud operations for Books
     */

    public List<BookPOJO> getAllBooks();
    public BookPOJO getBookById();
    public List<BookPOJO> getBooksByTitle();
    public BookPOJO getBookByISBN(String isbn_13);

    public boolean addBook(String title, Date publishDate, 
                            String isbn_13, String genre, String author);
    
    public boolean updateBookById(int id, String title, Date publishDate,
                                    String isbn_13, String genre, String author);
    public boolean updateBookByIsbn13(String title, Date publishDate, 
                                        String isbn_13, String genre, String author);
    
    public boolean deleteBookByIsbn13(String isbn_13);
    public boolean deleteBookById(int id);
}
