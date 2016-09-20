package com.luxoft.training.solid.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {

    public static final double DELIVERY_COST = 12;
    private static int receiptNo;

    private final int id;
    private List<Product> products;
    private boolean hasDelivery;

    public Cart(int id) {
        this.id = id;
        hasDelivery = false;
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addDelivery() {
        this.hasDelivery = true;
    }

    public double getTotalPrice() {
        double productsTotal = 0;
        for (Product p : products) {
            productsTotal += p.getFullPriceForAll();
        }
        double deliveryCost = hasDelivery ? DELIVERY_COST : 0;
        return productsTotal + deliveryCost;
    }

    public String getReceipt() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + ++receiptNo + "\n");
        for (Product p : products) {
            s.append(p.getName() + ": " + p.getCount() + " x " + p.getPrice() + " = " + p.getFullPriceForAll() + "\n");
        }
        if (hasDelivery) {
            s.append("Delivery: " + DELIVERY_COST + "\n");
        }
        s.append("Total: " + getTotalPrice() + "\n");
        s.append("Date: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "\n");
        return s.toString();
    }
}
