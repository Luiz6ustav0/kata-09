package org.blocktrust.pricing;

import java.math.BigDecimal;
import org.blocktrust.vo.PriceResultVO;

public class BatchDiscountPricingStrategy implements IPricingStrategy {

  private final BigDecimal unitPrice;
  private final BigDecimal batchPrice;
  private final int batchSize;

  public BatchDiscountPricingStrategy(BigDecimal unitPrice, BigDecimal batchPrice, int batchSize) {
    this.unitPrice = unitPrice;
    this.batchPrice = batchPrice;
    this.batchSize = batchSize;
  }

  @Override
  public PriceResultVO calculatePriceForQuantity(Integer quantity) {
    BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
    if (quantity < this.batchSize) {
      return new PriceResultVO(totalPrice, BigDecimal.ZERO);
    } else {
      int batchCount = quantity / this.batchSize;
      int remainder = quantity % this.batchSize;

      BigDecimal discountedPrice =
          this.batchPrice
              .multiply(new BigDecimal(batchCount))
              .add(this.unitPrice.multiply(new BigDecimal(remainder)));
      BigDecimal priceDifference = totalPrice.subtract(discountedPrice);
      return new PriceResultVO(discountedPrice, priceDifference);
    }
  }
}
