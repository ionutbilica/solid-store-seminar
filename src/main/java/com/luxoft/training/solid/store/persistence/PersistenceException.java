package com.luxoft.training.solid.store.persistence;

public class PersistenceException extends RuntimeException {

    public PersistenceException(Exception e) {
        super(e);
    }

    public PersistenceException(String message) {
        super(message);
    }
}
