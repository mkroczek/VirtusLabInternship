package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.PriorityDiscountApplier;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    private final ReceiptGenerator generator;
    private final PriorityDiscountApplier applier;

    public ReceiptController(ReceiptGenerator generator){
        this.generator = generator;
        this.applier = new PriorityDiscountApplier();
        applier.addDiscount(new FifteenPercentDiscount(), 1);
        applier.addDiscount(new TenPercentDiscount(), 2);
    }

    @PostMapping
    public Receipt createReceipt(@RequestBody Basket basket){
        Receipt receiptWithoutDiscounts = generator.generate(basket);
        return applier.apply(receiptWithoutDiscounts);
    }
}
