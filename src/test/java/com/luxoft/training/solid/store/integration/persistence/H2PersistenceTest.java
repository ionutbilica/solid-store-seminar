package com.luxoft.training.solid.store.integration.persistence;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.discount.NoDiscount;
import com.luxoft.training.solid.store.persistence.CartData;
import com.luxoft.training.solid.store.persistence.NotEnoughInStockException;
import com.luxoft.training.solid.store.persistence.ProductData;
import com.luxoft.training.solid.store.persistence.ProductNotFoundException;
import com.luxoft.training.solid.store.persistenceservice.h2.H2CartsRepo;
import com.luxoft.training.solid.store.persistenceservice.h2.H2Connection;
import com.luxoft.training.solid.store.persistenceservice.h2.H2ProductsRepo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class H2PersistenceTest {

    @Test
    public void testOneProduct() throws Exception {
        try (H2Connection connection = new H2Connection()) {
            H2ProductsRepo productsRepo = new H2ProductsRepo(connection);
            H2CartsRepo cartsRepo = new H2CartsRepo(connection);

            productsRepo.addProduct("wine", 100, 25);
            Product wineInCart = new Product(productsRepo.takeProduct("wine", 10), new NoDiscount());

            int newCartId = cartsRepo.createNewCart();
            Cart c = new Cart(newCartId);
            c.addDelivery();
            c.addProduct(wineInCart);
            cartsRepo.saveCart(c.getData());

            CartData cartData = cartsRepo.getCart(newCartId);

            Assert.assertTrue(cartData.hasDelivery());
            Assert.assertEquals(cartData.getProducts(), Arrays.asList(new ProductData("wine", 100, 10)));
        }
    }

    @Test(expected = NotEnoughInStockException.class)
    public void testNotEnoughInStock() throws Exception {
        try (H2Connection connection = new H2Connection()) {
            H2ProductsRepo productsRepo = new H2ProductsRepo(connection);

            productsRepo.addProduct("wine", 100, 25);
            productsRepo.takeProduct("wine", 200);
        }
    }

    @Test(expected = ProductNotFoundException.class)
    public void testProductNotFound() throws Exception {
        try (H2Connection connection = new H2Connection()) {
            H2ProductsRepo productsRepo = new H2ProductsRepo(connection);

            productsRepo.addProduct("wine", 100, 25);
            productsRepo.takeProduct("bread", 200);
        }
    }
}
