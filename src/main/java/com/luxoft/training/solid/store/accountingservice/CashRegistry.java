package com.luxoft.training.solid.store.accountingservice;

public class CashRegistry implements Account {

    private double total;

    public CashRegistry(double initialAmount) {
        this.total = initialAmount;
    }

    @Override
    public void receivePayment(double amount) {
        total += amount;
    }

    @Override
    public String getReport() {
        return "cash registry: " + total;
    }
}
