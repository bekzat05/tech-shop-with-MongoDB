package com.dbfinal.prac.services;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.ProductTest;
import com.dbfinal.prac.models.Purchase;
import com.dbfinal.prac.repositories.ProductRepository;
import com.dbfinal.prac.repositories.ProductTestRepository;
import com.dbfinal.prac.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductTestService {

    @Autowired
    private ProductTestRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<ProductTest> getRecommendedProducts(String userId) {
        // Step 1: Get user’s liked and purchased categories
        List<String> categories = getCategoriesFromUserActivity(userId);

        // Step 2: Get products in the user's liked and purchased categories
        List<ProductTest> recommendedProducts = productRepository.findByCategoryIn(categories)
                .stream()
                .toList();

        // Step 3: Get all other products that are not in the recommended list
        List<String> recommendedProductIds = recommendedProducts.stream()
                .map(ProductTest::getId)
                .toList();

        List<ProductTest> otherProducts = productRepository.findByIdNotIn(recommendedProductIds)
                .stream()
                .toList();

        // Step 4: Combine the two lists, with recommended products first
        List<ProductTest> allRecommendations = new ArrayList<>(recommendedProducts);
        allRecommendations.addAll(otherProducts);


        return allRecommendations;
    }

    public List<String> getCategoriesFromUserActivity(String userId) {
        // Retrieve categories from the user's purchase history and liked products
        List<String> purchasedCategories = purchaseRepository.findPurchaseByUserId(userId)
                .stream()
                .map(Purchase::getCategory)
                .distinct()
                .toList();

        List<String> likedCategories = productRepository.findAll().stream()
                .map(ProductTest::getCategory)
                .distinct()
                .toList();

        // Combine both lists and remove duplicates
        Set<String> categories = new HashSet<>(purchasedCategories);
        categories.addAll(likedCategories);

        return new ArrayList<>(categories);
    }
}
