package org.knowm.xchange.bitgetfutures.service;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum BitgetFuturesCandlePriceType {
  /**
   * Market price
   */
  MARKET("MARKET"),
  /** Calculated average price */
  MARK("MARK"),
  /** Futures asset reference price */
  INDEX("INDEX");

  private static final Map<String, BitgetFuturesCandlePriceType> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesCandlePriceType::getValue, Function.identity()));

  private final String value;

  BitgetFuturesCandlePriceType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  public static BitgetFuturesCandlePriceType getType(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Unknown candle price type: null");
    }
    String key = s.trim().toUpperCase();
    BitgetFuturesCandlePriceType value = LOOKUP.get(key);
    if (value == null) {
      throw new IllegalArgumentException("Unknown candle price type: " + s);
    }
    return value;
  }

}
