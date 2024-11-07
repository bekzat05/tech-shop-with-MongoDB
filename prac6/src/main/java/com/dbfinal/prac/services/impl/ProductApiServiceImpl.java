package com.dbfinal.prac.services.impl;

import com.dbfinal.prac.dto.ProductShowDto;
import com.dbfinal.prac.exceptions.ProductNotFoundException;
import com.dbfinal.prac.exceptions.UserNotFoundException;
import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.Purchase;
import com.dbfinal.prac.models.User;
import com.dbfinal.prac.repositories.ProductRepository;
import com.dbfinal.prac.repositories.PurchaseRepository;
import com.dbfinal.prac.repositories.UserRepository;
import com.dbfinal.prac.services.ProductApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductApiServiceImpl implements ProductApiService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public ProductShowDto convertToDto(Product product) {
        return new ProductShowDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getLikeCount(),
                product.getImg()
        );
    }

    public List<ProductShowDto> getAllProducts() {
        log.info("Finding all products");
        List<Product> products = productRepository.findAll();

        log.info("Found {} products", products.size());
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }

    public Product getProductById(String id) {
        log.info("Finding product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        log.info("Found product: {}", product);
        return product;
    }

    public Product getProductByName(String name) {
        log.info("Finding product by name: {}", name);
        Product product = productRepository.findProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));

        log.info("Found product: {}", product);
        return product;
    }

    public Product addProduct(Product product) {
        log.info("Adding product: {}", product);
        Product createdProduct = productRepository.save(product);

        log.info("Added product: {}", createdProduct);
        return createdProduct;
    }

    public Product update(String id, Product updatedProduct){
        log.info("Updating product: {}", updatedProduct);
        updatedProduct.setId(id);
        productRepository.save(updatedProduct);

        log.info("Updated product: {}", updatedProduct);
        return updatedProduct;
    }

    public void delete(String id) {
        log.info("Deleting product by id: {}", id);
        productRepository.deleteById(id);

        log.info("Deleted product by id: {}", id);
    }

    public Purchase purchaseProduct(String userId, String productId, int quantity) {
        log.info("User {} is attempting to purchase {} units of product {}", userId, quantity, productId);

        // Check if product exists and has enough stock
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough product stock available");
        }

        // Check if user exists and has sufficient balance
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        double totalCost = product.getPrice() * quantity;
        if (user.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Deduct balance from user and update product quantity
        user.setBalance(user.getBalance() - totalCost);
        product.setQuantity(product.getQuantity() - quantity);

        // Save updated entities
        userRepository.save(user);
        productRepository.save(product);

        // Create and save purchase history
        Purchase purchase = new Purchase();
        purchase.setUserId(userId);
        purchase.setProductId(productId);
        purchase.setProductName(product.getName());
        purchase.setImg(product.getImg());
        purchase.setQuantity(quantity);
        purchase.setPrice(product.getPrice());
        purchase.setTotalAmount(totalCost);
        purchase.setPurchaseDate(new Date());
        purchase.setCategory(product.getCategory());

        purchaseRepository.save(purchase);

        log.info("Purchase successful: {}", purchase);
        return purchase;
    }

    public List<Purchase> getPurchaseHistory(String userId) {
        return purchaseRepository.findPurchaseByUserId(userId);
    }

    public Product likeProduct(String userId, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException(productId));

        product.addLike(userId);
        productRepository.save(product);

        log.info("Product {} liked by user {}", productId, userId);
        return product;
    }

    public Product unlikeProduct(String userId, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException(productId));

        product.removeLike(userId);
        productRepository.save(product);

        log.info("User {} liked product {}", userId, productId);
        return product;
    }

    public List<ProductShowDto> getLikedProductsByUser(String userId) {
        List<Product> likedProducts = productRepository.findAll().stream()
                .filter(product -> product.getLikedBy().contains(userId))
                .toList();

        return likedProducts.stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ProductShowDto> getRecommendedProducts(String userId) {
        // Step 1: Get user’s liked and purchased categories
        List<String> categories = getCategoriesFromUserActivity(userId);

        // Step 2: Get products in the user's liked and purchased categories
        List<Product> recommendedProducts = productRepository.findByCategoryIn(categories)
                    .stream()
                    .toList();

        // Step 3: Get all other products that are not in the recommended list
        List<String> recommendedProductIds = recommendedProducts.stream()
                .map(Product::getId)
                .toList();

        List<Product> otherProducts = productRepository.findByIdNotIn(recommendedProductIds)
                .stream()
                .toList();

        // Step 4: Combine the two lists, with recommended products first
        List<Product> allRecommendations = new ArrayList<>(recommendedProducts);
        allRecommendations.addAll(otherProducts);

        allRecommendations.sort(Comparator.comparingInt(Product::getLikeCount).reversed());


        return allRecommendations.stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<String> getCategoriesFromUserActivity(String userId) {
        // Retrieve categories from the user's purchase history and liked products
        List<String> purchasedCategories = purchaseRepository.findPurchaseByUserId(userId)
                .stream()
                .map(Purchase::getCategory)
                .distinct()
                .toList();

        List<String> likedCategories = productRepository.findAll().stream()
                .filter(product -> product.getLikedBy().contains(userId))
                .map(Product::getCategory)
                .distinct()
                .toList();

        // Combine both lists and remove duplicates
        Set<String> categories = new HashSet<>(purchasedCategories);
        categories.addAll(likedCategories);

        return new ArrayList<>(categories);
    }

    @Override
    public List<ProductShowDto> getCollaborations() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Product::getLikeCount).reversed())
                .limit(15)
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<String> getAllCategories() {
        // Retrieve distinct categories from all products
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductShowDto> getProductsByCategory(String category) {
        // Fetch products by category and convert to ProductDto
        List<Product> products = productRepository.findByCategoryIn(List.of(category));
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductShowDto> searchProducts(String keyword) {
        List<Product> products;

        if (productRepository.existsByCategoryContainingIgnoreCase(keyword)) {
            products = productRepository.findByCategoryContainingIgnoreCase(keyword);
        } else {
            products = productRepository.findByDescriptionContainingIgnoreCase(keyword);
        }

        return products.stream()
                .map(this::convertToDto)
                .toList();
    }
}

