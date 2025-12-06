package org.knowm.xchange.bitgetfutures.service;

import java.io.Serializable;

public class BitgetFuturesProducType implements Serializable {

  private static final long serialVersionUID = 8277116217842459973L;

  /**
   * USDT-M Futures, Futures settled in USDT
   */
  public BitgetFuturesProducType USDT_FUTURES = new BitgetFuturesProducType("USDT-FUTURES");
  /**
   * USDC-M Futures, Futures settled in USDC
   */
  public BitgetFuturesProducType USDC_FUTURES = new BitgetFuturesProducType("USDC-FUTURES");
  /**
   * Coin-M Futures, Futures settled in cryptocurrencies
   */
  public BitgetFuturesProducType COIN_FUTURES = new BitgetFuturesProducType("COIN-FUTURES");

  private final String code;

  private BitgetFuturesProducType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
