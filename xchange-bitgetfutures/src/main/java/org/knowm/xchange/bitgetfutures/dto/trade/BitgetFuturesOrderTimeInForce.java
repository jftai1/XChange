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
public enum BitgetFuturesOrderTimeInForce {

  FILL_OR_KILL("fok"),
  GOOD_TIL_CANCELLED("gtc"),
  IMMEDIATE_OR_CANCEL("ioc"),
  POST_ONLY("post_only");

  private static final Map<String, BitgetFuturesOrderTimeInForce> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderTimeInForce::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderTimeInForce getTimeInForce(String s) {
    BitgetFuturesOrderTimeInForce value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown time in force: " + s);
    }
    return value;
  }

}
