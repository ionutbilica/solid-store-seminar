package com.luxoft.training.solid.store.discountintegration;

import com.luxoft.training.solid.store.Store;
import com.luxoft.training.solid.store.accounting.MockAccounting;
import com.luxoft.training.solid.store.discount.Discount;
import com.luxoft.training.solid.store.discount.DiscountsRepo;
import com.luxoft.training.solid.store.discounts.CompoundDiscount;
import com.luxoft.training.solid.store.discounts.FixDiscount;
import com.luxoft.training.solid.store.discounts.PercentDiscount;
import com.luxoft.training.solid.store.idgen.MockIdGenerator;
import com.luxoft.training.solid.store.persistence.InMemCartsRepo;
import com.luxoft.training.solid.store.persistence.TestStock;
import com.luxoft.training.solid.store.receipt.MockReceiptFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.training.solid.store.persistence.TestStock.BREAD_NAME;
import static com.luxoft.training.solid.store.persistence.TestStock.BREAD_PRICE;
import static org.junit.Assert.assertEquals;

public class DiscountsTest {

    private Store store;
    private int cartId;

    @Test
    public void testFixDiscount() {
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put(BREAD_NAME, new FixDiscount(2));
        store = new Store(new TestStock(), new DiscountsRepo(discounts), new InMemCartsRepo(new MockIdGenerator(1)), new MockReceiptFactory(), new MockAccounting());
        cartId = store.createNewCart();
        store.addProductToCart(BREAD_NAME, cartId);
        assertEquals("Incorrect price.", BREAD_PRICE-2, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testPercentDiscount() {
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put(BREAD_NAME, new PercentDiscount(20));
        store = new Store(new TestStock(), new DiscountsRepo(discounts), new InMemCartsRepo(new MockIdGenerator(1)), new MockReceiptFactory(), new MockAccounting());
        cartId = store.createNewCart();
        store.addProductToCart(BREAD_NAME, cartId);
        assertEquals("Incorrect price.", BREAD_PRICE-(BREAD_PRICE*20/100), store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testPercentFixDiscount() {
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put(BREAD_NAME, new CompoundDiscount(new PercentDiscount(20), new FixDiscount(2)));
        store = new Store(new TestStock(), new DiscountsRepo(discounts), new InMemCartsRepo(new MockIdGenerator(1)), new MockReceiptFactory(), new MockAccounting());
        cartId = store.createNewCart();
        store.addProductToCart(BREAD_NAME, cartId);
        assertEquals("Incorrect price.", BREAD_PRICE-(BREAD_PRICE*20/100)-2, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testFixPercentDiscount() {
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put(BREAD_NAME, new CompoundDiscount(new FixDiscount(1), new PercentDiscount(50)));
        store = new Store(new TestStock(), new DiscountsRepo(discounts), new InMemCartsRepo(new MockIdGenerator(1)), new MockReceiptFactory(), new MockAccounting());
        cartId = store.createNewCart();
        store.addProductToCart(BREAD_NAME, cartId);
        assertEquals("Incorrect price.", BREAD_PRICE-1-((BREAD_PRICE-1)*50/100), store.getCartTotal(cartId), 0.1);
    }
}
