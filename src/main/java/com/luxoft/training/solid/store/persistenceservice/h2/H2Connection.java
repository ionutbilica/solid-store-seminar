package com.luxoft.training.solid.store.persistenceservice.h2;

import com.luxoft.training.solid.store.persistence.PersistenceException;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class H2Connection implements Closeable {

    private final Connection connection;

    public H2Connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "");
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void executeSql(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            return connection.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public int insertAndGetId(String sqlQuery) {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            return getKey(statement);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private int getKey(PreparedStatement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new PersistenceException("Insert failed!");
        }
    }
}
