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
 * Account Leverage Change Information
 */
@Data
@Builder
@Jacksonized
public class BitgetFuturesAccountLeverageDto {

  /**
   * Trading pair, e.g. BTCUSDT
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Product type
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Margin coin
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  /**
   * Leverage ratio - Applicable to cross-margin mode - Applicable to one-way position scenarios in
   * isolated margin mode - Applicable to scenarios where the same leverage ratio is set for
   * different directions under hedge-mode in isolated margin mode
   */
  @JsonProperty("leverage")
  private BigDecimal leverage;

}
