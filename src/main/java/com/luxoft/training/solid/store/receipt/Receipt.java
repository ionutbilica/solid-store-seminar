package com.luxoft.training.solid.store.receipt;

public interface Receipt {

    void addProduct(String name, int count, double price, double priceForAll);

    void addDelivery(double cost);

    void setTotalPrice(double totalPrice);

    String toString();
}
