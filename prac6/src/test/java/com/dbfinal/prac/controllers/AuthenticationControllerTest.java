package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.LoginUserDto;
import com.dbfinal.prac.dto.RegisterUserDto;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.responses.LoginResponse;
import com.dbfinal.prac.services.AuthenticationService;
import com.dbfinal.prac.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationController = new AuthenticationController(jwtService, authenticationService);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto()
                .setName("John Doe")
                .setEmail("user@example.com")
                .setPassword("Password123!")
                .setAge(30)
                .setGender("Male");
        User registeredUser = new User()
                .setId("1")
                .setName("John Doe")
                .setEmail("user@example.com")
                .setPassword("encodedPassword")
                .setAge(30)
                .setGender("Male")
                .setBalance(10000.0);
        when(authenticationService.signup(registerUserDto)).thenReturn(registeredUser);

        // Act
        ResponseEntity<User> response = authenticationController.register(registerUserDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredUser, response.getBody());
    }

    @Test
    void shouldReturnConflictWhenRegisteringWithExistingEmail() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto()
                .setName("John Doe")
                .setEmail("user@example.com")
                .setPassword("Password123!")
                .setAge(30)
                .setGender("Male");
        when(authenticationService.signup(registerUserDto))
                .thenThrow(new IllegalArgumentException("Email is already in use"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationController.register(registerUserDto);
        });
        assertEquals("Email is already in use", exception.getMessage());
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto()
                .setEmail("user@example.com")
                .setPassword("Password123!");
        User authenticatedUser = new User()
                .setId("1")
                .setName("John Doe")
                .setEmail("user@example.com")
                .setPassword("encodedPassword")
                .setAge(30)
                .setGender("Male")
                .setBalance(10000.0);
        String mockToken = "mock-jwt-token";
        when(authenticationService.authenticate(loginUserDto)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn(mockToken);
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        // Act
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUserDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponse loginResponse = response.getBody();
        assertEquals(mockToken, loginResponse.getToken());
        assertEquals(3600L, loginResponse.getExpiresIn());
        assertEquals("John Doe", loginResponse.getUser().getName());
    }

    @Test
    void shouldReturnUnauthorizedForInvalidCredentials() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto()
                .setEmail("user@example.com")
                .setPassword("wrongpassword");
        when(authenticationService.authenticate(loginUserDto))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationController.authenticate(loginUserDto);
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }
}