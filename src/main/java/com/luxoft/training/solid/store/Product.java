package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.discount.Discount;
import com.luxoft.training.solid.store.persistence.ProductData;
import com.luxoft.training.solid.store.receipt.Receipt;

public final class Product {

    private final String name;
    private final double price;
    private final int count;
    private final Discount discount;

    public Product(String name, double price, int count, Discount discount) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.discount = discount;
    }

    public Product(ProductData data, Discount discount) {
        this(data.getName(), data.getPrice(), data.getCount(), discount);
    }

    public double getFullPriceForAll() {
        return (price - discount.getDiscount(price)) * count;
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
