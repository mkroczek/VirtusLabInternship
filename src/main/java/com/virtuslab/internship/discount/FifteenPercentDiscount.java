package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

public class FifteenPercentDiscount extends PercentDiscount{

    public FifteenPercentDiscount() {
        super(15, "FifteenPercentDiscount");
    }

    protected boolean shouldApply(Receipt receipt) {
        int grainCount = receipt.entries().stream()
                .filter(receiptEntry -> receiptEntry.product().type() == Product.Type.GRAINS)
                .map(ReceiptEntry::quantity)
                .reduce(0, Integer::sum);

        return grainCount >= 3;
    }
}
