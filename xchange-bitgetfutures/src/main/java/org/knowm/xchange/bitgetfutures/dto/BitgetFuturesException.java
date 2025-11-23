package org.knowm.xchange.bitgetfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BitgetFuturesException extends RuntimeException {

  @JsonProperty("code")
  Integer code;

  @JsonProperty("msg")
  String message;

  @JsonProperty("requestTime")
  Instant requestTime;

  @JsonProperty("data")
  Object data;
}
