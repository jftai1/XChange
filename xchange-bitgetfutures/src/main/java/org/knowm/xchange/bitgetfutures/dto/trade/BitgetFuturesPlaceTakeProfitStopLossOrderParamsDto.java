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
public class BitgetFuturesPlaceTakeProfitStopLossOrderParamsDto {

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

  /**
   * Trigger price
   */
  @JsonProperty("triggerPrice")
  private BigDecimal triggerPrice;

  /**
   * Trigger type
   * fill_price: market price;
   * mark_price: mark price
   */
  @JsonProperty("triggerType")
  private BitgetFuturesOrderTriggerPriceType triggerType;

  /**
   * Execution price
   * If it is 0 or not filled in, it means market price execution.
   * If it is greater than 0, it means limit price execution.
   * Do not fill in this parameters when planType is moving_plan,
   * it only executs in market price.
   */
  @JsonProperty("executePrice")
  private BigDecimal executePrice;

  /**
   * Two-way position:(long: long position, short: short position)
   * one-way position: (buy: long position, sell: short position)
   */
  @JsonProperty("holdSide")
  private BitgetFuturesPositionSide positionSide;

  /**
   * Order quantity(base coin)
   * It's required when planType is profit_plan, loss_plan or moving_plan,and should be greater than 0;
   * It's NOT required when planType is pos_profit or pos_loss
   */
  @JsonProperty("size")
  private BigDecimal size;

  /**
   * Callback range
   * It's required only in planType is moving_plan
   */
  @JsonProperty("rangeRate")
  private BigDecimal rangeRate;

  /**
   * Customize order ID
   */
  @JsonProperty("clientOid")
  private String clientOid;

  /**
   * STP Mode(Self Trade Prevention)
   * none: not setting STP(default value)
   * cancel_taker: cancel taker order
   * cancel_maker: cancel maker order
   * cancel_both: cancel both of taker and maker orders
   */
  @JsonProperty("stpMode")
  private BitgetFuturesOrderStpMode stpMode;

}
