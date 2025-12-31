package org.knowm.xchange.bitgetfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

/**
 * Account Balance Information
 */
@Data
@Builder
@Jacksonized
public class BitgetFuturesAccountBalanceInfoDto {

  /**
   * Margin coin
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  /**
   * Locked quantity (margin coin)
   */
  @JsonProperty("locked")
  private BigDecimal locked;
  /**
   * Available quantity in the account
   */
  @JsonProperty("available")
  private BigDecimal available;
  /**
   * Maximum available balance to open positions under the cross margin mode (margin coin).
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
   * Multi-assets multi-assets mode.
   */
  @JsonProperty("unionTotalMargin")
  private BigDecimal unionTotalMargin;

  /**
   * Available under multi-assets mode.
   */
  @JsonProperty("unionAvailable")
  private BigDecimal unionAvailable;

  /**
   * Maintenance margin under multi-assets mode.
   */
  @JsonProperty("unionMm")
  private BigDecimal unionMaintenanceMargin;

  /**
   * Assets list under multi-assets mode.
   */
  @JsonProperty("assetsList")
  private List<BitgetFuturesAccountSubBalanceDto> assets;

  /**
   * Isolated Margin Occupied.
   */
  @JsonProperty("isolatedMargin")
  private BigDecimal isolatedMargin;
  /**
   * Crossed Margin Occupied.
   */
  @JsonProperty("crossedMargin")
  private BigDecimal crossedMargin;
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

  @JsonProperty("assetMode")
  private AssetMode assetMode;


  public enum AssetMode {
    @JsonProperty("union")
    UNION,

    @JsonProperty("single")
    SINGLE
  }

}
