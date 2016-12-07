package com.luxoft.training.solid.store.persistenceservice.h2;

import com.luxoft.training.solid.store.persistence.NotEnoughInStockException;
import com.luxoft.training.solid.store.persistence.PersistenceException;
import com.luxoft.training.solid.store.persistence.ProductData;
import com.luxoft.training.solid.store.persistence.ProductNotFoundException;
import com.luxoft.training.solid.store.provisioning.ProductsRepo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class H2ProductsRepo implements ProductsRepo {

    private final H2Connection connection;

    public H2ProductsRepo(H2Connection connection) throws SQLException {
        this.connection = connection;

        String createProductTableSql = "CREATE TABLE IF NOT EXISTS PRODUCT"
                + "  (name           VARCHAR(30),"
                + "   price            FLOAT,"
                + "   count          INTEGER,"
                + " PRIMARY KEY (name)"
                + ")";
        connection.executeSql(createProductTableSql);
    }

    @Override
    public void addProduct(String name, double price, int count) {
        try {
            ProductData existingProduct = getProduct(name);
            updateProduct(new ProductData(name, price, existingProduct.getCount() + count));
        } catch (ProductNotFoundException e) {
            insertProduct(new ProductData(name, price, count));
        }
    }

    @Override
    public void removeProduct(String name, int countToRemove) throws ProductNotFoundException {
        takeProduct(name, countToRemove);
    }

    @Override
    public ProductData takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException {
        ProductData p = getProduct(name);
        removeProduct(p, count);
        return new ProductData(name, p.getPrice(), count);
    }

    private void removeProduct(ProductData p, int count) {
        int remainingCount = p.getCount() - count;
        if (remainingCount < 0) {
            throw new NotEnoughInStockException(p, count);
        }
        updateProduct(new ProductData(p.getName(), p.getPrice(), remainingCount));
    }

    private void insertProduct(ProductData productData) {
        connection.executeSql("INSERT INTO PRODUCT VALUES ('" + productData.getName() + "', " + productData.getPrice() + " , " + productData.getCount() + ")");
    }

    private void updateProduct(ProductData productData) {
        connection.executeSql("UPDATE PRODUCT SET price=" + productData.getPrice() + " , count=" + productData.getCount() + " WHERE name='" + productData.getName() + "'");
    }

    private ProductData getProduct(String name) {
        String sql = "SELECT * FROM PRODUCT WHERE name ='" + name + "'";
        try (ResultSet resultSet = connection.executeQuery(sql)) {
            if (resultSet.next()) {
                return loadProduct(resultSet);
            } else {
                throw new ProductNotFoundException(name);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private ProductData loadProduct(ResultSet resultSet) throws SQLException {
        return new ProductData(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("count"));
    }
}
