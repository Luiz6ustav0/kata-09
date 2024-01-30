package org.blocktrust.pricing;

import org.blocktrust.vo.PriceResultVO;

public interface IPricingStrategy {
    

    PriceResultVO calculatePriceForQuantity(Integer quantity);

}
