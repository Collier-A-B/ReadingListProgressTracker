package com.collier.personal_project.dao.user_book;

import java.util.List;

import com.collier.personal_project.dao_model.ReadingListBookPOJO;

public interface UserBookDAOInterface {
    public List<ReadingListBookPOJO> getAllBooksInUserList(int userId);

    public boolean addBookToUserListByISBN(int userId, String isbn_13);
    public boolean removeBookFromUserListByISBN(int userId, String isbn_13);
    public boolean updateBookStatusByISBN(int userId, String isbn_13, String status);


}
