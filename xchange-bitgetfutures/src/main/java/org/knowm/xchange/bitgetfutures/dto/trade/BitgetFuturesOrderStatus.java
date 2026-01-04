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
public enum BitgetFuturesOrderStatus implements IOrderFlags {

  PENDING("live"),
  PARTIALLY_FILLED("partially_filled"),
  FILLED("filled"),
  CANCELLED("cancelled");

  private static final Map<String, BitgetFuturesOrderStatus> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderStatus::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderStatus getOrderStatus(String s) {
    BitgetFuturesOrderStatus value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order status: " + s);
    }
    return value;
  }

}
