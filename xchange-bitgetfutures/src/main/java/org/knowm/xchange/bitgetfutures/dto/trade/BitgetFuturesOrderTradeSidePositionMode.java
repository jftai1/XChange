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
public enum BitgetFuturesOrderTradeSidePositionMode {

  OPEN_POSITION("open"),
  CLOSE_POSITION("close");

  private static final Map<String, BitgetFuturesOrderTradeSidePositionMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderTradeSidePositionMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderTradeSidePositionMode getPositionMode(String s) {
    BitgetFuturesOrderTradeSidePositionMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown trade side position mode: " + s);
    }
    return value;
  }

}
