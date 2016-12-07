package com.luxoft.training.solid.store.accountingservice;

public class CashPaymentMethod implements PaymentMethod {

    private final Account account;

    public CashPaymentMethod(Account account) {
        this.account = account;
    }

    @Override
    public void receivePayment(double amount) {
        account.receivePayment(amount);
    }
}
