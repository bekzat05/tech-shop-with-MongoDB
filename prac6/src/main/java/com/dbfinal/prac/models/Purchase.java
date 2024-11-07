package com.dbfinal.prac.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "purchases")
public class Purchase {
    @Id
    private String id;
    private String userId;
    private String productId;
    private String productName;
    private String img;
    private int quantity;
    private double price;
    private double totalAmount;
    private String category;
    private Date purchaseDate;
}
