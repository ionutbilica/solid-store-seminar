package com.luxoft.training.solid.store.receiptservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlReceipt extends AbstractReceipt {

    private final int no;
    private final Date date;

    public HtmlReceipt(int no, Date date) {
        this.no = no;
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + no + "\n");

        appendProducts(s);

        if (hasDelivery) {
            s.append("<div><b>Delivery</b>: " + deliveryCost + "</div>\n");
        }
        s.append("<div><b>Total</b>: " + totalPrice + "</div>\n");
        s.append("<div><b>Date</b>: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(date) + "</div>\n");
        return s.toString();
    }

    private void appendProducts(StringBuilder s) {
        for (ProductReceiptLine p : productLines) {
            s.append("<div><b>" + p.name + "</b>: " + p.count + " x " + p.price + " = " + p.priceForAll + "</div>" + "\n");
        }
    }
}
