package org.knowm.xchange.bitget.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetCancelOrderResponseDto {

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("clientOid")
  private String clientOid;

}
