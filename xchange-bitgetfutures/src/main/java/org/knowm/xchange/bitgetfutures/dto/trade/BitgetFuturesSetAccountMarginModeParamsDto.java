package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFuturesSetAccountMarginModeParamsDto {

  /**
   * 	Product type
   * USDT-FUTURES USDT-M Futures
   * COIN-FUTURES Coin-M Futures
   * USDC-FUTURES USDC-M Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Trading pair. e.g. BTCUSDT
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Margin coin, must be capitalized
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

}
