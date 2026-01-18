package org.knowm.xchange.bitgetfutures.dto.trade;

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
public class BitgetFuturesCancelTakeProfitStopLossOrderParamsDto {

  /**
   * Product type USDT-FUTURES USDT-M Futures COIN-FUTURES Coin-M Futures USDC-FUTURES USDC-M
   * Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Trading pair
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Margin coin must be capitalized
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  /**
   *Take profit and stop loss type
   * profit_plan: take profit plan;
   * loss_plan: stop loss plan;
   * moving_plan: trailing stop;
   * pos_profit: position take profit;
   * pos_loss: position stop loss
   */
  @JsonProperty("planType")
  private BitgetFuturesTakeProfitStopLossPlanType planType;

}
