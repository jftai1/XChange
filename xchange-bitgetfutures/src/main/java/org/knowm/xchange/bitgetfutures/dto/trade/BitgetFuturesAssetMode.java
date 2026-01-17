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
public enum BitgetFuturesAssetMode {

  SINGLE("single"),
  UNION("union");

  private static final Map<String, BitgetFuturesAssetMode> LOOKUP =
      Arrays.stream(values())
          .collect(Collectors.toMap(BitgetFuturesAssetMode::getValue, Function.identity()));
  @JsonValue
  private final String value;

  @JsonCreator
  public static BitgetFuturesAssetMode getMode(String s) {
    BitgetFuturesAssetMode value = LOOKUP.get(s);
    if (value == null) {
      throw new IllegalArgumentException("Unknown asset mode: " + s);
    }
    return value;
  }

}
