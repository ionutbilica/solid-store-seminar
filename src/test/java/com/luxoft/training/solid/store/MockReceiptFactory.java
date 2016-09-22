package com.luxoft.training.solid.store;

public class MockReceiptFactory implements ReceiptFactory {
    @Override
    public Receipt createReceipt(Format format) {
        return new MockReceipt();
    }
}
