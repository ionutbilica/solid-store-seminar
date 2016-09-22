package com.luxoft.training.solid.store.persistence;

public class NotEnoughInStockException extends RuntimeException {

    public NotEnoughInStockException(ProductData product, int countToRemove) {
        super("Not enough of product [] " + product + " to take " + (-countToRemove));
    }
}
