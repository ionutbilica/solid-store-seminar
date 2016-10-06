package com.luxoft.training.solid.store.discounts;

import com.luxoft.training.solid.store.discount.Discount;

public class FixDiscount implements Discount {

    private final double discount;

    public FixDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public double getDiscount(double price) {
        return discount;
    }
}
