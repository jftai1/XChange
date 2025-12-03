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
public class BitgetFuturesCancelOrderParamsDto {

  /**
   * Trading pair
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Product type USDT-FUTURES USDT-M Futures COIN-FUTURES Coin-M Futures USDC-FUTURES USDC-M
   * Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Margin coin must be capitalized
   */
  @JsonProperty("marginCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  /**
   * Order ID Either orderId or clientOid is required. If both are present, orderId prevails.
   */
  @JsonProperty("orderId")
  private String orderId;

  /**
   * Customize order ID Either orderId or clientOid is required. If both are present, orderId
   * prevails.
   */
  @JsonProperty("clientOid")
  private String clientOid;

}
