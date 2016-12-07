package com.luxoft.training.solid.store.accounting;

public interface Accounting {

    void receivePayment(double amount, String paymentMethodType);

    String getReport();
}
