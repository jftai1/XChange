package org.knowm.xchange.bitgetfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFuturesTickerDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("lastPr")
  private BigDecimal lastPrice;

  @JsonProperty("askPr")
  private BigDecimal bestAskPrice;

  @JsonProperty("bidPr")
  private BigDecimal bestBidPrice;

  @JsonProperty("askSz")
  private BigDecimal bestAskSize;

  @JsonProperty("bidSz")
  private BigDecimal bestBidSize;

  @JsonProperty("high24h")
  private BigDecimal high24h;

  @JsonProperty("low24h")
  private BigDecimal low24h;

  @JsonProperty("ts")
  private Instant timestamp;

  @JsonProperty("change24h")
  private BigDecimal change24h;

  @JsonProperty("baseVolume")
  private BigDecimal assetVolume24h;

  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume24h;

  @JsonProperty("usdtVolume")
  private BigDecimal usdtVolume24h;

  @JsonProperty("openUtc")
  private BigDecimal openUtc;

  @JsonProperty("changeUtc24h")
  private BigDecimal changeUtc24h;

  /**
   * Index price
   */
  @JsonProperty("indexPrice")
  private BigDecimal indexPrice;

  /**
   * Funding rate
   */
  @JsonProperty("fundingRate")
  private BigDecimal fundingRate;

  /**
   * Current holding positions(base coin)
   */
  @JsonProperty("holdingAmount")
  private BigDecimal holdingAmount;

  /**
   * Entry price of the last 24 hours
   * The opening time is compared on a 24-hour basis. i.e.:
   * Now it is 7:00 PM of the 2nd day of the month, then the corresponding opening time is 7:00 PM of the 1st day of the month.
   */
  @JsonProperty("open24h")
  private BigDecimal open24h;

  /**
   * Mark price
   */
  @JsonProperty("markPrice")
  private BigDecimal markPrice;


}
