package com.luxoft.training.solid.store;

public class NotEnoughInStockException extends RuntimeException {

    public NotEnoughInStockException(Product product, int countToRemove) {
        super("Not enough of product [] " + product + " to take " + (-countToRemove));
    }
}
