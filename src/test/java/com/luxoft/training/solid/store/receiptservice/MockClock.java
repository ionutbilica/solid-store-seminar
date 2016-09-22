package com.luxoft.training.solid.store.receiptservice;

import java.util.Date;

public class MockClock implements Clock {

    private final Date fixedDate;

    public MockClock(Date fixedDate) {
        this.fixedDate = fixedDate;
    }

    @Override
    public Date getDate() {
        return fixedDate;
    }
}
