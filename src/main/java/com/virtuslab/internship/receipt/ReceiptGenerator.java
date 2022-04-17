package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReceiptGenerator {

    public Receipt generate(Basket basket) {

        // count each product occurrences
        Map<Product, Long> occurrences = basket.getProducts().stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // create receiptEntry for each product
        List<ReceiptEntry> receiptEntries = new ArrayList<>(occurrences.keySet().stream()
                .map(key -> new ReceiptEntry(key, Math.toIntExact(occurrences.get(key))))
                .toList());

        return new Receipt(receiptEntries);
    }
}
