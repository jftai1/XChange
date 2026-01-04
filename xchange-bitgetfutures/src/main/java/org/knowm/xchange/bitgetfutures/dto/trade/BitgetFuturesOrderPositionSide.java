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
public enum BitgetFuturesOrderPositionSide implements IOrderFlags {

  LONG("long"),
  SHORT("short"),
  NET("net");

  private static final Map<String, BitgetFuturesOrderPositionSide> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderPositionSide::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderPositionSide getPositionDirection(String s) {
    BitgetFuturesOrderPositionSide value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown position direction: " + s);
    }
    return value;
  }

}
