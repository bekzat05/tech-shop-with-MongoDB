package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.UserShowDto;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.services.UserApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserApiControllerTest {

    @Mock
    private UserApiService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private UserApiController userApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userApiController = new UserApiController(userService);
    }

    @Test
    void shouldReturnAllUsers() {
        // Arrange
        List<UserShowDto> mockUsers = Arrays.asList(
                new UserShowDto("1", "John Doe", 30, "Male", "john@example.com", 10000.0),
                new UserShowDto("2", "Jane Smith", 25, "Female", "jane@example.com", 20000.0)
        );
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<UserShowDto>> response = userApiController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());
    }

    @Test
    void shouldReturnUserByNameWhenExists() {
        // Arrange
        String userName = "John Doe";
        UserShowDto mockUser = new UserShowDto("1", userName, 30, "Male", "john@example.com", 10000.0);
        when(userService.getUserByName(userName)).thenReturn(mockUser);

        // Act
        ResponseEntity<UserShowDto> response = userApiController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUserByNameDoesNotExist() {
        // Arrange
        String userName = "Unknown";
        when(userService.getUserByName(userName)).thenReturn(null);

        // Act
        ResponseEntity<UserShowDto> response = userApiController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnAuthenticatedUser() {
        // Arrange
        User currentUser = new User().setId("1").setName("John Doe").setAge(30).setGender("Male").setEmail("john@example.com").setBalance(10000.0);
        UserShowDto userDto = new UserShowDto("1", "John Doe", 30, "Male", "john@example.com", 10000.0);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(currentUser);
        when(userService.convertToDto(currentUser)).thenReturn(userDto);
        SecurityContextHolder.setContext(securityContext);

        // Act
        ResponseEntity<UserShowDto> response = userApiController.authenticatedUser();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void shouldReturnUserByIdWhenExists() {
        // Arrange
        String userId = "1";
        UserShowDto mockUser = new UserShowDto(userId, "John Doe", 30, "Male", "john@example.com", 10000.0);
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<UserShowDto> response = userApiController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUserByIdDoesNotExist() {
        // Arrange
        String userId = "unknown";
        when(userService.getUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserShowDto> response = userApiController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        String userId = "1";
        User userToUpdate = new User().setId(userId).setName("Updated Name").setAge(35).setGender("Male").setEmail("updated@example.com").setBalance(15000.0);
        when(userService.update(userId, userToUpdate)).thenReturn(userToUpdate);

        // Act
        ResponseEntity<User> response = userApiController.update(userToUpdate, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userToUpdate, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentUser() {
        // Arrange
        String userId = "unknown";
        User userToUpdate = new User().setId(userId).setName("Updated Name").setAge(35).setGender("Male").setEmail("updated@example.com").setBalance(15000.0);
        when(userService.update(userId, userToUpdate)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userApiController.update(userToUpdate, userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Arrange
        String userId = "1";
        UserShowDto mockUser = new UserShowDto(userId, "John Doe", 30, "Male", "john@example.com", 10000.0);
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<Void> response = userApiController.delete(userId);

        // Assert
        verify(userService, times(1)).delete(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() {
        // Arrange
        String userId = "unknown";
        when(userService.getUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<Void> response = userApiController.delete(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}