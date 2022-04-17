package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.util.*;

/**
 * Class serves as discount's container. Discounts have priorities, that allow to apply them in given order.
 */

public class PriorityDiscountApplier {
    private Map<PercentDiscount, Integer> availableDiscounts = new HashMap<>();

    public void addDiscount(PercentDiscount discount, int priority){
        availableDiscounts.put(discount, priority);
    }

    private List<PercentDiscount> getSortedByPriority(){
        return availableDiscounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();
    }

    public Receipt apply(Receipt receipt){
        // Sort discounts according to the priority
        List<PercentDiscount> discountsInOrder = getSortedByPriority();

        for (PercentDiscount discount : discountsInOrder){
            receipt = discount.apply(receipt);
        }

        return receipt;
    }
}
