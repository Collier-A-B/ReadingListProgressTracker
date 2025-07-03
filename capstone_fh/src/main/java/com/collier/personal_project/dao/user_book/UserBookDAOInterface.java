package com.collier.personal_project.dao.user_book;

import java.util.List;

import com.collier.personal_project.dao_model.ReadingListBookPOJO;

public interface UserBookDAOInterface {
    public List<ReadingListBookPOJO> getAllBooksInUserList(int userId);
}
