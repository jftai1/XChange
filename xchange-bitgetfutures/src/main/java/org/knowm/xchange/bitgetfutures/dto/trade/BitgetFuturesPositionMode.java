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
public enum BitgetFuturesPositionMode {

  ONE_WAY_MODE("one_way_mode"),
  HEDGE_MODE("hedge_mode");

  private static final Map<String, BitgetFuturesPositionMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesPositionMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesPositionMode getPositionMode(String s) {
    BitgetFuturesPositionMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown position mode: " + s);
    }
    return value;
  }

}
