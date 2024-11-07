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
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private Set<String> likedBy = new HashSet<>();
    private String img;

    public void addLike(String userId) {
        likedBy.add(userId);
    }

    public void removeLike(String userId) {
        likedBy.remove(userId);
    }

    public int getLikeCount() {
        return likedBy.size();
    }
}
