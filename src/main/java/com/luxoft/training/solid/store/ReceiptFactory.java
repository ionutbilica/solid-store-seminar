package com.luxoft.training.solid.store;

public interface ReceiptFactory {
    Receipt createReceipt(Format format);

    enum Format {TEXT, HTML}
}
