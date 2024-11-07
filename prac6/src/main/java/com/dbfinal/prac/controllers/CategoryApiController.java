package com.dbfinal.prac.controllers;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.models.Category;
import com.dbfinal.prac.services.CategoryApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryApiService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> products = categoryService.getAllCategories();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
