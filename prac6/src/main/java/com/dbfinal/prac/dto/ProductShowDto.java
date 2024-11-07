package com.dbfinal.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductShowDto {
    private String id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private int likeCount;
    private String img;
}
