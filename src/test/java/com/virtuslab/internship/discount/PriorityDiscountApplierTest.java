package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityDiscountApplierTest {
    PriorityDiscountApplier applier;

    @BeforeEach
    void initializeApplier(){
        applier = new PriorityDiscountApplier();
        applier.addDiscount(new TenPercentDiscount(), 2);
        applier.addDiscount(new FifteenPercentDiscount(), 1);
    }

    @Test
    void only15PercentDiscountShouldApply() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = applier.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void only10PercentDiscountShouldApply() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price().add(steak.price())
                .multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = applier.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void bothShouldApplyWhenPriceIsAbove59() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = steak.price().add(bread.price().multiply(BigDecimal.valueOf(3)))
                .multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = applier.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void only15ShouldApplyWhenPriceIsBetween50And59() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(2))
                .add(bread.price().multiply(BigDecimal.valueOf(3)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = applier.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
}
