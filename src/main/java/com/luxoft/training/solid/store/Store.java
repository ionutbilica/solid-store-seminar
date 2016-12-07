package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.accounting.Accounting;
import com.luxoft.training.solid.store.persistence.CartData;
import com.luxoft.training.solid.store.persistence.CartsRepo;
import com.luxoft.training.solid.store.persistence.ProductData;
import com.luxoft.training.solid.store.persistence.Stock;
import com.luxoft.training.solid.store.receipt.Receipt;
import com.luxoft.training.solid.store.receipt.ReceiptFactory;

public class Store implements Sales {

    private final Stock stock;
    private final CartsRepo cartsRepo;
    private final ReceiptFactory receiptFactory;
    private final Accounting accounting;

    public Store(Stock stock, CartsRepo cartsRepo, ReceiptFactory receiptFactory, Accounting accounting) {
        this.stock = stock;
        this.cartsRepo = cartsRepo;
        this.receiptFactory = receiptFactory;
        this.accounting = accounting;
    }

    @Override
    public int createNewCart() {
        return cartsRepo.createNewCart();
    }

    @Override
    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    @Override
    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = new Product(stock.takeProduct(name, count));
        cart.addProduct(product);
        cartsRepo.saveCart(cart.getData());
    }

    private Cart getCart(int cartId) {
        CartData data = cartsRepo.getCart(cartId);
        Cart cart = new Cart(data.getId());
        if (data.hasDelivery()) {
            cart.addDelivery();
        }
        for (ProductData pd : data.getProducts()) {
            cart.addProduct(new Product(pd));
        }
        return cart;
    }

    @Override
    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    @Override
    public void addDeliveryToCart(int cartId) {
        Cart cart = getCart(cartId);
        cart.addDelivery();
        cartsRepo.saveCart(cart.getData());
    }

    @Override
    public String pay(int cartId, String paymentMethod, String receiptFormat) {
        Cart cart = getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        accounting.receivePayment(moneyFromTheClient, paymentMethod);
        Receipt receipt = receiptFactory.createReceipt(receiptFormat);
        return cart.fillReceipt(receipt);
    }

    @Override
    public String getAccountingReport() {
        return accounting.getReport();
    }
}
