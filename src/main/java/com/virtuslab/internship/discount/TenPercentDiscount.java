package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount extends PercentDiscount{

    public TenPercentDiscount() {
        super(10, "TenPercentDiscount");
    }

    protected boolean shouldApply(Receipt receipt) {
        return !(receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) < 0);
    }
}
