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
public class BitgetFuturesAccountSubBalanceDto {

  /**
   * Coin name
   */
  @JsonProperty("coin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  /**
   * Balance.
   */
  @JsonProperty("balance")
  private BigDecimal balance;

  /**
   * Maximum transferable amount. Unit: current coin
   */
  @JsonProperty("available")
  private BigDecimal available;

}
