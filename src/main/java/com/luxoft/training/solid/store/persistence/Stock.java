package com.luxoft.training.solid.store.persistence;

public interface Stock {

    ProductData takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException;
}
