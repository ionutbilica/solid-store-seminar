package com.luxoft.training.solid.store;

public interface Stock {

    Product takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException;
}
