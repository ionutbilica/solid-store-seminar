package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.receipt.Receipt;

public final class Product {

    private final String name;
    private final double price;
    private final int count;

    public Product (String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public double getFullPriceForAll() {
        return getPrice() * count;
    }

    public void fillReceipt(Receipt receipt) {
        receipt.addProduct(name, count, price, getFullPriceForAll());
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
