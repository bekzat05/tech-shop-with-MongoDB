package com.dbfinal.prac.responses;

import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.models.User;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private UserShowDto user;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserShowDto getUser() {
        return user;
    }

    public LoginResponse setUser(UserShowDto user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
