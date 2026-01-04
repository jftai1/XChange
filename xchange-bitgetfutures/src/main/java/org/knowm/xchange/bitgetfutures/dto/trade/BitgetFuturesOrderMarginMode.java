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
public enum BitgetFuturesOrderMarginMode implements IOrderFlags {

  CROSSED("crossed"),
  ISOLATED("isolated");

  private static final Map<String, BitgetFuturesOrderMarginMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderMarginMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderMarginMode getMarginMode(String s) {
    BitgetFuturesOrderMarginMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order margin mode: " + s);
    }
    return value;
  }

}
