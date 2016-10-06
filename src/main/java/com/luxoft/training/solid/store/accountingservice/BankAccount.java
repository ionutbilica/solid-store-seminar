package com.luxoft.training.solid.store.accountingservice;

public class BankAccount implements Account {

    private final String name;
    private double total;
    private final double interrogationFee;

    public BankAccount(String name, double initialAmount, double interrogationFee) {
        this.name = name;
        this.total = initialAmount;
        this.interrogationFee = interrogationFee;
    }

    @Override
    public void receivePayment(double amount) {
        total += amount;
    }

    @Override
    public String getReport() {
        total -= interrogationFee;
        return "bank account " + name + ": " + total;
    }
}
