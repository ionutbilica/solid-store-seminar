package com.luxoft.training.solid.store.persistence;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4779888699623232478L;

	public ProductNotFoundException(String productId) {
        super("Product with id [" + productId + "] does not exist.");
    }
}
