package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFururesSetAccountLeverageResponseDto {

  /**
   * Trading pair. e.g. BTCUSDT
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Margin coin
   */
  @JsonProperty("marginCoin")
  private String marginCoin;

  /**
   * Margin mode.
   * isolated: isolated margin mode
   * crossed: crossed margin mode
   */
  @JsonProperty("marginMode")
  private BitgetFuturesMarginMode marginMode;

  /**
   * Leverage of long positions
   */
  @JsonProperty("longLeverage")
  private BigDecimal longLeverage;

  /**
   * Leverage of short positions
   */
  @JsonProperty("shortLeverage")
  private BigDecimal shortLeveage;
}
