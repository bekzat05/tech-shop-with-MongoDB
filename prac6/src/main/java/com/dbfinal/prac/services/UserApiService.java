package com.dbfinal.prac.services;

import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.models.User;

import java.util.List;

public interface UserApiService {
    boolean existsByEmail(String email);
    List<UserShowDto> getAllUsers();

    UserShowDto getUserById(String id);

    UserShowDto getUserByName(String name);

    User update(String id, User updatedUser);

    void delete(String id);

    UserShowDto convertToDto(User user);
}
