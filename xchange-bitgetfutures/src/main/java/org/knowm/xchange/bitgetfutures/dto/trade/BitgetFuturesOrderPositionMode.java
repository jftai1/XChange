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
public enum BitgetFuturesOrderPositionMode implements IOrderFlags {

  ONE_WAY_POSITION("one_way_mode"),
  TWO_WAY_POSITION("hedge_mode");

  private static final Map<String, BitgetFuturesOrderPositionMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderPositionMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderPositionMode getPositionMode(String s) {
    BitgetFuturesOrderPositionMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown position mode: " + s);
    }
    return value;
  }

}
