package org.blocktrust.vo;

import java.math.BigDecimal;

public class CheckoutItemVO {

  private final String itemName;
  private int quantity;
  private PriceResultVO priceResultVO;

  public CheckoutItemVO(String itemName, int quantity) {
    this.itemName = itemName;
    this.quantity = quantity;
    this.priceResultVO = new PriceResultVO(BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public void increaseQuantity() {
    this.quantity++;
  }

  public PriceResultVO getPriceResultVO() {
    return priceResultVO;
  }

  public void setPriceResultVO(PriceResultVO priceResultVO) {
    this.priceResultVO = priceResultVO;
  }

  public String getItemName() {
    return itemName;
  }

  public int getQuantity() {
    return quantity;
  }
}
