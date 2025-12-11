package org.knowm.xchange.bitgetfutures.service.params;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.knowm.xchange.bitget.service.params.BitgetCandleStickHistoryParams;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleStickPeriodType;
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
   * @param params
   * @return
   */
  public static BitgetFuturesCandleStickParams createBitgetCandleStickParams(CandleStickDataParams params) {

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

    if (isHistoryParams(startDate, periodInSeconds)){
      return new BitgetFuturesCandleStickHistoryParams(startDate,endDate,periodInSeconds,limit,null,null);
    }else{
      return new BitgetFuturesCandleStickRecentParams(startDate,endDate,periodInSeconds,limit,null,null);
    }
  }

  /**
   * Determines whether the params are for history or recent candles.
   * @param startDate
   * @param periodInSeconds
   * @return true if history params, false if recent params.
   */
  public static boolean isHistoryParams(Date startDate, long periodInSeconds){
    BitgetFuturesCandleStickPeriodType periodType = BitgetFuturesCandleStickPeriodType.getPeriodTypeFromSeconds(periodInSeconds);
    Instant now = Instant.now();
    long daysBetween = ChronoUnit.DAYS.between(startDate.toInstant(), now);

    switch (periodType){
      case CANDLE_STICK_1M:
      case CANDLE_STICK_3M:
      case CANDLE_STICK_5M:
        return daysBetween > 30;
      case CANDLE_STICK_15M:
        return daysBetween > 52;
      case CANDLE_STICK_30M:
        return daysBetween > 62;
      case CANDLE_STICK_1H:
        return daysBetween > 83;
      case CANDLE_STICK_2H:
        return daysBetween > 120;
      case CANDLE_STICK_4H:
        return daysBetween > 240;
      case CANDLE_STICK_6H:
        return daysBetween > 360;
      default:
        return false;

    }



  }

}
