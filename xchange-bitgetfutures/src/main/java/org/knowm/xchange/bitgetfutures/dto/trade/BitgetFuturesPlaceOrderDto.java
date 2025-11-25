package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.bitgetfutures.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitgetfutures.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;

@Data
@Builder
@Jacksonized
public class BitgetFuturesPlaceOrderDto {

  /**
   * Trading pair, e.g. BTC_USDT
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Product type
   */
  @JsonProperty("productType")
  private String productType;

  @JsonProperty("marginMode")
  private MarginMode marginMode;

  /**
   * Margin coin
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  /**
   * Amount (base coin) To get the decimal places of size :
   * https://www.bitget.com/api-doc/contract/market/Get-All-Symbols-Contracts
   */
  @JsonProperty("size")
  private BigDecimal size;

  /**
   * Price of the order. Required if the "orderType" is limit To get the decimal places of price :
   * https://www.bitget.com/api-doc/contract/market/Get-All-Symbols-Contracts
   */
  @JsonProperty("price")
  private BigDecimal price;

  /**
   * Trade side buy: Buy(one-way-mode); Long position direction(hedge-mode) sell:
   * Sell(one-way-mode); Short position direction(hedge-mode)
   */
  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  private Order.OrderType orderSide;

  /**
   * Trade type Only required in hedge-mode open: Open position close: Close position
   */
  @JsonProperty("tradeSide")
  private String tradeSide;

  /**
   * Order type limit: limit orders market: market orders
   */
  @JsonProperty("orderType")
  private OrderType orderType;

  /**
   * Order expiration date. Required if the orderType is limit ioc: Immediate or cancel fok: Fill or
   * kill gtc: Good till canceled(default value) post_only: Post only
   */
  @JsonProperty("force")
  private TimeInForce timeInForce;

  /**
   * Customize order ID
   */
  @JsonProperty("clientOid")
  private String clientOid;

  /**
   * Whether or not to just reduce the position.
   */
  @JsonProperty("reduceOnly")
  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean reduceOnly;

  /**
   * Take-profit value No take-profit is set if the field is empty.
   */
  @JsonProperty("presetStopSurplusPrice")
  private BigDecimal presetStopSurplusPrice;

  /**
   * Stop-loss value No stop-loss is set if the field is empty.
   */
  @JsonProperty("presetStopLossPrice")
  private BigDecimal presetStopLossPrice;

  /**
   * Preset stop - profit execution price.
   */
  @JsonProperty("presetStopSurplusExecutePrice")
  private BigDecimal presetStopSurplusExecutePrice;

  /**
   * Preset stop-loss execution price.
   */
  @JsonProperty("presetStopLossExecutePrice")
  private BigDecimal presetStopLossExecutePrice;

  public enum OrderType {
    @JsonProperty("limit")
    LIMIT,

    @JsonProperty("market")
    MARKET
  }

  /**
   * STP Mode(Self Trade Prevention) none: not setting STP(default value) cancel_taker: cancel taker
   * order cancel_maker: cancel maker order cancel_both: cancel both of taker and maker orders
   */
  @JsonProperty("stpMode")
  private StpMode stpMode;

  public enum MarginMode {
    @JsonProperty("crossed")
    CROSSED,

    @JsonProperty("isolated")
    ISOLATED
  }

  public enum TimeInForce {
    @JsonProperty("fok")
    FILL_OR_KILL,
    @JsonProperty("gtc")
    GOOD_TIL_CANCELLED,
    @JsonProperty("ioc")
    IMMEDIATE_OR_CANCEL,
    @JsonProperty("post_only")
    POST_ONLY
  }

  public enum StpMode {
    @JsonProperty("none")
    NONE,

    @JsonProperty("cancel_taker")
    CANCEL_TAKER,

    @JsonProperty("cancel_maker")
    CANCEL_MAKER,

    @JsonProperty("cancel_both")
    CANCEL_BOTH
  }
}
