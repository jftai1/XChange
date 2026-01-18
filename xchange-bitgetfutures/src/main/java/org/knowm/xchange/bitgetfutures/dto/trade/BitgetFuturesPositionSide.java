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
public enum BitgetFuturesPositionSide {

  LONG("long"),
  SHORT("short"),
  BUY("buy"),
  SELL("sell");

  private static final Map<String, BitgetFuturesPositionSide> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesPositionSide::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesPositionSide getPositionSide(String s) {
    BitgetFuturesPositionSide value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown position direction: " + s);
    }
    return value;
  }

}
