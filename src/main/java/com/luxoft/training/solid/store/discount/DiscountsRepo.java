package com.luxoft.training.solid.store.discount;

import java.util.Map;

public class DiscountsRepo {

    private final Map<String, Discount> discounts;

    public DiscountsRepo(Map<String, Discount> discounts) {
        this.discounts = discounts;
    }

    public Discount getDiscount(String productName) {
        Discount discount = discounts.get(productName);
        if (discount == null) {
            return new NoDiscount();
        }
        return discount;
    }
}
