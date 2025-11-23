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

@Data
@Builder
@Jacksonized
public class BitgetFuturesSubAccountBalanceDto {

  /**
   * Sub account userId.
   */
  @JsonProperty("userId")
  private String userId;

  /**
   * Collection of all futures assets under sub-accounts.
   */
  @JsonProperty("assetsList")
  private List<BitgetFuturesSubAccountSubBalanceDto> assets;

}
