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
public enum BitgetFuturesMarginMode {

  CROSSED("crossed"),
  ISOLATED("isolated");

  private static final Map<String, BitgetFuturesMarginMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesMarginMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesMarginMode getMode(String s) {
    if (s == null){
      throw new IllegalArgumentException("Unknown order margin mode: null");
    }
    String key = s.trim().toLowerCase();
    BitgetFuturesMarginMode value = LOOKUP.get(key);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order margin mode: " + s);
    }
    return value;
  }

}
