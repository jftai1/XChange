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
public enum BitgetFuturesOrderType implements IOrderFlags {

  LIMIT("limit"),
  MARKET("market");

  private static final Map<String, BitgetFuturesOrderType> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderType::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderType getOrderType(String s) {
    BitgetFuturesOrderType value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order type: " + s);
    }
    return value;
  }

}
