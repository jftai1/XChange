package org.knowm.xchange.bitget.service.params;

import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public class BitgetCandleStickRecentParams extends BitgetCandleStickParams {

  BitgetCandleStickRecentParams(Date startDate, Date endDate,
      long periodInSeconds, int limit) {
    super(startDate, endDate, periodInSeconds, limit);
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);
    Validate.isTrue(limit <= 1000);
  }

}
