package com.dbfinal.prac.repositories;

import com.dbfinal.prac.models.Product;
import com.dbfinal.prac.models.ProductTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTestRepository extends MongoRepository<ProductTest, String> {
    List<ProductTest> findByCategoryIn(List<String> categories);

    List<ProductTest> findByIdNotIn(List<String> ids);
}
