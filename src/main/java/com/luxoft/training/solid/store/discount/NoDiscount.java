package com.luxoft.training.solid.store.discount;

public class NoDiscount implements Discount {

    @Override
    public double getDiscount(double price) {
        return 0;
    }
}
