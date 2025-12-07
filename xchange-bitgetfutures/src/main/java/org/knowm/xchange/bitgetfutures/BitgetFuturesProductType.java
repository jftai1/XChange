package org.knowm.xchange.bitgetfutures;

import java.io.Serializable;

public class BitgetFuturesProductType implements Serializable {

  private static final long serialVersionUID = 8277116217842459973L;

  /**
   * USDT-M Futures, Futures settled in USDT
   */
  public static final BitgetFuturesProductType USDT_FUTURES = new BitgetFuturesProductType("USDT-FUTURES");
  /**
   * USDC-M Futures, Futures settled in USDC
   */
  public static final BitgetFuturesProductType USDC_FUTURES = new BitgetFuturesProductType("USDC-FUTURES");
  /**
   * Coin-M Futures, Futures settled in cryptocurrencies
   */
  public static final BitgetFuturesProductType COIN_FUTURES = new BitgetFuturesProductType("COIN-FUTURES");

  private final String code;

  private BitgetFuturesProductType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
