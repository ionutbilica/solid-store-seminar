package com.luxoft.training.solid.store.receiptservice;

import java.util.Date;

public class LocalClock implements Clock {

    @Override
    public Date getDate() {
        return new Date();
    }
}
