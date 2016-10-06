package com.luxoft.training.solid.store.persistence;

public class NotEnoughInStockException extends RuntimeException {

	private static final long serialVersionUID = 2738551754160624545L;

	public NotEnoughInStockException(ProductData product, int countToRemove) {
        super("Not enough of product [] " + product + " to take " + (-countToRemove));
    }
}
