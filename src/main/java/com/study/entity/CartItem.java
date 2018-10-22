package com.study.entity;

import java.time.LocalDateTime;

public class CartItem {

    private Product product;
    private int number;
    private LocalDateTime created;

    public CartItem(Product product, int number) {
        this.product = product;
        this.number = number;
        this.created = LocalDateTime.now();
    }

    public Product getProduct() {
        return product;
    }

    public int getNumber() {
        return number;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void incNumber() {
        this.number += 1;
    }
}
