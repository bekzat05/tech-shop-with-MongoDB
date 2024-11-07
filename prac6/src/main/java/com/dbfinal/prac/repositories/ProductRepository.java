package com.dbfinal.prac.repositories;

import com.dbfinal.prac.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIdNotIn(List<String> ids);

    Optional<Product> findProductByName(String name);

    List<Product> findByCategoryIn(List<String> categories);

    List<Product> findByCategoryContainingIgnoreCase(String category);
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    boolean existsByCategoryContainingIgnoreCase(String category);
}
