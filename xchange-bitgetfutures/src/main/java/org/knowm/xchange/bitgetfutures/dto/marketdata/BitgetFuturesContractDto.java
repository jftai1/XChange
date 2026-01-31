package org.knowm.xchange.bitgetfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.derivative.BitgetFuturesContract;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

@Data
@Builder
@Jacksonized
public class BitgetFuturesContractDto {

  /**
   * Product name
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Base currency
   * Specifically refers to ETH as in ETHUSDT
   */
  @JsonProperty("baseCoin")
  private String baseCoin;

  /**
   * Quote currency
   * Specifically refers to USDT as in ETHUSDT
   */
  @JsonProperty("quoteCoin")
  private String quoteCoin;

  /**
   * Ratio of bid price to limit price
   */
  @JsonProperty("buyLimitPriceRatio")
  private BigDecimal buyLimitPriceRatio;

  /**
   * Ratio of ask price to limit price
   */
  @JsonProperty("sellLimitPriceRatio")
  private BigDecimal sellLimitPriceRatio;

  /**
   * Transaction fee increase ratio
   */
  @JsonProperty("feeRateUpRatio")
  private BigDecimal feeRateUpRatio;

  /**
   * Maker rate
   */
  @JsonProperty("makerFeeRate")
  private BigDecimal makerFeeRate;

  /**
   * Taker rate
   */
  @JsonProperty("takerFeeRate")
  private BigDecimal takerFeeRate;

  /**
   * Opening cost increase ratio
   */
  @JsonProperty("openCostUpRatio")
  private BigDecimal openCostUpRatio;

  /**
   * Supported margin coins
   */
  @JsonProperty("supportMarginCoins")
  private List<String> supportMarginCoins;

  /**
   * Minimum opening amount (base currency)
   */
  @JsonProperty("minTradeNum")
  private BigDecimal minTradeNum;

  /**
   * price step length
   */
  @JsonProperty("priceEndStep")
  private BigDecimal priceEndStep;

  /**
   * Decimal places of the quantity
   */
  @JsonProperty("volumePlace")
  private BigDecimal volumePlace;

  /**
   * Decimal places of the price
   */
  @JsonProperty("pricePlace")
  private BigDecimal pricePlace;

  /**
   * Quantity multiplier, the quantity of the order must be greater
   * than minTradeNum and is a multiple of sizeMulti.
   */
  @JsonProperty("sizeMultiplier")
  private BigDecimal sizeMultiplier;

  /**
   * Futures types: perpetual; delivery
   */
  @JsonProperty("symbolType")
  private SymbolType symbolType;

  /**
   * Minimum USDT transaction amount
   */
  @JsonProperty("minTradeUSDT")
  private BigDecimal minTradeUSDT;

  /**
   * Maximum number of orders held-symbol dimension
   */
  @JsonProperty("maxSymbolOrderNum")
  private BigDecimal maxSymbolOrderNum;

  /**
   * Maximum number of held orders-product type dimension
   */
  @JsonProperty("maxProductOrderNum")
  private BigDecimal maxProductOrderNum;

  /**
   * Maximum number of positions held
   */
  @JsonProperty("maxPositionNum")
  private BigDecimal maxPositionNum;

  /**
   * Trading pair status
   * listed Listing symbol
   * normal trade normal
   * maintain can't open/close position
   * limit_open: can't place orders(can close position)
   * restrictedAPI:can't place orders with API
   * off: offline
   */
  @JsonProperty("symbolStatus")
  private String symbolStatus;

  /**
   * Removal time, '-1' means normal
   */
  @JsonProperty("offTime")
  private BigDecimal offTime;

  /**
   * 	Time to open positions, '-1' means normal;
   * 	other values indicate that the symbol is under maintenance
   * 	or to be maintained and trading is prohibited after the specified time.
   */
  @JsonProperty("limitOpenTime")
  private BigDecimal limitOpenTime;

  /**
   * Delivery time
   */
  @JsonProperty("deliveryTime")
  private Instant deliveryTime;

  /**
   * Delivery start time
   */
  @JsonProperty("deliveryStartTime")
  private Instant deliveryStartTime;

  /**
   * Delivery period
   * this_quarter current quarter
   * next_quarter second quarter
   */
  @JsonProperty("deliveryPeriod")
  private String deliveryPeriod;

  /**
   * 	Listing time
   */
  @JsonProperty("launchTime")
  private Instant launchTime;

  /**
   * Funding fee settlement cycle,
   * hourly/every 8 hours
   */
  @JsonProperty("fundInterval")
  private BigDecimal fundInterval;

  /**
   * minimum leverage
   */
  @JsonProperty("minLever")
  private BigDecimal minLever;

  /**
   * Maximum leverage
   */
  @JsonProperty("maxLever")
  private BigDecimal maxLever;

  /**
   * Position limits
   */
  @JsonProperty("posLimit")
  private BigDecimal posLimit;

  /**
   * Maintenance time
   * (there will be a value when the status is under maintenance/upcoming maintenance)
   */
  @JsonProperty("maintainTime")
  private Instant maintainTime;

  /**
   * Maximum order quantity for a single MARKET order
   * This refers to the maximum allowed quantity when placing an order using the base coin
   */
  @JsonProperty("maxMarketOrderQty")
  private BigDecimal maxMarketOrderQty;

  /**
   * Maximum order quantity for a single LIMIT order
   * This refers to the maximum allowed quantity when placing an order using the base coin
   */
  @JsonProperty("maxOrderQty")
  private BigDecimal maxOrderQty;

  /**
   * Is this an RWA Symbol
   * YES
   * NO
   */
  @JsonProperty("isRwa")
  private String isRwa;

  /**
   * Get the matching CurrencyPair.
   * @return
   */
  public CurrencyPair getCurrencyPair(){
    return new CurrencyPair(baseCoin, quoteCoin);
  }

  /**
   * Get the matching FutureContract (Intrument).
   * @return
   */
  public FuturesContract getFuturesContract(){
    return new BitgetFuturesContract(getCurrencyPair(),symbolType.name());
  }

  public enum SymbolType {
    @JsonProperty("perpetual")
    PERPETUAL,
    @JsonProperty("delivery")
    DELIVERY
  }

}
