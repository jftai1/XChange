package org.knowm.xchange.bitget.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetCancelOrderParamsDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("tpslType")
  private BitgetPlaceOrderDto.TpSlType tpSlType;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("clientOid")
  private String clientOid;

}
