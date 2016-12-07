package com.luxoft.training.solid.store.accountingservice;

public class BankTransferPaymentMethod implements PaymentMethod {

    private final Account account;
    private final double fee;

    public BankTransferPaymentMethod(Account account, double fee) {
        this.account = account;
        this.fee = fee;
    }

    @Override
    public void receivePayment(double amount) {
        double amountMinusFee = amount - fee;
        account.receivePayment(amountMinusFee);
    }
}
