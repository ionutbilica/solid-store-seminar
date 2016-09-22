package com.luxoft.training.solid.store.provisioning;

import com.luxoft.training.solid.store.NotEnoughInStockException;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.ProductNotFoundException;
import com.luxoft.training.solid.store.Stock;

import java.util.HashMap;
import java.util.Map;

public class ProductsRepo implements Provisioning, Stock {

    private final Map<String, Product> products;

    public ProductsRepo() {
        this.products = new HashMap<>();
    }

    @Override
    public void addProduct(String name, double price, int count) {
        Product p;
        try {
            p = findProduct(name);
            p = new Product(name, price, p.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new Product(name, price, count);
        }
        products.put(name, p);
    }

    @Override
    public void removeProduct(String name, int countToRemove) {
        Product p = findProduct(name);
        removeProduct(p, countToRemove);
    }

    public Product takeProduct(String name, int count) {
        Product productInStock = findProduct(name);
        removeProduct(productInStock, count);
        return new Product(productInStock.getName(), productInStock.getPrice(), count);
    }

    private Product findProduct(String name) {
        Product productInStock = products.get(name);
        if (productInStock == null) {
            throw new ProductNotFoundException(name);
        }
        return productInStock;
    }

    private void removeProduct(Product p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new Product(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        products.put(p.getName(), p);
    }
}
