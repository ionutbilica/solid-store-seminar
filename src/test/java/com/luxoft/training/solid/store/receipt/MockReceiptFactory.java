package com.luxoft.training.solid.store.receipt;

public class MockReceiptFactory implements ReceiptFactory {
    @Override
    public Receipt createReceipt(String format) {
        return new MockReceipt();
    }
}
