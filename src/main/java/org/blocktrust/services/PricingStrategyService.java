package org.blocktrust.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.blocktrust.config.BasicLogger;
import org.blocktrust.pricing.IPricingStrategy;
import org.blocktrust.pricing.BatchDiscountPricingStrategy;
import org.blocktrust.pricing.RegularPricingStrategy;
import org.blocktrust.vo.CheckoutItemVO;
import org.blocktrust.vo.PriceResultVO;

public class PricingStrategyService {

  private final Map<String, IPricingStrategy> itemToPricingStrategy;

  public PricingStrategyService() {
    this.itemToPricingStrategy = new HashMap<>();
      this.itemToPricingStrategy.put(
              "A", new BatchDiscountPricingStrategy(new BigDecimal("50"), new BigDecimal("130"), 3));
    this.itemToPricingStrategy.put(
        "B", new BatchDiscountPricingStrategy(new BigDecimal("30"), new BigDecimal("45"), 2));
    this.itemToPricingStrategy.put("C", new RegularPricingStrategy(new BigDecimal("20")));
    this.itemToPricingStrategy.put("D", new RegularPricingStrategy(new BigDecimal("15")));
  }

  PriceResultVO calculatePriceFor(CheckoutItemVO checkoutItem) {
    return Optional.ofNullable(itemToPricingStrategy.get(checkoutItem.getItemName()))
        .map(strategy -> strategy.calculatePriceForQuantity(checkoutItem.getQuantity()))
        .orElseGet(
            () -> {
              BasicLogger.warn("No price found for item " + checkoutItem.getItemName());
              return new PriceResultVO(BigDecimal.ZERO, BigDecimal.ZERO);
            });
  }
}
