package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    private final ReceiptGenerator generator;

    public ReceiptController(ReceiptGenerator generator){
        this.generator = generator;
    }

    @PostMapping
    public Receipt createReceipt(@RequestBody Basket basket){
        return generator.generate(basket);
    }
}
