package org.blocktrust.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.blocktrust.vo.CheckoutItemVO;
import org.blocktrust.vo.PriceResultVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PricingStrategyServiceUnitTest {

    private final PricingStrategyService pricingStrategyService = new PricingStrategyService();

    @Test
    public void whenCalculatingPriceForRegularItemShouldReturnPriceMultipliedByQuantity() {
        // given
        CheckoutItemVO checkoutItem = new CheckoutItemVO("C", 3);
        BigDecimal expectedDiscount = BigDecimal.ZERO;
        BigDecimal expectedTotalPrice = new BigDecimal("60");

        // when
        PriceResultVO priceResultVO = pricingStrategyService.calculatePriceFor(checkoutItem);

        // then
        assertEquals(expectedTotalPrice, priceResultVO.totalPrice());
        assertEquals(expectedDiscount, priceResultVO.totalDiscount());
    }

    @Test
    public void whenCalculatingPriceForItemWithoutPricingShouldReturnEmptyPriceResult() {
        // given
        CheckoutItemVO checkoutItem = new CheckoutItemVO("Weird Item", 2);
        BigDecimal expectedDiscount = BigDecimal.ZERO;
        BigDecimal expectedTotalPrice = BigDecimal.ZERO;

        // when
        PriceResultVO priceResultVO = pricingStrategyService.calculatePriceFor(checkoutItem);

        // then
        assertEquals(expectedTotalPrice, priceResultVO.totalPrice());
        assertEquals(expectedDiscount, priceResultVO.totalDiscount());
    }

    @ParameterizedTest
    @MethodSource("getItemsWithBatchDiscountAndExactBatchSize")
    public void whenCalculatingPriceForItemWithBatchDiscountShouldReturnDiscountedPriceAsTotalAndPriceDifference(
            CheckoutItemVO checkoutItem, BigDecimal expectedDiscount, BigDecimal expectedPrice) {
        // when
        PriceResultVO priceResultVO = pricingStrategyService.calculatePriceFor(checkoutItem);

        // then
        assertEquals(expectedPrice, priceResultVO.totalPrice());
        assertEquals(expectedDiscount, priceResultVO.totalDiscount());
    }

    @ParameterizedTest
    @MethodSource("getItemsWithBatchDiscountButMoreItemsThanBatchSize")
    public void whenCalculatingPriceForItemsWithBatchDiscountButNotAllFitInABatchShouldUseNormalPriceForTheRest() {
        // given
        CheckoutItemVO checkoutItem = new CheckoutItemVO("B", 3);
        BigDecimal expectedDiscount = new BigDecimal("15");
        BigDecimal expectedTotalPrice = new BigDecimal("75");

        // when
        PriceResultVO priceResultVO = pricingStrategyService.calculatePriceFor(checkoutItem);

        // then
        assertEquals(expectedTotalPrice, priceResultVO.totalPrice());
        assertEquals(expectedDiscount, priceResultVO.totalDiscount());
    }

    private static Stream<Arguments> getItemsWithBatchDiscountAndExactBatchSize() {
        CheckoutItemVO checkoutItemA = new CheckoutItemVO("A", 3);
        BigDecimal expectedDiscountA = new BigDecimal("20");
        BigDecimal expectedTotalPriceA = new BigDecimal("130");
        CheckoutItemVO checkoutItemA2 = new CheckoutItemVO("A", 6);
        BigDecimal expectedDiscountA2 = new BigDecimal("40");
        BigDecimal expectedTotalPriceA2 = new BigDecimal("260");
        CheckoutItemVO checkoutItemB = new CheckoutItemVO("B", 2);
        BigDecimal expectedDiscountB = new BigDecimal("15");
        BigDecimal expectedTotalPriceB = new BigDecimal("45");

        return Stream.of(
                Arguments.of(checkoutItemA, expectedDiscountA, expectedTotalPriceA),
                Arguments.of(checkoutItemA2, expectedDiscountA2, expectedTotalPriceA2),
                Arguments.of(checkoutItemB, expectedDiscountB, expectedTotalPriceB));
    }

    private static Stream<Arguments> getItemsWithBatchDiscountButMoreItemsThanBatchSize() {
        CheckoutItemVO checkoutItemA = new CheckoutItemVO("A", 5);
        BigDecimal expectedDiscountA = new BigDecimal("20");
        BigDecimal expectedTotalPriceA = new BigDecimal("230");
        CheckoutItemVO checkoutItemB = new CheckoutItemVO("B", 7);
        BigDecimal expectedDiscountB = new BigDecimal("45");
        BigDecimal expectedTotalPriceB = new BigDecimal("165");
        CheckoutItemVO checkoutItemB2 = new CheckoutItemVO("B", 3);
        BigDecimal expectedDiscountB2 = new BigDecimal("15");
        BigDecimal expectedTotalPriceB2 = new BigDecimal("75");

        return Stream.of(
                Arguments.of(checkoutItemA, expectedDiscountA, expectedTotalPriceA),
                Arguments.of(checkoutItemB, expectedDiscountB, expectedTotalPriceB),
                Arguments.of(checkoutItemB2, expectedDiscountB2, expectedTotalPriceB2));
    }
}
