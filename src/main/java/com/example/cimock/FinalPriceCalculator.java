package com.example.cimock;

import java.util.List;

public class FinalPriceCalculator {
    private final List<PriceRule> rules;

    public FinalPriceCalculator(List<PriceRule> rules) {
        this.rules = rules;
    }

    public double calculate(ShoppingCart cart) {
        double total = 0;
        for (PriceRule rule : rules) {
            total += rule.priceToAggregator(cart);
        }
        return total;
    }
}
