package com.example.cimock;

import lombok.Getter;

@Getter
public class Item {
    private ItemType type;
    private int quantity;

    public Item(ItemType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }
}
