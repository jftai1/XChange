package org.knowm.xchange.bitgetfutures.service.params;

import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandlePriceType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;

@Getter
public class BitgetFuturesCandleStickRecentParams extends BitgetFuturesCandleStickParams {

  /**
   * Candlestick chart types: MARKET tick; MARK mark; INDEX index;
   * MARKET by default.
   */
  private BitgetFuturesCandlePriceType candlePriceType = null;

  /**
   * Constructor has package visibility to prevent direct instantiation. Use factory method instead.
   * @param startDate The start time is to query the k-lines after this time According to the different time granularity, the corresponding time unit must be rounded down to be queried. (optional)
   * @param endDate The end time is to query the k-lines before this time. According to the different time granularity, the corresponding time unit must be rounded down to be queried. (optional)
   * @param periodInSeconds granularity K-line particle size, ex: 1H(1 hour) (required)
   * @param limit Default: 100, maximum: 1000 (optional)
   * @param productType Product type (required)
   * @param candlePriceType Candlestick chart types: MARKET tick; MARK mark; INDEX index; MARKET by default (optional)
   */
  BitgetFuturesCandleStickRecentParams(
      Date startDate,
      Date endDate,
      long periodInSeconds,
      int limit,
      BitgetFuturesProductType productType,
      BitgetFuturesCandlePriceType candlePriceType) {
    super(startDate, endDate, periodInSeconds, limit, productType);
    this.candlePriceType = candlePriceType;
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);
    Validate.isTrue(limit <= 1000);
  }


}
