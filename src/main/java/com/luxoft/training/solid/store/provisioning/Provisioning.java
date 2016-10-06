package com.luxoft.training.solid.store.provisioning;

import com.luxoft.training.solid.store.persistence.ProductNotFoundException;

public interface Provisioning {

    void addProduct(String name, double price, int count);
    void removeProduct(String name, int countToRemove) throws ProductNotFoundException;
}
