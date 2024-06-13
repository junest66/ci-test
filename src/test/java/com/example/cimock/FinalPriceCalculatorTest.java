package com.example.cimock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinalPriceCalculatorTest {
    private PriceRule deliveryPriceRule;
    private PriceRule extraChargeForElectronicsRule;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        deliveryPriceRule = new DeliveryPrice();
//        extraChargeForElectronicsRule = mock(PriceRule.class);
        extraChargeForElectronicsRule = new ExtraChargeForElectronics();
    }

    @Test
    void testCalculateFinalPriceWithMixedRules() {
        cart.addItem(ItemType.ELECTRONIC, 300.0, 1);
        cart.addItem(ItemType.OTHER, 50.0, 2);
//        when(extraChargeForElectronicsRule.priceToAggregator(cart)).thenReturn(7.5);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (7.5 + 5.0)
    }

    @Test
    void testCalculateFinalPriceWithMockReturningZero() {
        cart.addItem(ItemType.OTHER, 50.0, 3);
//        when(extraChargeForElectronicsRule.priceToAggregator(cart)).thenReturn(0.0);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(5.0, price); // 예상 결과 (0.0 + 5.0)
    }

    @Test
    void testCalculateFinalPriceWithSixItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(ItemType.OTHER, 10.0, 6);

//        when(extraChargeForElectronicsRule.priceToAggregator(cart)).thenReturn(0.0);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (0.0 + 12.5)
    }
}
