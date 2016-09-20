package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static com.luxoft.training.solid.store.TestStock.*;
import static org.junit.Assert.assertEquals;

public class CartTest {

    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store();
        new TestStock().insertIntoStore(store);
        cartId = store.createNewCart();
    }

    @Test
    public void testEmptyCart() throws Exception {
        assertEquals("Cart total should be the price of the one product.", 0, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testSingleProduct() {
        store.addProductToCart(BREAD_NAME, cartId);
        assertEquals("Cart total should be 0.", BREAD_PRICE, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testTwoProduct() {
        store.addProductToCart(BREAD_NAME, cartId);
        store.addProductToCart(WINE_NAME, cartId);
        assertEquals("Cart total should be the sum of both products.", BREAD_PRICE + WINE_PRICE, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testTwoOfOneAndAnotherProduct() {
        store.addProductToCart(BREAD_NAME, 2, cartId);
        store.addProductToCart(WINE_NAME, cartId);
        assertEquals("Cart total should be the sum of both products.", 2 * BREAD_PRICE + WINE_PRICE, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testWithDelivery() {
        store.addProductToCart(BREAD_NAME, cartId);
        store.addProductToCart(WINE_NAME, cartId);
        store.addDeliveryToCart(cartId);
        assertEquals("Cart total should be the sum of both products plus delivery.", BREAD_PRICE + WINE_PRICE + 12, store.getCartTotal(cartId), 0.1);
    }

    @Test(expected = CartNotFoundException.class)
    public void testNonexistentCart() throws Exception {
        store.addProductToCart("Bread", 777);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testNonexistentProduct() throws Exception {
        store.addProductToCart("NotFound", cartId);
    }
}
