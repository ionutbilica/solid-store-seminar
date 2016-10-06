package com.luxoft.training.solid.store.accountingservice;

public interface Account {
    void receivePayment(double amount);
    String getReport();
}
