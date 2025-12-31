package org.knowm.xchange.bitgetfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

/**
 * Single Account Details
 */
@Data
@Builder
@Jacksonized
public class BitgetFuturesAccountBalanceDetailDto {
  /**
   * Margin coin
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;
  /**
   * Locked quantity (margin coin). Lockup will be triggered when there is a position to be closed.
   */
  @JsonProperty("locked")
  private BigDecimal locked;
  /**
   * Available quantity in the account
   */
  @JsonProperty("available")
  private BigDecimal available;
  /**
   * Maximum available balance to open positions under the cross margin mode (margin coin)
   */
  @JsonProperty("crossedMaxAvailable")
  private BigDecimal crossedMaxAvailable;
  /**
   * Maximum available balance to open positions under the isolated margin mode (margin coin).
   */
  @JsonProperty("isolatedMaxAvailable")
  private BigDecimal isolatedMaxAvailable;
  /**
   * Maximum transferable amount
   */
  @JsonProperty("maxTransferOut")
  private BigDecimal maxTransferOut;
  /**
   * Account equity (margin coin), Includes unrealized PnL (based on mark price).
   */
  @JsonProperty("accountEquity")
  private BigDecimal accountEquity;
  /**
   * Account equity in USDT.
   */
  @JsonProperty("usdtEquity")
  private BigDecimal usdtEquity;
  /**
   * Account equity in BTC.
   */
  @JsonProperty("btcEquity")
  private BigDecimal btcEquity;
  /**
   * Risk ratio in cross margin mode.
   */
  @JsonProperty("crossedRiskRate")
  private BigDecimal crossedRiskRate;
  /**
   * Leverage in cross margin mode
   */
  @JsonProperty("crossedMarginLeverage")
  private BigDecimal crossedMarginLeverage;
  /**
   * Leverage of long positions in isolated margin mode
   */
  @JsonProperty("isolatedLongLever")
  private BigDecimal isolatedLongLever;
  /**
   * Leverage of short positions in isolated margin mode
   */
  @JsonProperty("isolatedShortLever")
  private BigDecimal isolatedShortLever;
  /**
   * Margin mode. isolated – isolated margin mode; crossed – cross margin mode
   */
  @JsonProperty("marginMode")
  private MarginMode marginMode;
  /**
   * Position mode one_way_mode: one-way mode hedge_mode: hedge mode
   */
  @JsonProperty("posMode")
  private PositionMode positionMode;
  /**
   * Unrealized PnL
   */
  @JsonProperty("unrealizedPL")
  private BigDecimal unrealizedPL;
  /**
   * Trading bonus.
   */
  @JsonProperty("coupon")
  private BigDecimal coupon;
  /**
   * UnrealizedPL for crossed.
   */
  @JsonProperty("crossedUnrealizedPL")
  private BigDecimal crossedUnrealizedPL;
  /**
   * UnrealizedPL for isolated.
   */
  @JsonProperty("isolatedUnrealizedPL")
  private BigDecimal isolatedUnrealizedPL;
  /**
   * Assets mode union Multi-assets mode single Single-assets mode
   */
  @JsonProperty("assetMode")
  private AssetMode assetMode;

  public enum MarginMode {
    @JsonProperty("crossed")
    CROSSED,
    @JsonProperty("isolated")
    ISOLATED
  }

  public enum PositionMode {
    @JsonProperty("one_way_mode")
    ONE_WAY_POSITION,
    @JsonProperty("hedge_mode")
    TWO_WAY_POSITION
  }

  public enum AssetMode {
    @JsonProperty("union")
    UNION,
    @JsonProperty("single")
    SINGLE
  }
}
