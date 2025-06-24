package com.collier.personal_project.dao;

import java.util.List;

import com.collier.personal_project.dao_model.AuthorPOJO;

public interface AuthorsDAOInterface {
    /**
     * Crud operations for Authors
     */

    public List<AuthorPOJO> getAllAuthors();
    public AuthorPOJO getAuthorById(int id);
    public AuthorPOJO getAuthorByName(String name);

    public boolean addAuthor(String name);

    public boolean updateAuthorById(int id, String name);

    public boolean deleteAuthorByName(String name);
    public boolean deleteAuthorById(int id);
}
