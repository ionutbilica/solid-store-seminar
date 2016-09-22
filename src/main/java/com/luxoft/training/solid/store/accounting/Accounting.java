package com.luxoft.training.solid.store.accounting;

public interface Accounting {

    void receivePayment(double amount, PaymentMethodType pmt);

    String getReport();
}
