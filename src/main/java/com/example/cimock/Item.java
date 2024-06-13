package com.example.cimock;

import lombok.Getter;

@Getter
public class Item {
    private ItemType type;
    private double price;
    private int quantity;

    public Item(ItemType type, double price, int quantity) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }
}
