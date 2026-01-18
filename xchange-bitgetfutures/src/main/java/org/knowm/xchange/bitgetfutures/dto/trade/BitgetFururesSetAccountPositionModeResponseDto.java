package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFururesSetAccountPositionModeResponseDto {

  /**
   * Position mode
   * one_way_mode: one-way mode
   * hedge_mode: hedge mode
   */
  @JsonProperty("posMode")
  private BitgetFuturesPositionMode positionMode;

}
