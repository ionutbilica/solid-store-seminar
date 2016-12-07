package com.luxoft.training.solid.store.integration.persistence;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.idgen.MockIdGenerator;
import com.luxoft.training.solid.store.persistence.*;
import com.luxoft.training.solid.store.persistenceservice.file.FileCartsRepo;
import com.luxoft.training.solid.store.persistenceservice.file.FileProductsRepo;
import com.luxoft.training.solid.store.provisioning.ProductsRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class FilePersistenceTest {

    @After
    public void clean() {
        new File("products.repo").delete();
        new File("carts.repo").delete();
    }

    @Test
    public void testOneProduct() throws Exception {
        ProductsRepo productsRepo = new FileProductsRepo();
        CartsRepo cartsRepo = new FileCartsRepo(new MockIdGenerator(1));

        productsRepo.addProduct("wine", 100, 25);
        Product wineInCart = new Product(productsRepo.takeProduct("wine", 10));

        int newCartId = cartsRepo.createNewCart();
        Cart c = new Cart(newCartId);
        c.addDelivery();
        c.addProduct(wineInCart);
        cartsRepo.saveCart(c.getData());

        cartsRepo = new FileCartsRepo(new MockIdGenerator(1));
        CartData cartData = cartsRepo.getCart(newCartId);

        Assert.assertTrue(cartData.hasDelivery());
        Assert.assertEquals(cartData.getProducts(), Arrays.asList(new ProductData("wine", 100, 10)));
    }

    @Test(expected = NotEnoughInStockException.class)
    public void testNotEnoughInStock() throws Exception {
        ProductsRepo productsRepo = new FileProductsRepo();

        productsRepo.addProduct("wine", 100, 25);
        productsRepo.takeProduct("wine", 200);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testProductNotFound() throws Exception {
        ProductsRepo productsRepo = new FileProductsRepo();

        productsRepo.addProduct("wine", 100, 25);
        productsRepo.takeProduct("bread", 200);
    }
}
