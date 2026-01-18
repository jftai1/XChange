package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BitgetFuturesClosePositionsParamsDto {

  /**
   * 	Product type
   * USDT-FUTURES USDT-M Futures
   * COIN-FUTURES Coin-M Futures
   * USDC-FUTURES USDC-M Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Trading pair
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Position direction
   * 1. In one-way position mode(buy or sell): This field should be left blank. Will be ignored if filled in.
   * 2. In hedge-mode position(open or close): All positions will be closed if the field is left blank; Positions of the specified direction will be closed is the field is filled in.
   * long: Long position; short: Short position
   */
  @JsonProperty("holdSide")
  private BitgetFuturesPositionMode positionMode;

}
