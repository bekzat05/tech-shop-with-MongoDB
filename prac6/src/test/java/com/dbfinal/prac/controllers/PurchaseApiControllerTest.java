package com.dbfinal.prac.controllers;

import com.dbfinal.prac.models.Purchase;
import com.dbfinal.prac.services.ProductApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PurchaseApiControllerTest {

    @Mock
    private ProductApiService productApiService;

    private PurchaseApiController purchaseApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseApiController = new PurchaseApiController(productApiService);
    }

    @Test
    void shouldPurchaseProductSuccessfully() {
        // Arrange
        String userId = "user1";
        String productId = "product1";
        int quantity = 2;

        Purchase mockPurchase = new Purchase(
                "purchase1",
                userId,
                productId,
                "Product1",
                "product1.png",
                quantity,
                100.0,
                200.0,
                "Category1",
                new Date()
        );

        when(productApiService.purchaseProduct(userId, productId, quantity)).thenReturn(mockPurchase);

        // Act
        ResponseEntity<Purchase> response = purchaseApiController.purchaseProduct(userId, productId, quantity);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPurchase, response.getBody());
    }

    @Test
    void shouldReturnPurchaseHistorySuccessfully() {
        // Arrange
        String userId = "user1";
        List<Purchase> mockPurchases = Arrays.asList(
                new Purchase(
                        "purchase1",
                        userId,
                        "product1",
                        "Product1",
                        "product1.png",
                        2,
                        100.0,
                        200.0,
                        "Category1",
                        new Date()
                ),
                new Purchase(
                        "purchase2",
                        userId,
                        "product2",
                        "Product2",
                        "product2.png",
                        1,
                        150.0,
                        150.0,
                        "Category2",
                        new Date()
                )
        );

        when(productApiService.getPurchaseHistory(userId)).thenReturn(mockPurchases);

        // Act
        ResponseEntity<List<Purchase>> response = purchaseApiController.getPurchaseHistory(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPurchases, response.getBody());
    }
}