package org.knowm.xchange.bitgetfutures.service.params;

import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleChartType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleStickPeriodType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;

@Getter
public class BitgetFuturesCandleStickRecentParams extends BitgetFuturesCandleStickParams {

  /**
   * Candlestick chart types: MARKET tick; MARK mark; INDEX index;
   * MARKET by default.
   */
  private BitgetFuturesCandleChartType chartType = null;

  /**
   * Constructor has package visibility to prevent direct instantiation. Use factory method instead.
   * @param startDate
   * @param endDate
   * @param periodInSeconds
   * @param limit
   * @param productType
   * @param periodType
   * @param chartType
   */
  BitgetFuturesCandleStickRecentParams(
      Date startDate,
      Date endDate,
      long periodInSeconds,
      int limit,
      BitgetFuturesProductType productType,
      BitgetFuturesCandleStickPeriodType periodType,
      BitgetFuturesCandleChartType chartType) {
    super(startDate, endDate, periodInSeconds, limit, productType, periodType);
    this.chartType = chartType;
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);
    Validate.isTrue(limit <= 1000);
  }


}
