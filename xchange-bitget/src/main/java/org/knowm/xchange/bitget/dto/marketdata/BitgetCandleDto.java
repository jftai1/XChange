package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitget.config.deserializer.CandleDtoDeserializer;

/**
 * Candle data
 */
@Data
@Builder
@JsonDeserialize(using = CandleDtoDeserializer.class)
public class BitgetCandleDto {

  private Instant timestamp;
  private BigDecimal openingPrice;
  private BigDecimal closingPrice;
  private BigDecimal lowestPrice;
  private BigDecimal highestPrice;
  private BigDecimal tradingVolumneBaseCurrency;
  private BigDecimal tradingVolumneQuoteCurrency;
  private BigDecimal tradingVolumneUSDT;

}
