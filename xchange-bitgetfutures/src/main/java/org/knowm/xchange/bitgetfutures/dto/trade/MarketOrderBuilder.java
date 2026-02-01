package org.knowm.xchange.bitgetfutures.dto.trade;

import java.math.BigDecimal;
import lombok.Builder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

/**
 * This class should be implemented in core.
 */
@Builder()
public class MarketOrderBuilder {

  private OrderType type = null;
  private Instrument instrument = null;
  private BigDecimal originalAmount = null;
  private BigDecimal averagePrice = null;
  private String userReference = null;

  public MarketOrder toMarketOrder() {
    MarketOrder.Builder builder = new MarketOrder.Builder(type, instrument);

    if (originalAmount != null) {
      builder.originalAmount(originalAmount);
    }
    if (averagePrice != null) {
      builder.averagePrice(averagePrice);
    }
    if (userReference != null) {
      builder.userReference(userReference);
    }

    return builder.build();
  }
}
