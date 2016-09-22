package com.luxoft.training.solid.store.provisioning;

public interface Provisioning {

    void addProduct(String name, double price, int count);
    void removeProduct(String name, int countToRemove);
}
