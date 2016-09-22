package com.luxoft.training.solid.store.persistenceservice.h2;

import com.luxoft.training.solid.store.persistence.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2CartsRepo implements CartsRepo {

    private final H2Connection connection;

    public H2CartsRepo(H2Connection connection) throws SQLException {
        this.connection = connection;

        String createCartTableSql = "CREATE TABLE IF NOT EXISTS CART"
                + "  (id           INTEGER auto_increment,"
                + "   delivery            BOOLEAN  DEFAULT FALSE,"
                + " PRIMARY KEY (id)"
                + ")";
        connection.executeSql(createCartTableSql);

        String createCartProductTableSql = "CREATE TABLE IF NOT EXISTS CART_PRODUCT"
                + "  (cart_id           INTEGER,"
                + "   name             VARCHAR(30),"
                + "   price            FLOAT,"
                + "   count          INTEGER,"
                + " PRIMARY KEY (cart_id,name)"
                + ")";
        connection.executeSql(createCartProductTableSql);
    }

    @Override
    public int createNewCart() {
        return connection.insertAndGetId("INSERT INTO CART VALUES ()");
    }

    @Override
    public CartData getCart(int cartId) {
        CartData cart = getCartWithoutProducts(cartId);
        List<ProductData> products = new ArrayList<>();
        String sql = "SELECT * FROM CART_PRODUCT WHERE cart_id =" + cartId;
        try (ResultSet resultSet = connection.executeQuery(sql)) {
            while (resultSet.next()) {
                products.add(loadProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return new CartData(cart.getId(), products, cart.hasDelivery());
    }

    @Override
    public void saveCart(CartData cartData) throws CartNotFoundException {
        getCartWithoutProducts(cartData.getId());
        updateCart(cartData);
    }

    private CartData getCartWithoutProducts(int cartId) {
        String sql = "SELECT * FROM CART WHERE id ='" + cartId + "'";
        try (ResultSet resultSet = connection.executeQuery(sql)) {
            if (resultSet.next()) {
                return new CartData(resultSet.getInt("id"), null, resultSet.getBoolean("delivery"));
            } else {
                throw new CartNotFoundException(cartId);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private void updateCart(CartData cartData) {
        cleanProducts(cartData);
        connection.executeSql("UPDATE CART SET delivery=" + cartData.hasDelivery() + " WHERE id=" + cartData.getId());
        insertCartProducts(cartData);
    }

    private void cleanProducts(CartData cartData) {
        connection.executeSql("DELETE FROM CART_PRODUCT WHERE cart_id=" + cartData.getId());
    }

    private void insertCartProducts(CartData cartData) {
        for (ProductData p : cartData.getProducts()) {
            insertProductToCart(cartData, p);
        }
    }

    private void insertProductToCart(CartData data, ProductData p) {
        connection.executeSql("INSERT INTO CART_PRODUCT VALUES (" + data.getId() + ", '" + p.getName() + "', " + p.getPrice() + ", " + p.getCount() + ")");
    }

    private ProductData loadProduct(ResultSet resultSet) throws SQLException {
        return new ProductData(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("count"));
    }
}
