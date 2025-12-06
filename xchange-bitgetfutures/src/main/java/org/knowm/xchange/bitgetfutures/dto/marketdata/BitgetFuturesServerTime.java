package org.knowm.xchange.bitgetfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFuturesServerTime {

  @JsonProperty("serverTime")
  private Instant serverTime;
}
