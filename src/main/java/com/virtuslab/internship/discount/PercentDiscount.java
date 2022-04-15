package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;
import java.math.MathContext;

public abstract class PercentDiscount {
    protected final BigDecimal multiplier;
    protected final String NAME;

    public PercentDiscount(int percent, String discountName){
        multiplier = BigDecimal.valueOf(100-percent).divide(BigDecimal.valueOf(100), new MathContext(2));
        NAME = discountName;
    }

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(multiplier);
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    protected abstract boolean shouldApply(Receipt receipt);
}
