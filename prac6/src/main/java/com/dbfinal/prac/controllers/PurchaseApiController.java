package com.dbfinal.prac.controllers;

import com.dbfinal.prac.models.Purchase;
import com.dbfinal.prac.services.ProductApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@AllArgsConstructor
public class PurchaseApiController {

    private final ProductApiService productApiService;

    @PostMapping("/{userId}/buy/{productId}")
    public ResponseEntity<Purchase> purchaseProduct(
            @PathVariable String userId,
            @PathVariable String productId,
            @RequestParam int quantity) {
        Purchase purchase = productApiService.purchaseProduct(userId, productId, quantity);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Purchase>> getPurchaseHistory(@PathVariable String userId) {
        List<Purchase> purchases = productApiService.getPurchaseHistory(userId);
        return ResponseEntity.ok(purchases);
    }
}
