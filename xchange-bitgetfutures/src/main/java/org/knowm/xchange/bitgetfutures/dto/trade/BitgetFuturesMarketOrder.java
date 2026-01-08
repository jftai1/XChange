package org.knowm.xchange.bitgetfutures.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

/**
 * Bitget futures order.
 */
@Getter
public class BitgetFuturesMarketOrder extends MarketOrder {

  private BitgetFuturesProductType productType;
  private BitgetFuturesOrderMarginMode marginMode;

  @lombok.Builder
  public BitgetFuturesMarketOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesOrderMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String userReference) {
    super(type, originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, fee,
        status, userReference);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
  }

  @lombok.Builder
  public BitgetFuturesMarketOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesOrderMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status) {
    super(type, originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, fee,
        status);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
  }

  @lombok.Builder
  public BitgetFuturesMarketOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesOrderMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp) {
    super(type, originalAmount, instrument, id, timestamp);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
  }

  @lombok.Builder
  public BitgetFuturesMarketOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesOrderMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument,
      Date timestamp) {
    super(type, originalAmount, instrument, timestamp);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
  }

  @lombok.Builder
  public BitgetFuturesMarketOrder(BitgetFuturesProductType productType,
      OrderType type,
      BitgetFuturesOrderMarginMode marginMode,
      BigDecimal originalAmount,
      Instrument instrument) {
    super(type, originalAmount, instrument);
    Objects.requireNonNull(productType, "productType must not be null");
    this.productType = productType;
    Objects.requireNonNull(marginMode, "marginMode must not be null");
    this.marginMode = marginMode;
  }
}
