package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFururesCancelTakeProfitStopLossOrderResponseDto {

  /**
   * The collection of successfully cancelled orders.
   */
  @JsonProperty("successList")
  List<CancelTakeProfitStopLossOrderResponse> successList;

  /**
   * The collection of unsuccessfully cancelled orders.
   */
  @JsonProperty("failureList")
  List<CancelTakeProfitStopLossOrderResponse> failureList;

  @Data
  @Builder
  @Jacksonized
  public static class CancelTakeProfitStopLossOrderResponse {

    /**
     * Order ID
     */
    @JsonProperty("orderId")
    private String orderId;

    /**
     * Customize order ID
     */
    @JsonProperty("clientOid")
    private String clientOid;

    /**
     * The Symbol
     */
    @JsonProperty("symbol")
    private String symbol;

    /**
     * Failure reason
     */
    @JsonProperty("errorMsg")
    private String errorMessage;

  }
}
