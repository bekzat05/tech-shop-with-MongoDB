package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.dto.SearchRequestDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.services.ProductApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductApiService productService;


    @GetMapping()
    public ResponseEntity<List<ProductShowDto>> getAllProducts() {
        List<ProductShowDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable("id") String id) {
        Product updatedProduct = productService.update(id, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{productId}/like")
    public ResponseEntity<Product> likeProduct(
            @PathVariable String productId,
            @RequestParam String userId) {
        Product product = productService.likeProduct(userId, productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{productId}/unlike")
    public ResponseEntity<Product> unlikeProduct(
            @PathVariable String productId,
            @RequestParam String userId) {
        Product product = productService.unlikeProduct(userId, productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity<List<ProductShowDto>> getLikedProductsByUser(@PathVariable String userId) {
        List<ProductShowDto> likedProducts = productService.getLikedProductsByUser(userId);
        return ResponseEntity.ok(likedProducts);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = productService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductShowDto>> getProductsByCategory(@PathVariable String category) {
        List<ProductShowDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductShowDto>> searchProducts(@RequestBody SearchRequestDto searchRequest) {
        List<ProductShowDto> products = productService.searchProducts(searchRequest.getKeyword());
        return ResponseEntity.ok(products);
    }
}
