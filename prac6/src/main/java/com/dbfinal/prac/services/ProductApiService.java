package com.dbfinal.prac.services;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.Purchase;

import java.util.List;

public interface ProductApiService {
    List<ProductShowDto> getAllProducts();

    Product getProductById(String id);

    Product getProductByName(String name);

    Product addProduct(Product product);

    Product update(String id, Product updatedProduct);

    void delete(String id);

    Purchase purchaseProduct(String userId, String productId, int quantity);

    List<Purchase> getPurchaseHistory(String userId);

    Product likeProduct(String userId, String productId);

    Product unlikeProduct(String userId, String productId);

    List<ProductShowDto> getLikedProductsByUser(String userId);

    List<ProductShowDto> getRecommendedProducts(String userId);

    List<ProductShowDto> getCollaborations();

    List<String> getCategoriesFromUserActivity(String userId);

    List<String> getAllCategories();

    List<ProductShowDto> getProductsByCategory(String category);

    List<ProductShowDto> searchProducts(String keyword);

}
