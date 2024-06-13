package com.example.cimock;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(ItemType type, double price, int quantity) {
        items.add(new Item(type, price, quantity));
    }

    public int numberOfItems() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    public List<Item> getItems() {
        return items;
    }
}

