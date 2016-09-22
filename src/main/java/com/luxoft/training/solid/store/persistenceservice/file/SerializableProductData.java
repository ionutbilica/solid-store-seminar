package com.luxoft.training.solid.store.persistenceservice.file;

import com.luxoft.training.solid.store.persistence.ProductData;

import java.io.Serializable;

class SerializableProductData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final double price;
    private final int count;

    public SerializableProductData(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public ProductData asProductData() {
        return new ProductData(name, price, count);
    }
}
