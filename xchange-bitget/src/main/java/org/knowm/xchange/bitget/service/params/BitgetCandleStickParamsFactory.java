package org.knowm.xchange.bitget.service.params;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

/**
 * Factory class to create instances of BitgetCandleStickHistoryParams or BitgetCandleStickRecentParams.
 */
public class BitgetCandleStickParamsFactory {

  /**
   * Creates Bitget candle recent or history params based on the start date value.
   * Before 60 days in the past will use create history params.
   * @param params
   * @return
   */
  public static BitgetCandleStickParams createBitgetCandleStickParams(CandleStickDataParams params) {

    // Exchange params
    Date startDate = null;
    Date endDate = null;
    long periodInSeconds = 0;
    int limit = 0;

    if (params instanceof DefaultCandleStickParamWithLimit){
      limit = (((DefaultCandleStickParamWithLimit) params).getLimit() == 0) ? null : ((DefaultCandleStickParamWithLimit) params).getLimit();
    }
    if (params instanceof DefaultCandleStickParam) {
      startDate = ((DefaultCandleStickParam)params).getStartDate();
      endDate = ((DefaultCandleStickParam)params).getEndDate();
      periodInSeconds = ((DefaultCandleStickParam)params).getPeriodInSecs();
    }

    if (startDate != null && startDate.after(Date.from(Instant.now().minus(60, ChronoUnit.DAYS)))){
        return new BitgetCandleStickRecentParams(startDate, endDate, periodInSeconds, limit);
    }else {
        return new BitgetCandleStickHistoryParams(endDate, periodInSeconds, limit);
    }
  }
}
