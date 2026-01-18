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
  private BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode;
  private Boolean reduceOnly;
  private BigDecimal presetStopSurplusPrice;
  private BigDecimal presetStopLossPrice;


  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode,
      BigDecimal originalAmount,
      Instrument instrument,
      Boolean reduceOnly,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice) {
    this(productType,
        type,
        marginMode,
        tradeSidePositionMode,
        originalAmount,
        instrument,
        reduceOnly,
        id,
        timestamp,
        limitPrice,
        null,
        presetStopSurplusPrice,
        presetStopLossPrice,
        null,
        null,
        null);
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode,
      BigDecimal originalAmount,
      BigDecimal cumulativeAmount,
      Instrument instrument,
      Boolean reduceOnly,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice) {
    this(productType,
        type,
        marginMode,
        tradeSidePositionMode,
        originalAmount,
        instrument,
        reduceOnly,
        id,
        timestamp,
        limitPrice,
        null,
        presetStopSurplusPrice,
        presetStopLossPrice,
        cumulativeAmount,
        null,
        null,
        null);
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode,
      BigDecimal originalAmount,
      Instrument instrument,
      Boolean reduceOnly,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal presetStopSurplusPrice,
      BigDecimal presetStopLossPrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status) {
    this(productType,
        type,
        marginMode,
        tradeSidePositionMode,
        originalAmount,
        instrument,
        reduceOnly,
        id,
        timestamp,
        limitPrice,
        averagePrice,
        presetStopSurplusPrice,
        presetStopLossPrice,
        cumulativeAmount,
        fee,
        status,
        null);
  }

  @lombok.Builder
  public BitgetFuturesLimitOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesMarginMode marginMode,
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode,
      BigDecimal originalAmount,
      Instrument instrument,
      Boolean reduceOnly,
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
    this.tradeSidePositionMode = tradeSidePositionMode;
    this.reduceOnly = reduceOnly;
    this.presetStopSurplusPrice = presetStopSurplusPrice;
    this.presetStopLossPrice = presetStopLossPrice;
  }
}