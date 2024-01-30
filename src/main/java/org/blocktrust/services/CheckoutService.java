package org.blocktrust.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.blocktrust.vo.CheckoutItemVO;
import org.blocktrust.vo.PriceResultVO;

public class CheckoutService {
  private final PricingStrategyService pricingService;
  private final Map<String, CheckoutItemVO> items;
  private BigDecimal totalPrice = BigDecimal.ZERO;
  private BigDecimal totalDiscount = BigDecimal.ZERO;

  public CheckoutService(PricingStrategyService pricingService) {
    this.items = new HashMap<>();
    this.pricingService = pricingService;
  }

  public void scan(String item) {
    CheckoutItemVO checkoutItem = this.items.getOrDefault(item, new CheckoutItemVO(item, 0));
    checkoutItem.increaseQuantity();
    PriceResultVO updatedItemPrice = pricingService.calculatePriceFor(checkoutItem);
    updatedTotalCheckoutPrices(checkoutItem, updatedItemPrice);
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  private void updatedTotalCheckoutPrices(
      CheckoutItemVO checkoutItem, PriceResultVO updatedItemPrice) {
    this.totalPrice = this.totalPrice.subtract(checkoutItem.getPriceResultVO().totalPrice());
    this.totalDiscount =
        this.totalDiscount.subtract(checkoutItem.getPriceResultVO().totalDiscount());
    checkoutItem.setPriceResultVO(updatedItemPrice);
    this.totalPrice = this.totalPrice.add(checkoutItem.getPriceResultVO().totalPrice());
    this.totalDiscount = this.totalDiscount.add(checkoutItem.getPriceResultVO().totalDiscount());
    this.items.put(checkoutItem.getItemName(), checkoutItem);
  }
}
