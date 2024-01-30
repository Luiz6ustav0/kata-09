package org.blocktrust.pricing;

import java.math.BigDecimal;
import org.blocktrust.vo.PriceResultVO;

public class RegularPricingStrategy implements IPricingStrategy {

  private final BigDecimal itemPrice;

  public RegularPricingStrategy(BigDecimal price) {
    this.itemPrice = price;
  }

  @Override
  public PriceResultVO calculatePriceForQuantity(Integer quantity) {
    BigDecimal totalPrice = this.itemPrice.multiply(new BigDecimal(quantity));
    return new PriceResultVO(totalPrice, BigDecimal.ZERO);
  }
}
