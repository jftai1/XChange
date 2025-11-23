package org.knowm.xchange.bitgetfutures.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitgetfutures.config.deserializer.CandleFuturesDtoDeserializer;

/**
 * Candle data
 */
@Data
@Builder
@JsonDeserialize(using = CandleFuturesDtoDeserializer.class)
public class BitgetFuturesCandleDto {

  /**
   * Milliseconds format of timestamp Unix, e.g. 1597026383085
   */
  private Instant timestamp;
  /**
   * Entry price
   */
  private BigDecimal entryPrice;
  /**
   * Exit price.
   * The latest exit price may be updated in the future.
   * Subscribe to WebSocket to track the latest price.
   */
  private BigDecimal exitPrice;
  /**
   * Lowest price
   */
  private BigDecimal lowestPrice;
  /**
   * Highest price
   */
  private BigDecimal highestPrice;
  /**
   * Trading volume of the base coin
   */
  private BigDecimal tradingVolumneBaseCurrency;
  /**
   * Trading volume of quote currency
   */
  private BigDecimal tradingVolumneQuoteCurrency;

}
