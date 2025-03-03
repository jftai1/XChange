package org.knowm.xchange.bitget.service.params;

import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.bitget.service.BitgetCandleStickPeriodType;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

@Getter
public abstract class BitgetCandleStickParams extends DefaultCandleStickParamWithLimit {

  private BitgetCandleStickPeriodType periodType = null;

  public BitgetCandleStickParams(Date startDate, Date endDate,
      long periodInSeconds, int limit) {
    super(startDate, endDate, periodInSeconds, limit);
    this.periodType = BitgetCandleStickPeriodType.getPeriodTypeFromSeconds(periodInSeconds);
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException(
          "CandleStickPeriodType not supported;"
              + Arrays.toString(BitgetCandleStickPeriodType.getSupportedPeriodsInSeconds()));
    }
  }
}
