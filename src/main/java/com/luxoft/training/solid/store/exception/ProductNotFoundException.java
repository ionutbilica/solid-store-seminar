package com.luxoft.training.solid.store.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Product with id [" + productId + "] does not exist.");
    }
}
