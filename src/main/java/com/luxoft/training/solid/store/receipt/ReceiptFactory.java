package com.luxoft.training.solid.store.receipt;

public interface ReceiptFactory {
    Receipt createReceipt(Format format);

    enum Format {TEXT, HTML}
}
