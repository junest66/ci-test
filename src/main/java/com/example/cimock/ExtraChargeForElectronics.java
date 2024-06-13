package com.example.cimock;

import java.util.List;

public class ExtraChargeForElectronics implements PriceRule{
    @Override
    public double priceToAggregator(ShoppingCart cart) {
        List<Item> items = cart.getItems();

        boolean hasAnElectronic = items
                .stream()
                .anyMatch(it -> it.getType() == ItemType.ELECTRONIC);

        if (hasAnElectronic) {
            return 7.50;
        }
        return 0;
    }
}
