package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public enum BitgetFuturesOrderStpMode {

  NONE("none"),
  CANCEL_TAKER("cancel_taker"),
  CANCEL_MAKER("cancel_maker"),
  CANCEL_BOTH("cancel_both");

  private static final Map<String, BitgetFuturesOrderStpMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderStpMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderStpMode getStpMode(String s) {
    BitgetFuturesOrderStpMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order stp mode: " + s);
    }
    return value;
  }

}
