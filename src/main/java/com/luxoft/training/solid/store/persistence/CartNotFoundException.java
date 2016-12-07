package com.luxoft.training.solid.store.persistence;

public class CartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8160293546214026679L;

	public CartNotFoundException(int cartId) {
        super("Cart with id [" + cartId + "] does not exist.");
    }
}
