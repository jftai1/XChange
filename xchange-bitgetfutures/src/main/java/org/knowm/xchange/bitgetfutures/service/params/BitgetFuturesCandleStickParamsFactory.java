package org.knowm.xchange.bitgetfutures.service.params;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandlePriceType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/**
 * Factory class to create instances of BitgetCandleStickHistoryParams or BitgetCandleStickRecentParams.
 */
public class BitgetFuturesCandleStickParamsFactory {

  /**
   * Creates Bitget candle recent or history params based on the start date value.
   * Before 60 days in the past will use create history params.
   * @return
   */
  public static BitgetFuturesCandleStickParams createBitgetCandleStickParams(
      CandleStickDataParams params,
      BitgetFuturesProductType productType,
      BitgetFuturesCandlePriceType candlePriceType) {

    Date startDate = null;
    Date endDate = null;
    long periodInSeconds = 0;
    int limit = 0;

    if (params instanceof DefaultCandleStickParam) {
      startDate = ((DefaultCandleStickParam) params).getStartDate();
      endDate = ((DefaultCandleStickParam) params).getEndDate();
      periodInSeconds = ((DefaultCandleStickParam) params).getPeriodInSecs();
    }
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = (((DefaultCandleStickParamWithLimit) params).getLimit() == 0) ? null
          : ((DefaultCandleStickParamWithLimit) params).getLimit();
    }

    // Default limit is 100
    if (limit == 0) {
      limit = 100;
    }

    if (startDate != null && startDate.after(Date.from(Instant.now().minus(60, ChronoUnit.DAYS)))) {
      return new BitgetFuturesCandleStickRecentParams(startDate,
          endDate,
          periodInSeconds,
          limit,
          productType,
          candlePriceType);
    } else {
      return new BitgetFuturesCandleStickHistoryParams(startDate,
          endDate,
          periodInSeconds,
          limit,
          productType);
    }
  }
}
