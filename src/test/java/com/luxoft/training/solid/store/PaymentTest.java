package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.accounting.MockAccounting;
import com.luxoft.training.solid.store.discount.NoDiscountsRepo;
import com.luxoft.training.solid.store.idgen.MockIdGenerator;
import com.luxoft.training.solid.store.persistence.InMemCartsRepo;
import com.luxoft.training.solid.store.persistence.TestStock;
import com.luxoft.training.solid.store.receipt.MockReceiptFactory;
import org.junit.Before;
import org.junit.Test;

import static com.luxoft.training.solid.store.persistence.TestStock.*;
import static org.junit.Assert.assertEquals;


public class PaymentTest {

    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store(new TestStock(), new NoDiscountsRepo(), new InMemCartsRepo(new MockIdGenerator(1)), new MockReceiptFactory(), new MockAccounting());
        cartId = store.createNewCart();
    }

    @Test
    public void testPayment() throws Exception {
        store.addProductToCart(BREAD_NAME, cartId);
        store.addProductToCart(WINE_NAME, cartId);
        store.pay(cartId, "CASH", "TEXT");
        assertEquals("Accounting report incorrect.", "cash registry: " + (BREAD_PRICE + WINE_PRICE), store.getAccountingReport());
    }
}
