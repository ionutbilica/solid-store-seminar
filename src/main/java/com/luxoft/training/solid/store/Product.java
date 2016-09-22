package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.persistence.ProductData;
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

    public Product(ProductData data) {
        this(data.getName(), data.getPrice(), data.getCount());
    }

    public Product(Product originalProduct, int count) {
        this(originalProduct.name, originalProduct.price, count);
    }

    public double getFullPriceForAll() {
        return price * count;
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

    public ProductData getData() {
        return new ProductData(name, price, count);
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
