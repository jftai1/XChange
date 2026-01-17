package org.knowm.xchange.bitgetfutures.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

@Getter
public class BitgetFuturesLimitOrder extends LimitOrder {

  private BitgetFuturesProductType productType;
  private BitgetFuturesMarginMode marginMode;
  private BigDecimal presetStopSurplusPrice;
  private BigDecimal presetStopLossPrice;

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
    this.presetStopSurplusPrice = presetStopSurplusPrice;
    this.presetStopLossPrice = presetStopLossPrice;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BigDecimal originalAmount,
      BigDecimal cumulativeAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice) {
    super(type, originalAmount, cumulativeAmount, instrument, id, timestamp, limitPrice);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
    this.presetStopSurplusPrice = presetStopSurplusPrice;
    this.presetStopLossPrice = presetStopLossPrice;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice, averagePrice,
        cumulativeAmount,
        fee, status);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
    this.presetStopSurplusPrice = presetStopSurplusPrice;
    this.presetStopLossPrice = presetStopLossPrice;
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String userReference) {
    super(type, originalAmount, instrument, id, timestamp, limitPrice, averagePrice,
        cumulativeAmount,
        fee, status, userReference);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
    this.presetStopSurplusPrice = presetStopSurplusPrice;
    this.presetStopLossPrice = presetStopLossPrice;
  }
}