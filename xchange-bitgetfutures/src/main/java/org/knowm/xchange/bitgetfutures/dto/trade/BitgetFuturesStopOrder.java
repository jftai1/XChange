package org.knowm.xchange.bitgetfutures.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.instrument.Instrument;

/**
 * Bitget futures stop order.
 */
@Getter
public class BitgetFuturesStopOrder extends StopOrder {

  private final BitgetFuturesProductType productType;
  private final BitgetFuturesOrderTriggerPriceType triggerPriceType;

  @lombok.Builder
  public BitgetFuturesStopOrder(
      BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode,
      BigDecimal originalAmount,
      Instrument instrument,
      Boolean reduceOnly,
      String id,
      Date timestamp,
      BitgetFuturesOrderTriggerPriceType triggerPriceType,
      BigDecimal stopPrice,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String userReference,
      Intention intention,
      BigDecimal trailValue) {
    super(type,
        originalAmount,
        instrument,
        id,
        timestamp,
        stopPrice,
        limitPrice,
        averagePrice,
        cumulativeAmount,
        fee,
        status,
        userReference,
        intention,
        trailValue);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    this.triggerPriceType = triggerPriceType;
  }
}
