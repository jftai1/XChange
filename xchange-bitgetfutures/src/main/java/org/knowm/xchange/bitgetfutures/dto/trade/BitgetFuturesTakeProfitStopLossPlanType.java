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
public enum BitgetFuturesTakeProfitStopLossPlanType {

  PROFIT_PLAN("profit_plan"),
  LOSS_PLAN("loss_plan"),
  MOVING_PLAN("moving_plan"),
  POS_PROFIT("pos_profit"),
  POS_LOSS("pos_loss");

  private static final Map<String, BitgetFuturesTakeProfitStopLossPlanType> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesTakeProfitStopLossPlanType::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesTakeProfitStopLossPlanType getPlanType(String s) {
    BitgetFuturesTakeProfitStopLossPlanType value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown order type: " + s);
    }
    return value;
  }

}
