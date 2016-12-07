package com.luxoft.training.solid.store.discounts;

import com.luxoft.training.solid.store.discount.Discount;

public class CompoundDiscount implements Discount {

    private final Discount[] discounts;

    public CompoundDiscount(Discount... discounts) {
        this.discounts = discounts;
    }

    @Override
    public double getDiscount(double price) {
        double compoundedDiscount = 0;
        for (Discount d : discounts) {
            double currentDiscount = d.getDiscount(price - compoundedDiscount);
            compoundedDiscount += currentDiscount;
        }
        return compoundedDiscount;
    }
}
