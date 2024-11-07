package com.dbfinal.prac.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String id) {
        super("User with id " + id + " not found");
    }
}
