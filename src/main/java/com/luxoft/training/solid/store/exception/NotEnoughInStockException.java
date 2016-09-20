package com.luxoft.training.solid.store.exception;

import com.luxoft.training.solid.store.Product;

public class NotEnoughInStockException extends RuntimeException {

    public NotEnoughInStockException(Product product, int countToRemove) {
        super("Not enough of product [] " + product + " to take " + (-countToRemove));
    }
}
