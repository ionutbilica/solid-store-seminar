package com.luxoft.training.solid.store.accountingservice;

public class CreditCardPaymentMethod implements PaymentMethod {

    private final Account account;
    private final double fee;

    public CreditCardPaymentMethod(Account account, double fee) {
        this.account = account;
        this.fee = fee;
    }

    @Override
    public void receivePayment(double amount) {
        double amountMinusFee = amount - fee;
        account.receivePayment(amountMinusFee);
    }
}
