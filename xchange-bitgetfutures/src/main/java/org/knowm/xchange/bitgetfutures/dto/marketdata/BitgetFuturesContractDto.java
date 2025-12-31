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

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("baseCoin")
  private String baseCoin;

  @JsonProperty("quoteCoin")
  private String quoteCoin;

  @JsonProperty("buyLimitPriceRatio")
  private BigDecimal buyLimitPriceRatio;

  @JsonProperty("sellLimitPriceRatio")
  private BigDecimal sellLimitPriceRatio;

  @JsonProperty("feeRateUpRatio")
  private BigDecimal feeRateUpRatio;

  @JsonProperty("makerFeeRate")
  private BigDecimal makerFeeRate;

  @JsonProperty("takerFeeRate")
  private BigDecimal takerFeeRate;

  @JsonProperty("openCostUpRatio")
  private BigDecimal openCostUpRatio;

  @JsonProperty("supportMarginCoins")
  private List<String> supportMarginCoins;

  @JsonProperty("minTradeNum")
  private BigDecimal minTradeNum;

  @JsonProperty("priceEndStep")
  private BigDecimal priceEndStep;

  @JsonProperty("volumePlace")
  private BigDecimal volumePlace;

  @JsonProperty("pricePlace")
  private BigDecimal pricePlace;

  @JsonProperty("sizeMultiplier")
  private BigDecimal sizeMultiplier;

  /**
   * Futures types: perpetual; delivery
   */
  @JsonProperty("symbolType")
  private SymbolType symbolType;

  @JsonProperty("minTradeUSDT")
  private BigDecimal minTradeUSDT;

  @JsonProperty("maxSymbolOrderNum")
  private BigDecimal maxSymbolOrderNum;

  @JsonProperty("maxProductOrderNum")
  private BigDecimal maxProductOrderNum;

  @JsonProperty("maxPositionNum")
  private BigDecimal maxPositionNum;

  @JsonProperty("symbolStatus")
  private String symbolStatus;

  @JsonProperty("offTime")
  private BigDecimal offTime;

  @JsonProperty("limitOpenTime")
  private BigDecimal limitOpenTime;

  @JsonProperty("deliveryTime")
  private Instant deliveryTime;

  @JsonProperty("deliveryStartTime")
  private Instant deliveryStartTime;

  @JsonProperty("deliveryPeriod")
  private String deliveryPeriod;

  @JsonProperty("launchTime")
  private Instant launchTime;

  @JsonProperty("fundInterval")
  private BigDecimal fundInterval;

  @JsonProperty("minLever")
  private BigDecimal minLever;

  @JsonProperty("maxLever")
  private BigDecimal maxLever;

  @JsonProperty("posLimit")
  private BigDecimal posLimit;

  @JsonProperty("maintainTime")
  private Instant maintainTime;

  @JsonProperty("maxMarketOrderQty")
  private BigDecimal maxMarketOrderQty;

  @JsonProperty("maxOrderQty")
  private BigDecimal maxOrderQty;

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
