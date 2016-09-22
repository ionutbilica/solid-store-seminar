package com.luxoft.training.solid.store.persistence;

import java.util.Collections;
import java.util.List;

public class CartData {

    private final int id;
    private final List<ProductData> products;
    private final boolean hasDelivery;

    public CartData(int id, List<ProductData> products, boolean hasDelivery) {
        this.id = id;
        this.products = products;
        this.hasDelivery = hasDelivery;
    }

    public CartData(int id) {
        this(id, Collections.<ProductData>emptyList(), false);
    }

    public int getId() {
        return id;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public boolean hasDelivery() {
        return hasDelivery;
    }

    @Override
    public String toString() {
        return "CartData{" +
                "id=" + id +
                ", products=" + products +
                ", hasDelivery=" + hasDelivery +
                '}';
    }
}
