package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFuturesSetAccountPositionModeParamsDto {

  /**
   * 	Product type
   * USDT-FUTURES USDT-M Futures
   * COIN-FUTURES Coin-M Futures
   * USDC-FUTURES USDC-M Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Position mode
   * one_way_mode: one-way mode
   * hedge_mode: hedge mode
   */
  @JsonProperty("posMode")
  private BitgetFuturesPositionMode positionMode;

}
