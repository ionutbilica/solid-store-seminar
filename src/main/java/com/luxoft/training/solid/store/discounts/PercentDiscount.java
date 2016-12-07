package com.luxoft.training.solid.store.discounts;

import com.luxoft.training.solid.store.discount.Discount;

public class PercentDiscount implements Discount {

    private final int percent;

    public PercentDiscount(int percent) {
        this.percent = percent;
    }

    @Override
    public double getDiscount(double price) {
        return price/100*percent;
    }
}
