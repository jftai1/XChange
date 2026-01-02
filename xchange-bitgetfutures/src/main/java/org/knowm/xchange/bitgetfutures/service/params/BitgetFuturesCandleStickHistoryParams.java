package org.knowm.xchange.bitgetfutures.service.params;

import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleChartType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandleStickPeriodType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;

public class BitgetFuturesCandleStickHistoryParams extends BitgetFuturesCandleStickParams {

  /**
   * Constructor has package visibility to prevent direct instantiation. Use factory method instead.
   * @param endDate
   * @param periodInSeconds
   * @param limit
   * @param productType
   */
  BitgetFuturesCandleStickHistoryParams(
      Date startDate,
      Date endDate,
      long periodInSeconds,
      int limit,
      BitgetFuturesProductType productType) {
    super(startDate, endDate, periodInSeconds, limit, productType);
    Objects.requireNonNull(endDate);
    Validate.isTrue(limit <= 200);
  }

}
