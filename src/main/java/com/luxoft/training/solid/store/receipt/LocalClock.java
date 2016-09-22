package com.luxoft.training.solid.store.receipt;

import java.util.Date;

public class LocalClock implements Clock {

    @Override
    public Date getDate() {
        return new Date();
    }
}
