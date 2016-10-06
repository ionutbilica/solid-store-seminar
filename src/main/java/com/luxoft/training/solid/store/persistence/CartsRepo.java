package com.luxoft.training.solid.store.persistence;

public interface CartsRepo {
    int createNewCart();

    CartData getCart(int cartId);

    void saveCart(CartData cart);
}
