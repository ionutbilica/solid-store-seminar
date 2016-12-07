package com.luxoft.training.solid.store.receiptservice;

import com.luxoft.training.solid.store.idgen.IdGenerator;
import com.luxoft.training.solid.store.receipt.Receipt;
import com.luxoft.training.solid.store.receipt.ReceiptFactory;

public class ConcreteReceiptFactory implements ReceiptFactory {

    private final IdGenerator receiptNoGenerator;
    private final Clock clock;

    public ConcreteReceiptFactory(IdGenerator receiptNoGenerator, Clock clock) {
        this.receiptNoGenerator = receiptNoGenerator;
        this.clock = clock;
    }

    @Override
    public Receipt createReceipt(String format) {
        switch (format) {
            case "TEXT": return new TextReceipt(receiptNoGenerator.generateId(), clock.getDate());
            case "HTML": return new HtmlReceipt(receiptNoGenerator.generateId(), clock.getDate());
            default: throw new UnknownReceiptFormat(format);
        }
    }

    private class UnknownReceiptFormat extends RuntimeException {
        public UnknownReceiptFormat(String format) {
            super("Unknown receiptservice format: " + format);
        }
    }
}
