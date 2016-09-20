package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.NotEnoughInStockException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private final Map<String, Product> stock;
    private double cash;
    private final Map<Integer, Cart> carts;
    private int latestCartId;

    public Store() {
        this.stock = new HashMap<>();
        cash = 0;
        carts = new HashMap<Integer, Cart>();
        latestCartId = 0;
    }

    public void addProductToStock(String name, double price, int count) {
        Product p;
        try {
            p = findProductInStock(name);
            p = new Product(name, price, p.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new Product(name, price, count);
        }
        stock.put(name, p);
    }

    private Product findProductInStock(String name) {
        Product productInStock = stock.get(name);
        if (productInStock == null) {
            throw new ProductNotFoundException(name);
        }
        return productInStock;
    }

    public void removeProductFromStock(String name, int countToRemove) {
        Product p = findProductInStock(name);
        removeProductFromStock(p, countToRemove);
    }

    private void removeProductFromStock(Product p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new Product(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        stock.put(p.getName(), p);
    }

    public int createNewCart() {
        latestCartId++;
        carts.put(latestCartId, new Cart(latestCartId));
        return latestCartId;
    }

    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = takeFromStock(name, count);
        cart.addProduct(product);
    }

    private Product takeFromStock(String name, int count) {
        Product productInStock = findProductInStock(name);
        removeProductFromStock(productInStock, count);
        return new Product(productInStock.getName(), productInStock.getPrice(), count);
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    public void addDeliveryToCart(int cartId) {
        Cart cart = getCart(cartId);
        cart.addDelivery();
    }

    public String pay(int cartId) {
        Cart cart = getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        cash += moneyFromTheClient;
        return cart.getReceipt();
    }

    public double getCashAmount() {
        return cash;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }
}
