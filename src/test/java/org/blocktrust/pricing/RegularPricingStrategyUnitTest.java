package org.blocktrust.pricing;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.blocktrust.vo.PriceResultVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegularPricingStrategyUnitTest {

  @Test
  public void whenCalculatingPriceShouldReturnValueMultipliedByQuantity() {
    // given
    RegularPricingStrategy regularPricingStrategy =
        new RegularPricingStrategy(new BigDecimal("15"));
    BigDecimal expectedPrice = new BigDecimal("180");
    BigDecimal expectedDiscount = BigDecimal.ZERO;

    // when
    PriceResultVO priceResultVO = regularPricingStrategy.calculatePriceForQuantity(12);

    // then
    Assertions.assertEquals(expectedPrice, priceResultVO.totalPrice());
    Assertions.assertEquals(expectedDiscount, priceResultVO.totalDiscount());
  }

}
