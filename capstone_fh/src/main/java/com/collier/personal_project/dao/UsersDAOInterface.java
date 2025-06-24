package com.collier.personal_project.dao;

import java.util.List;

import com.collier.personal_project.dao_model.UserPOJO;

public interface UsersDAOInterface {
    /**
     * Crud operations for Users
     */

    public List<UserPOJO> getAllUsers();
    public UserPOJO getUserById(int id);
    public UserPOJO getUserByUsername(String username);

    public boolean addUser(String username, String password);
    
    public boolean updateUserById(int id, String username, String pasword);

    public boolean deleteUserById(int id);
    public boolean deleteUserByUsername(String username);
}
