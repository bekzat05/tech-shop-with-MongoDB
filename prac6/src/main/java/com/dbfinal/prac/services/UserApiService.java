package com.dbfinal.prac.services;

import com.dbfinal.prac.models.User;

import java.util.List;

public interface UserApiService {
    boolean existsByEmail(String email);
    List<User> getAllUsers();

    User getUserById(String id);

    User getUserByName(String name);

    User addUser(User user);

    User update(String id, User updatedUser);

    void delete(String id);
}
