package com.luxoft.training.solid.store.accounting;

public class MockAccounting implements Accounting {

    private double cashTotal;

    @Override
    public void receivePayment(double amount, String pmt) {
        if (!"CASH".equals(pmt)) {
            throw new IllegalArgumentException("Unsupported payment method: " + pmt + ". Mock accounting only supports cash.");
        }
        cashTotal += amount;
    }

    @Override
    public String getReport() {
        return "cash registry: " + cashTotal;
    }
}
