package com.dbfinal.prac.repositories;

import com.dbfinal.prac.models.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<Purchase> findPurchaseByUserId(String userId);

}
