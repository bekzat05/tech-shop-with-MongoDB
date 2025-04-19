package com.dbfinal.prac.controllers;

import com.dbfinal.prac.models.Category;
import com.dbfinal.prac.services.CategoryApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CategoryApiControllerTest {

    @Mock
    private CategoryApiService categoryService;

    private CategoryApiController categoryApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryApiController = new CategoryApiController(categoryService);
    }

    @Test
    void shouldReturnAllCategories() {
        // Arrange
        List<Category> mockCategories = Arrays.asList(
                new Category("1", "Electronics", "electronics.png"),
                new Category("2", "Books", "books.png")
        );
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        // Act
        ResponseEntity<List<Category>> response = categoryApiController.getAllCategories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCategories, response.getBody());
    }
}