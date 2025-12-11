package org.knowm.xchange.bitgetfutures.service.params;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleStickPeriodType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

@Getter
public abstract class BitgetFuturesCandleStickParams extends DefaultCandleStickParamWithLimit {
  /**
   * Product type
   */
  private final BitgetFuturesProductType productType = null;
  /**
   * K-line particle size (Granularity
   */
  private BitgetFuturesCandleStickPeriodType periodType = null;

  public BitgetFuturesCandleStickParams(
      Date startDate,
      Date endDate,
      long periodInSeconds,
      int limit,
      BitgetFuturesProductType productType,
      BitgetFuturesCandleStickPeriodType periodType) {
    super(startDate, endDate, periodInSeconds, limit);
    // Product type is mandatory
    Objects.requireNonNull(productType);
    // Period type is mandatory
    Objects.requireNonNull(periodType);
    // Period type is mandatory
    this.periodType = BitgetFuturesCandleStickPeriodType.getPeriodTypeFromSeconds(periodInSeconds);
    // Validate period type
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException(
          "CandleStickPeriodType not supported;"
              + Arrays.toString(BitgetFuturesCandleStickPeriodType.getSupportedPeriodsInSeconds()));
    }
    // Validate time query
    if (startDate.after(endDate)) {
      throw new IllegalArgumentException("Start date must be before end date");
    }
    long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    if (days > 90) {
      throw new IllegalArgumentException("Candlestick query period must be less than 90 days");
    }
  }
}
