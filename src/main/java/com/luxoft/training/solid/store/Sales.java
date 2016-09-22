package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.persistence.CartNotFoundException;
import com.luxoft.training.solid.store.persistence.NotEnoughInStockException;
import com.luxoft.training.solid.store.persistence.ProductNotFoundException;

public interface Sales {

    int createNewCart();

    void addProductToCart(String name, int cartId) throws ProductNotFoundException, CartNotFoundException, NotEnoughInStockException;

    void addProductToCart(String name, int count, int cartId) throws ProductNotFoundException, CartNotFoundException, NotEnoughInStockException;

    double getCartTotal(int cartId) throws CartNotFoundException;

    void addDeliveryToCart(int cartId) throws CartNotFoundException;

    String pay(int cartId, String paymentMethodName, String formatName) throws CartNotFoundException;

    String getAccountingReport();
}
