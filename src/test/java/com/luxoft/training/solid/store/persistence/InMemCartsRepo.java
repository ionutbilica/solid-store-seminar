package com.luxoft.training.solid.store.persistence;

import com.luxoft.training.solid.store.idgen.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class InMemCartsRepo implements CartsRepo {

    private final IdGenerator idGenerator;
    private final Map<Integer, CartData> carts;

    public InMemCartsRepo(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        carts = new HashMap<>();
    }

    @Override
    public int createNewCart() {
        int id = idGenerator.generateId();
        carts.put(id, new CartData(id));
        return id;
    }

    @Override
    public CartData getCart(int cartId) {
        CartData cartData = carts.get(cartId);
        if (cartData == null) {
            throw new CartNotFoundException(cartId);
        }
        return cartData;
    }

    @Override
    public void saveCart(CartData cartData) {
        carts.put(cartData.getId(), cartData);
    }
}
