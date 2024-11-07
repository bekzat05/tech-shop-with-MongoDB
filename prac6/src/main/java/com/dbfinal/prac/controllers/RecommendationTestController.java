package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.ProductTest;
import com.dbfinal.prac.services.ProductApiService;
import com.dbfinal.prac.services.ProductTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationTestController {

    @Autowired
    private ProductTestService productTestService;
    @Autowired
    private ProductApiService productService;

    @GetMapping("/api/test/recommend120products/{userId}")
    public ResponseEntity<List<ProductShowDto>> recommend120Products(@PathVariable String userId) {
        long startTime = System.currentTimeMillis(); // Start time

        List<ProductShowDto> products = productService.getRecommendedProducts(userId); // Call the service method

        long endTime = System.currentTimeMillis(); // End time
        long duration = endTime - startTime; // Calculate duration in milliseconds

        System.out.println("Time taken to load 120 products: " + duration + " ms");
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/test/recommend1000products/{userId}")
    public ResponseEntity<List<ProductTest>> recommend1000Products(@PathVariable String userId) {
        long startTime = System.currentTimeMillis(); // Start time

        List<ProductTest> products = productTestService.getRecommendedProducts(userId); // Call the service method

        long endTime = System.currentTimeMillis(); // End time
        long duration = endTime - startTime; // Calculate duration in milliseconds

        System.out.println("Time taken to load 1000 products: " + duration + " ms");
        return ResponseEntity.ok(products);
    }
}
