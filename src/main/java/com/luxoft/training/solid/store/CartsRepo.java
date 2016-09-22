package com.luxoft.training.solid.store;

import java.util.HashMap;
import java.util.Map;

public class CartsRepo {

    private final IdGenerator idGenerator;
    private final Map<Integer, Cart> carts;

    public CartsRepo(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        carts = new HashMap<>();
    }

    public int createNewCart() {
        int id = idGenerator.generateId();
        carts.put(id, new Cart(id));
        return id;
    }

    public Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new CartNotFoundException(cartId);
        }
        return cart;
    }
}
