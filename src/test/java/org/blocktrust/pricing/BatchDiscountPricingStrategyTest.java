package org.blocktrust.pricing;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.blocktrust.vo.PriceResultVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BatchDiscountPricingStrategyTest {

  @Test
  public void whenCalculatingPriceShouldReturnValueMultipliedByQuantity() {
    // given
    BatchDiscountPricingStrategy batchDiscountPricingStrategy =
        new BatchDiscountPricingStrategy(new BigDecimal("50"), new BigDecimal("130"), 3);
    BigDecimal expectedPrice = new BigDecimal("440");
    BigDecimal expectedDiscount = new BigDecimal("60");

    // when
    PriceResultVO priceResultVO = batchDiscountPricingStrategy.calculatePriceForQuantity(10);

    // then
    Assertions.assertEquals(expectedPrice, priceResultVO.totalPrice());
    Assertions.assertEquals(expectedDiscount, priceResultVO.totalDiscount());
  }
}
