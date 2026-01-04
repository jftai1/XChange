package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * Information received after placing, updating, or cancelling an order.
 */
@Data
@Builder
@Jacksonized
public class BitgetFuturesOrderUpdateInfoDto {

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("clientOid")
  private String clientOid;

}
