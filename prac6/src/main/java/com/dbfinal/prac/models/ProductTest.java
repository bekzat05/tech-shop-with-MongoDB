package com.dbfinal.prac.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "productsTest")
public class ProductTest {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
}
