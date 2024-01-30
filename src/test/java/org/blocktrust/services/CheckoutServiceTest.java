package org.blocktrust.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CheckoutServiceTest {

  @Test
  public void totals() {
    assertEquals(BigDecimal.ZERO, calculatePrice(""));
    assertEquals(new BigDecimal("50"), calculatePrice("A"));
    assertEquals(new BigDecimal("80"), calculatePrice("AB"));
    assertEquals(new BigDecimal("115"), calculatePrice("CDBA"));

    assertEquals(new BigDecimal("100"), calculatePrice("AA"));
    assertEquals(new BigDecimal("130"), calculatePrice("AAA"));
    assertEquals(new BigDecimal("180"), calculatePrice("AAAA"));
    assertEquals(new BigDecimal("230"), calculatePrice("AAAAA"));
    assertEquals(new BigDecimal("260"), calculatePrice("AAAAAA"));

    assertEquals(new BigDecimal("160"), calculatePrice("AAAB"));
    assertEquals(new BigDecimal("175"), calculatePrice("AAABB"));
    assertEquals(new BigDecimal("190"), calculatePrice("AAABBD"));
    assertEquals(new BigDecimal("190"), calculatePrice("DABABA"));
  }

  @Test
  public void incremental() {
    CheckoutService checkout = new CheckoutService(new PricingStrategyService());
    assertEquals(new BigDecimal("0"), checkout.getTotalPrice());
    checkout.scan("A");
    assertEquals(new BigDecimal("50"), checkout.getTotalPrice());
    checkout.scan("B");
    assertEquals(new BigDecimal("80"), checkout.getTotalPrice());
    checkout.scan("A");
    assertEquals(new BigDecimal("130"), checkout.getTotalPrice());
    checkout.scan("A");
    assertEquals(new BigDecimal("160"), checkout.getTotalPrice());
    checkout.scan("B");
    assertEquals(new BigDecimal("175"), checkout.getTotalPrice());
  }

  private BigDecimal calculatePrice(String items) {
    CheckoutService checkout = new CheckoutService(new PricingStrategyService());
    for (int index = 0; index < items.length(); index++) {
      checkout.scan(String.valueOf(items.charAt(index)));
    }
    return checkout.getTotalPrice();
  }

}
