package com.dbfinal.prac.exceptions;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String id) {
        super("Product with id " + id + " not found");
    }
}
