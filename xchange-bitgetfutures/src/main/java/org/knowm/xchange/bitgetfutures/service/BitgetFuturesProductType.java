package org.knowm.xchange.bitgetfutures.service;

public enum BitgetFuturesProductType {

  /**
   * USDT-M Futures, Futures settled in USDT
   */
  USDT_FUTURES("USDT-FUTURES"),
  
  /**
   * USDC-M Futures, Futures settled in USDC
   */
  USDC_FUTURES("USDC-FUTURES"),
  
  /**
   * Coin-M Futures, Futures settled in cryptocurrencies
   */
  COIN_FUTURES("COIN-FUTURES");

  private final String code;

  BitgetFuturesProductType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
  
  /**
   * Find enum value by code string
   * @param code the product type code
   * @return the matching enum value or null if not found
   */
  public static BitgetFuturesProductType fromCode(String code) {
    if (code == null) {
      return null;
    }
    
    for (BitgetFuturesProductType type : values()) {
      if (type.code.equals(code)) {
        return type;
      }
    }
    return null;
  }
}