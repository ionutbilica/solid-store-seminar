package com.luxoft.training.solid.store.discount;

import java.util.Collections;

public class NoDiscountsRepo extends DiscountsRepo {
    public NoDiscountsRepo() {
        super(Collections.<String, Discount>emptyMap());
    }
}
