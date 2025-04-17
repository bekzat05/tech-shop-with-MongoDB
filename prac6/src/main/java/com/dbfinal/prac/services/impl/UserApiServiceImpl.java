package com.dbfinal.prac.services.impl;

import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.exceptions.UserNotFoundException;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.repositories.UserRepository;
import com.dbfinal.prac.services.UserApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserApiServiceImpl implements UserApiService {
    private final UserRepository userRepository;

    public List<UserShowDto> getAllUsers() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();

        log.info("Found {} users", users.size());
        return users.stream().map(this::convertToDto).toList();
    }

    public UserShowDto getUserById(String id) {
        log.info("Finding user by id: {}", id);
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));

        log.info("Found user: {}", user);
        return convertToDto(user);
    }

    public UserShowDto getUserByName(String name) {
        log.info("Finding user by name: {}", name);
        User user = userRepository.findUserByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));

        log.info("Found user: {}", user);
        return convertToDto(user);
    }


    public User update(String id, User updatedUser){
        log.info("Updating user: {}", updatedUser);
        updatedUser.setId(id);
        userRepository.save(updatedUser);

        log.info("Updated user: {}", updatedUser);
        return updatedUser;
    }

    public void delete(String id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);

        log.info("Deleted user with id: {}", id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserShowDto convertToDto(User user) {
        return new UserShowDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getEmail(),
                user.getBalance()
        );
    }
}
