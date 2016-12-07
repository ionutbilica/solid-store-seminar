package com.luxoft.training.solid.store.accountingservice;

import com.luxoft.training.solid.store.accounting.Accounting;

import java.util.Map;
import java.util.Set;

public class AccountingService implements Accounting {

    private final Map<PaymentMethodType, PaymentMethod> paymentMethods;
    private final Set<Account> accounts;

    public AccountingService(Map<PaymentMethodType, PaymentMethod> paymentMethods, Set<Account> accounts) {
        this.paymentMethods = paymentMethods;
        this.accounts = accounts;
    }

    @Override
    public void receivePayment(double amount, String paymentMethodType) {
        PaymentMethod paymentMethod = paymentMethods.get(PaymentMethodType.valueOf(paymentMethodType));
        paymentMethod.receivePayment(amount);
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        boolean first = true;
        for (Account a : accounts) {
            if (first) {
                first = false;
            } else {
                report.append(" , ");
            }
            report.append(a.getReport());
        }
        return report.toString();
    }
}
