package com.luxoft.training.solid.store;

public class TestStock {

    public static final double BREAD_PRICE = 5;
    public static final double WINE_PRICE = 10;
    public static final String BREAD_NAME = "Bread";
    public static final String WINE_NAME = "Wine";

    public void insertIntoStore(Store store) {
        store.addProductToStock(BREAD_NAME, BREAD_PRICE, 100);
        store.addProductToStock(WINE_NAME, WINE_PRICE, 100);
    }
}
