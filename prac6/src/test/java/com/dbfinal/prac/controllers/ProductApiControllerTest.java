package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.dto.SearchRequestDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.services.ProductApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductApiControllerTest {

    @Mock
    private ProductApiService productApiService;

    private ProductApiController productApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productApiController = new ProductApiController(productApiService);
    }

    @Test
    void shouldReturnAllProductsSuccessfully() {
        // Arrange
        List<ProductShowDto> mockProducts = Arrays.asList(
                new ProductShowDto("1", "Laptop", "High-end laptop", "Electronics", 1500.0, 10, 5, "image1.png"),
                new ProductShowDto("2", "Tablet", "Affordable tablet", "Electronics", 500.0, 20, 10, "image2.png")
        );
        when(productApiService.getAllProducts()).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<ProductShowDto>> response = productApiController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
    }

    @Test
    void shouldReturnProductByNameSuccessfully() {
        // Arrange
        String productName = "Laptop";
        Product mockProduct = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.getProductByName(productName)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productApiController.getProductByName(productName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenProductByNameDoesNotExist() {
        // Arrange
        String productName = "Unknown";
        when(productApiService.getProductByName(productName)).thenReturn(null);

        // Act
        ResponseEntity<Product> response = productApiController.getProductByName(productName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnProductByIdSuccessfully() {
        // Arrange
        String productId = "1";
        Product mockProduct = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.getProductById(productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productApiController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenProductByIdDoesNotExist() {
        // Arrange
        String productId = "unknown";
        when(productApiService.getProductById(productId)).thenReturn(null);

        // Act
        ResponseEntity<Product> response = productApiController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        // Arrange
        Product productToCreate = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.addProduct(productToCreate)).thenReturn(productToCreate);

        // Act
        ResponseEntity<Product> response = productApiController.createProduct(productToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productToCreate, response.getBody());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Arrange
        String productId = "1";
        Product productToUpdate = new Product("1", "Laptop", "Updated laptop", 1600.0, 8, "Electronics", null, "image1.png");
        when(productApiService.update(productId, productToUpdate)).thenReturn(productToUpdate);

        // Act
        ResponseEntity<Product> response = productApiController.update(productToUpdate, productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productToUpdate, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentProduct() {
        // Arrange
        String productId = "unknown";
        Product productToUpdate = new Product("unknown", "Laptop", "Updated laptop", 1600.0, 8, "Electronics", null, "image1.png");
        when(productApiService.update(productId, productToUpdate)).thenReturn(null);

        // Act
        ResponseEntity<Product> response = productApiController.update(productToUpdate, productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // Arrange
        String productId = "1";
        Product mockProduct = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.getProductById(productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Void> response = productApiController.delete(productId);

        // Assert
        verify(productApiService, times(1)).delete(productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentProduct() {
        // Arrange
        String productId = "unknown";
        when(productApiService.getProductById(productId)).thenReturn(null);

        // Act
        ResponseEntity<Void> response = productApiController.delete(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldLikeProductSuccessfully() {
        // Arrange
        String productId = "1";
        String userId = "123";
        Product mockProduct = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.likeProduct(userId, productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productApiController.likeProduct(productId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void shouldUnlikeProductSuccessfully() {
        // Arrange
        String productId = "1";
        String userId = "123";
        Product mockProduct = new Product("1", "Laptop", "High-end laptop", 1500.0, 10, "Electronics", null, "image1.png");
        when(productApiService.unlikeProduct(userId, productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productApiController.unlikeProduct(productId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void shouldSearchProductsSuccessfully() {
        // Arrange
        SearchRequestDto searchRequest = new SearchRequestDto("Laptop");
        List<ProductShowDto> mockProducts = Arrays.asList(
                new ProductShowDto("1", "Laptop", "High-end laptop", "Electronics", 1500.0, 10, 5, "image1.png")
        );
        when(productApiService.searchProducts(searchRequest.getKeyword())).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<ProductShowDto>> response = productApiController.searchProducts(searchRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
    }
}