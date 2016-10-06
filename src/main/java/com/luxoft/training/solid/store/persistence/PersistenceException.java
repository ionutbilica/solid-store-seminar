package com.luxoft.training.solid.store.persistence;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = -6644373882919399589L;

	public PersistenceException(Exception e) {
        super(e);
    }

    public PersistenceException(String message) {
        super(message);
    }
}
