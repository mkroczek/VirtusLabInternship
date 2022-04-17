package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiptControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createReceiptWithoutDiscountsTest(){
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var apple = productDb.getProduct("Apple");
        var milk = productDb.getProduct("Milk");
        var cheese = productDb.getProduct("Cheese");
        var expectedTotalPrice = apple.price().add(milk.price()).add(cheese.price());

        cart.addProduct(apple);
        cart.addProduct(milk);
        cart.addProduct(cheese);

        // When
        ResponseEntity<Receipt> receiptEntity = restTemplate.postForEntity("/receipt", cart, Receipt.class);
        Receipt receipt = receiptEntity.getBody();

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());

    }
}
