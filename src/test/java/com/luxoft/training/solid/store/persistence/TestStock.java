package com.luxoft.training.solid.store.persistence;

import java.util.HashMap;
import java.util.Map;

public class TestStock implements Stock {

    public static final double BREAD_PRICE = 5;
    public static final double WINE_PRICE = 10;
    public static final String BREAD_NAME = "Bread";
    public static final String WINE_NAME = "Wine";

    private final Map<String, ProductData> products;

    public TestStock() {
        products = new HashMap<>();
        addProduct(BREAD_NAME, BREAD_PRICE, 100);
        addProduct(WINE_NAME, WINE_PRICE, 100);
    }

    @Override
    public ProductData takeProduct(String name, int count) {
        ProductData productInStock = findProduct(name);
        removeProduct(productInStock, count);
        return new ProductData(productInStock.getName(), productInStock.getPrice(), count);
    }

    private void addProduct(String name, double price, int count) {
        ProductData p;
        try {
            p = findProduct(name);
            p = new ProductData(name, price, p.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new ProductData(name, price, count);
        }
        products.put(name, p);
    }

    private ProductData findProduct(String name) {
        ProductData productInStock = products.get(name);
        if (productInStock == null) {
            throw new ProductNotFoundException(name);
        }
        return productInStock;
    }

    private void removeProduct(ProductData p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new ProductData(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        products.put(p.getName(), p);
    }
}
