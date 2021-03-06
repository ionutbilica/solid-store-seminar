package com.luxoft.training.solid.store.accountingservice;

import com.luxoft.training.solid.store.accounting.Accounting;
import com.luxoft.training.solid.store.accounting.PaymentMethodType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AccountingTest {

    public static final int INITIAL_AMOUNT = 10;
    public static final double CC_INTERROGATION_FEE = 0.1;
    public static final double BT_INTERROGATION_FEE = 0.2;
    public static final int CC_PAYMENT_FEE = 1;
    public static final int BT_PAYMENT_FEE = 2;
    public static final int CASH_PAYMENT = 25;
    public static final int CC_PAYMENT = 35;
    public static final int BT_PAYMENT = 45;
    public static final String CC_ACCOUNT_NAME = "for credit card";
    public static final String BT_ACCOUNT_NAME = "for bank transfer";

    @Test
    public void test() {
        Accounting accounting = createAccountingService();

        accounting.receivePayment(CASH_PAYMENT, PaymentMethodType.CASH);
        accounting.receivePayment(CC_PAYMENT, PaymentMethodType.CREDIT_CARD);
        accounting.receivePayment(BT_PAYMENT, PaymentMethodType.BANK_TRANSFER);

        double expectedCashTotal = INITIAL_AMOUNT + CASH_PAYMENT;
        double expectedCCTotal = INITIAL_AMOUNT + CC_PAYMENT - CC_PAYMENT_FEE - CC_INTERROGATION_FEE;
        double expectedBTTotal = INITIAL_AMOUNT + BT_PAYMENT - BT_PAYMENT_FEE - BT_INTERROGATION_FEE;
        String expectedReport = "cash registry: " + expectedCashTotal
                + " , bank account " + CC_ACCOUNT_NAME + ": " + expectedCCTotal
                + " , bank account " + BT_ACCOUNT_NAME + ": " + expectedBTTotal;

        Assert.assertEquals("Report incorrect.", expectedReport, accounting.getReport());
    }

    private Accounting createAccountingService() {
        CashRegistry cashRegistry = new CashRegistry(INITIAL_AMOUNT);
        BankAccount ccBankAccount = new BankAccount(CC_ACCOUNT_NAME, INITIAL_AMOUNT, CC_INTERROGATION_FEE);
        BankAccount btBankAccount = new BankAccount(BT_ACCOUNT_NAME, INITIAL_AMOUNT, BT_INTERROGATION_FEE);
        Set<Account> accounts = new LinkedHashSet<>(Arrays.asList(cashRegistry, ccBankAccount, btBankAccount));

        Map<PaymentMethodType, PaymentMethod> paymentMethods = new HashMap<>();
        CashPaymentMethod cashPaymentMethod = new CashPaymentMethod(cashRegistry);
        paymentMethods.put(PaymentMethodType.CASH, cashPaymentMethod);
        CreditCardPaymentMethod creditCardPaymentMethod = new CreditCardPaymentMethod(ccBankAccount, CC_PAYMENT_FEE);
        paymentMethods.put(PaymentMethodType.CREDIT_CARD, creditCardPaymentMethod);
        BankTransferPaymentMethod bankTransferPaymentMethod = new BankTransferPaymentMethod(btBankAccount, BT_PAYMENT_FEE);
        paymentMethods.put(PaymentMethodType.BANK_TRANSFER, bankTransferPaymentMethod);

        return new AccountingService(paymentMethods, accounts);
    }
}
