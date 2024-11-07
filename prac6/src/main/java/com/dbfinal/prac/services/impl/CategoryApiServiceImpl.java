package com.dbfinal.prac.services.impl;

import com.dbfinal.prac.models.Category;
import com.dbfinal.prac.repositories.CategoryRepository;
import com.dbfinal.prac.services.CategoryApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryApiServiceImpl implements CategoryApiService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        log.info("Finding all categories");

        List<Category> categories = categoryRepository.findAll();

        log.info("Found {} categories", categories.size());
        return categories;
    }
}
