package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.knowm.xchange.dto.Order.IOrderFlags;

@Getter
@RequiredArgsConstructor
public enum BitgetFuturesOrderPresetTriggerPriceType {

  MARKET_PRICE("fill_price"),
  MARK_PRICE("mark_price");

  private static final Map<String, BitgetFuturesOrderPresetTriggerPriceType> LOOKUP =
      Arrays.stream(values()).collect(
          Collectors.toMap(BitgetFuturesOrderPresetTriggerPriceType::getValue,
              Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderPresetTriggerPriceType getPresetTriggerPriceType(String s) {
    BitgetFuturesOrderPresetTriggerPriceType value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order preset trigger price type: " + s);
    }
    return value;
  }

}
