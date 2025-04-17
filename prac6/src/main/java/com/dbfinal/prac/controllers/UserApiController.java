package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.services.UserApiService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserApiController {

    private final UserApiService userService;


    @GetMapping()
    public ResponseEntity<List<UserShowDto>> getAllUsers() {
        List<UserShowDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserShowDto> getUserByName(@PathVariable String name) {
        UserShowDto user = userService.getUserByName(name);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserShowDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserShowDto userDto = userService.convertToDto(currentUser);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserShowDto> getUserById(@PathVariable String id) {
        UserShowDto user = userService.getUserById(id);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid User user, @PathVariable("id") String id) {
        User updatedUser = userService.update(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        UserShowDto user = userService.getUserById(id);
        if (user != null) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
