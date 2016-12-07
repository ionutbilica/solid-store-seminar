package com.luxoft.training.solid.store.persistenceservice.file;

import com.luxoft.training.solid.store.persistence.NotEnoughInStockException;
import com.luxoft.training.solid.store.persistence.ProductData;
import com.luxoft.training.solid.store.persistence.ProductNotFoundException;
import com.luxoft.training.solid.store.provisioning.ProductsRepo;

import java.io.File;
import java.util.Map;

public class FileProductsRepo implements ProductsRepo {

    private static final File REPO_FILE = new File("products.repo");

    private final Map<String, SerializableProductData> productDataMap;

    public FileProductsRepo() {
        productDataMap = new FileBackedMap<>(REPO_FILE);
    }

    @Override
    public void addProduct(String name, double price, int count) {
        SerializableProductData pd = productDataMap.get(name);
        if (pd == null) {
            pd = new SerializableProductData(name, price, count);
        } else {
            pd = new SerializableProductData(pd.getName(), pd.getPrice(), pd.getCount() + count);
        }
        productDataMap.put(name, pd);
    }

    @Override
    public void removeProduct(String name, int countToRemove) throws ProductNotFoundException {
        SerializableProductData pd = productDataMap.get(name);
        removeProduct(name, pd, countToRemove);
    }

    private void removeProduct(String name, SerializableProductData pd, int countToRemove) {
        if (pd == null) {
            throw new ProductNotFoundException(name);
        }
        int remaining = pd.getCount() - countToRemove;
        if (remaining < 0) {
            throw new NotEnoughInStockException(pd.asProductData(), countToRemove);
        }
        SerializableProductData pdRemaining = new SerializableProductData(pd.getName(), pd.getPrice(), remaining);
        productDataMap.put(pd.getName(), pdRemaining);
    }

    @Override
    public ProductData takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException {
        SerializableProductData pd = productDataMap.get(name);
        removeProduct(name, pd, count);
        return new ProductData(name, pd.getPrice(), count);
    }
}
