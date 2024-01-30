package org.blocktrust.vo;

import java.math.BigDecimal;

public record PriceResultVO(BigDecimal totalPrice, BigDecimal totalDiscount) {
}
