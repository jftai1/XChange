package org.knowm.xchange.bitget.service.params;

import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public class BitgetCandleStickHistoryParams extends BitgetCandleStickParams {

  BitgetCandleStickHistoryParams(Date endDate,
      long periodInSeconds, int limit) {
    super(null, endDate, periodInSeconds, limit);
    Objects.requireNonNull(endDate);
    Validate.isTrue(limit <= 200);
  }

}
