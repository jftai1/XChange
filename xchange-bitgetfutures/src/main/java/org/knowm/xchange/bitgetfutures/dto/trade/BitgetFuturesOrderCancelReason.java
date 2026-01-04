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
public enum BitgetFuturesOrderCancelReason implements IOrderFlags {

  NORMAL("normal_cancel"),
  STP("stp_cancel");

  private static final Map<String, BitgetFuturesOrderCancelReason> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesOrderCancelReason::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesOrderCancelReason getCancelReason(String s) {
    BitgetFuturesOrderCancelReason value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown cancel reason: " + s);
    }
    return value;
  }

}
