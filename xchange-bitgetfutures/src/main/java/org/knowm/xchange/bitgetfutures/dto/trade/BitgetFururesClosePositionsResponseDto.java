package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFururesClosePositionsResponseDto {

  /**
   * The collection of successfully closed orders
   */
  @JsonProperty("successList")
  List<ClosePositionOrderResponse> successList;

  /**
   * The collection of unsuccessfully closed orders The close order may fail when the pair is in
   * delivery or in risk control handling
   */
  @JsonProperty("failureList")
  List<ClosePositionOrderResponse> failureList;

  @Data
  @Builder
  @Jacksonized
  public static class ClosePositionOrderResponse {

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

    /**
     * Failure code
     */
    @JsonProperty("errorCode")
    private String errorCode;

  }
}
