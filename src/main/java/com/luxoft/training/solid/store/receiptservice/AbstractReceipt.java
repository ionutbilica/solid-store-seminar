package com.luxoft.training.solid.store.receiptservice;

import com.luxoft.training.solid.store.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReceipt implements Receipt {

    protected List<ProductReceiptLine> productLines;
    protected double deliveryCost;
    protected boolean hasDelivery;
    protected double totalPrice;

    public AbstractReceipt() {
        productLines = new ArrayList<>();
        hasDelivery = false;
    }

    @Override
    public void addProduct(String name, int count, double price, double priceForAll) {
        productLines.add(new ProductReceiptLine(name, count, price, priceForAll));
    }

    @Override
    public void addDelivery(double cost) {
        hasDelivery = true;
        deliveryCost = cost;
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class ProductReceiptLine {
        protected final String name;
        protected final int count;
        protected final double price;
        protected final double priceForAll;

        public ProductReceiptLine(String name, int count, double price, double priceForAll) {
            this.name = name;
            this.count = count;
            this.price = price;
            this.priceForAll = priceForAll;
        }
    }
}
