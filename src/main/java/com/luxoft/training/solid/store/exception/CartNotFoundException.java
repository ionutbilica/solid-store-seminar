package com.luxoft.training.solid.store.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(int cartId) {
        super("Cart with id [" + cartId + "] does not exist.");
    }
}
