package com.collier.personal_project.dao;

import java.util.List;

import com.collier.personal_project.dao_model.GenrePOJO;

public interface GenreDAOInterface {
    /**
     * Crud operations for Genres
     */

    public List<GenrePOJO> getAllGenres();
    public GenrePOJO getGenreById(int id);
    public GenrePOJO getGenreByName(String name);

    public boolean addGenre(String name);
    
    public boolean updateGenreById(String id, String newName);

    public boolean deleteGenreByName(String name);
    public boolean deleteGenreById(int id);
}
