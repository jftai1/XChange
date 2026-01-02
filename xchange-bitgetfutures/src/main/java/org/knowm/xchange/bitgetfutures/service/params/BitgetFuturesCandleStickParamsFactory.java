package org.knowm.xchange.bitgetfutures.service.params;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleChartType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleStickPeriodType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;

/**
 * Factory class to create instances of BitgetCandleStickHistoryParams or BitgetCandleStickRecentParams.
 */
public class BitgetFuturesCandleStickParamsFactory {

  /**
   * Creates Bitget candle recent or history params based on the start date value.
   * Before 60 days in the past will use create history params.
   * @return
   */
  public static BitgetFuturesCandleStickParams createBitgetCandleStickParams(Date startDate,
      Date endDate,
      int limit,
      BitgetFuturesProductType productType,
      BitgetFuturesCandleStickPeriodType periodType,
      BitgetFuturesCandleChartType chartType
  ) {

    // Default limit is 100
    if (limit == 0) {
      limit = 100;
    }

    long periodInSeconds = periodType.getPeriodInSeconds();

    if (startDate != null && startDate.after(Date.from(Instant.now().minus(60, ChronoUnit.DAYS)))) {
      return new BitgetFuturesCandleStickRecentParams(startDate, endDate, periodInSeconds, limit,
          productType, chartType);
    }else{
      return new BitgetFuturesCandleStickHistoryParams(startDate, endDate, periodInSeconds, limit,
          productType);
    }
  }
}
