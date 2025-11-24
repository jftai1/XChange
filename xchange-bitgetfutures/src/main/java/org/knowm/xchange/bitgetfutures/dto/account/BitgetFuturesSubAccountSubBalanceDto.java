package org.knowm.xchange.bitgetfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BitgetFuturesSubAccountSubBalanceDto {

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
   * PnL of open positions
   */
  @JsonProperty("unrealizedPL")
  private BigDecimal unrealizedPL;

  /**
   * Trading bonus.
   */
  @JsonProperty("coupon")
  private BigDecimal coupon;

}
