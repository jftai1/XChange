package org.knowm.xchange.bitgetfutures.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesLimitOrder extends LimitOrder {

  private BitgetFuturesProductType productType;

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType, OrderType type, BigDecimal originalAmount, Instrument instrument,
      String id, Date timestamp, BigDecimal limitPrice) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType, OrderType type, BigDecimal originalAmount,
      BigDecimal cumulativeAmount, Instrument instrument, String id, Date timestamp,
      BigDecimal limitPrice) {
    super(type, originalAmount, cumulativeAmount, instrument, id, timestamp, limitPrice);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType, OrderType type, BigDecimal originalAmount, Instrument instrument,
      String id, Date timestamp, BigDecimal limitPrice, BigDecimal averagePrice,
      BigDecimal cumulativeAmount, BigDecimal fee, OrderStatus status) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice, averagePrice,
        cumulativeAmount,
        fee, status);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType, OrderType type, BigDecimal originalAmount, Instrument instrument,
      String id, Date timestamp, BigDecimal limitPrice, BigDecimal averagePrice,
      BigDecimal cumulativeAmount, BigDecimal fee, OrderStatus status, String userReference) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice, averagePrice,
        cumulativeAmount,
        fee, status, userReference);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
  }
}