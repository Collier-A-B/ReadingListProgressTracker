package com.collier.personal_project.dao.genre;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.collier.personal_project.connection.ConnectionManager;
import com.collier.personal_project.custom_exceptions.database_exceptions.DBReturnNullConnectionException;
import com.collier.personal_project.custom_exceptions.database_exceptions.GenreNotCreatedException;
import com.collier.personal_project.custom_exceptions.database_exceptions.GenreNotFoundException;
import com.collier.personal_project.dao_model.GenrePOJO;

public class GenreDAOClass implements GenreDAOInterface{

    // Get a connection to the database
    private Connection dbConnection;

    @Override
    public boolean addGenre(String name) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "INSERT INTO genres(name) VALUES(?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GenreNotCreatedException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("addAuthor threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("addAuthor threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("addAuthor threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("addAuthor threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("addAuthor threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotCreatedException e) {
            System.err.println("addAuthor threw a AuthorNotCreatedException" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteGenreById(int id) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM genres WHERE genre_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GenreNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteAuthorById threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("deleteAuthorById threw a GenreNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteGenreByName(String name) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "DELETE FROM genres WHERE name = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GenreNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("deleteAuthorById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("deleteAuthorById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("deleteAuthorById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("deleteAuthorById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("deleteAuthorById threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("deleteAuthorById threw a GenreNotFoundException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<GenrePOJO> getAllGenres() {
        List<GenrePOJO> genres = new ArrayList<>();

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM genres";
            PreparedStatement ps = dbConnection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("genre_id");
                String name = rs.getString("name");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                GenrePOJO genre = new GenrePOJO(id, name, createdAt, updatedAt);
                genres.add(genre);
            }
            return genres;
        } catch (ClassNotFoundException e) {
            System.err.println("getAllAuthors threw a ClassNotFoundException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getAllAuthors threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getAllAuthors threw a IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getAllAuthors threw a SQLException: " + e.getMessage());
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getAllAuthors threw a DBReturnNullConnectionException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public GenrePOJO getGenreById(int id) {
        GenrePOJO genre;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM genres WHERE genre_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                genre = new GenrePOJO(id,
                        rs.getString("name"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"));
            else
                throw new GenreNotFoundException();
            return genre;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getGenreById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getGenreById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getGenreById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getGenreById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getGenreById threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("getGenreById threw a GenreNotFoundException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public GenrePOJO getGenreByName(String name) {
        GenrePOJO genre;

        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "SELECT * FROM genres WHERE name = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                genre = new GenrePOJO(rs.getInt("genre_id"),
                        name,
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"));
            else
                throw new GenreNotFoundException();
            return genre;
        } catch (DBReturnNullConnectionException e) {
            System.err.println("getGenreByName threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("getGenreByName threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("getGenreByName threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("getGenreByName threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("getGenreByName threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("getGenreByName threw a GenreNotFoundException: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean updateGenreById(int id, String newName) {
        try {
            dbConnection = ConnectionManager.getConnection();
            System.out.println("Connection established successfully: " + dbConnection.getCatalog());

            String sql = "UPDATE genres SET name = ? WHERE genre_id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GenreNotFoundException();
            } else {
                return true;
            }
        } catch (DBReturnNullConnectionException e) {
            System.err.println("updateGenreById threw a DBReturnNullConnectionException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("updateGenreById threw a SQLException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("updateGenreById threw a FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("updateGenreById threw a IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("updateGenreById threw a ClassNotFoundException: " + e.getMessage());
        } catch (GenreNotFoundException e) {
            System.err.println("updateGenreById threw a GenreNotFoundException: " + e.getMessage());
        }
        return false;
    }

}
