package com.example.cimock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FinalPriceCalculatorTest {
    private PriceRule deliveryPriceRule;
    private PriceRule extraChargeForElectronicsRule;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        deliveryPriceRule = new DeliveryPrice();
        extraChargeForElectronicsRule = new ExtraChargeForElectronics();
    }

    @Test
    @DisplayName("전자 제품과 기타 항목이 섞인 경우의 최종 가격 계산")
    void testCalculateFinalPriceWithMixedRules() {
        cart.addItem(ItemType.ELECTRONIC, 300.0, 1);
        cart.addItem(ItemType.OTHER, 50.0, 2);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (7.5 + 5.0)
    }

    @Test
    @DisplayName("전자 제품이 없는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithMockReturningZero() {
        cart.addItem(ItemType.OTHER, 50.0, 3);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(5.0, price); // 예상 결과 (0.0 + 5.0)
    }

    @Test
    @DisplayName("장바구니에 10개의 항목이 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithTenItems() {
        cart.addItem(ItemType.OTHER, 10.0, 10);
        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (0.0 + 12.5)
    }

    @Test
    @DisplayName("장바구니에 10개 이상의 항목이 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithMoreThanTenItems() {
        cart.addItem(ItemType.OTHER, 10.0, 20);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(20.0, price); // 예상 결과 (20.0)
    }

    @Test
    @DisplayName("전자 제품만 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithOnlyElectronics() {
        cart.addItem(ItemType.ELECTRONIC, 300.0, 1);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (7.5 + 5.0)
    }

    @Test
    @DisplayName("여러 전자 제품이 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithMultipleElectronics() {
        cart.addItem(ItemType.ELECTRONIC, 300.0, 1);
        cart.addItem(ItemType.ELECTRONIC, 150.0, 2);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (5 + 7.5)
    }

    @Test
    @DisplayName("빈 장바구니의 경우 최종 가격 계산")
    void testCalculateFinalPriceWithNoItems() {
        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(0.0, price); // 예상 결과 (0.0)
    }

    @Test
    @DisplayName("장바구니에 3개의 항목이 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithThreeItems() {
        cart.addItem(ItemType.OTHER, 10.0, 3);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(5.0, price); // 예상 결과 (5.0)
    }

    @Test
    @DisplayName("장바구니에 4개의 항목이 있는 경우 최종 가격 계산")
    void testCalculateFinalPriceWithFourItems() {
        cart.addItem(ItemType.OTHER, 10.0, 4);

        FinalPriceCalculator calculator = new FinalPriceCalculator(
                Arrays.asList(deliveryPriceRule, extraChargeForElectronicsRule));
        double price = calculator.calculate(cart);

        assertEquals(12.5, price); // 예상 결과 (12.5)
    }

    @Test
    @DisplayName("장바구니가 비어 있을 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithNoItems() {
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(0.0, deliveryCharge); // 예상 결과: 0.0
    }

    @Test
    @DisplayName("장바구니에 항목이 1개일 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithOneItem() {
        cart.addItem(ItemType.OTHER, 10.0, 1);
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(5.0, deliveryCharge); // 예상 결과: 5.0
    }

    @Test
    @DisplayName("장바구니에 항목이 3개일 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithThreeItems() {
        cart.addItem(ItemType.OTHER, 10.0, 3);
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(5.0, deliveryCharge); // 예상 결과: 5.0
    }

    @Test
    @DisplayName("장바구니에 항목이 4개일 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithFourItems() {
        cart.addItem(ItemType.OTHER, 10.0, 4);
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(12.5, deliveryCharge); // 예상 결과: 12.5
    }

    @Test
    @DisplayName("장바구니에 항목이 10개일 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithTenItems() {
        cart.addItem(ItemType.OTHER, 10.0, 10);
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(12.5, deliveryCharge); // 예상 결과: 12.5
    }

    @Test
    @DisplayName("장바구니에 항목이 11개일 때 DeliveryPrice의 동작 테스트")
    void testDeliveryPriceWithElevenItems() {
        cart.addItem(ItemType.OTHER, 10.0, 11);
        double deliveryCharge = deliveryPriceRule.priceToAggregator(cart);
        assertEquals(20.0, deliveryCharge); // 예상 결과: 20.0
    }
}
