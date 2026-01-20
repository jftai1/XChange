package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BitgetFuturesOrderTriggerPriceType {

  /**
   * Exact market transaction price
   */
  MARKET_PRICE("fill_price"),
  /**
   * Calculated average reference price based on market and funding
   */
  MARK_PRICE("mark_price");

  private static final Map<String, BitgetFuturesOrderTriggerPriceType> LOOKUP =
      Arrays.stream(values()).collect(
          Collectors.toMap(BitgetFuturesOrderTriggerPriceType::getValue,
              Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderTriggerPriceType getPresetTriggerPriceType(String s) {
    BitgetFuturesOrderTriggerPriceType value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order preset trigger price type: " + s);
    }
    return value;
  }

}
