package org.knowm.xchange.bitgetfutures.service.params;

import lombok.Getter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.service.marketdata.params.Params;

@Getter
public class BitgetFuturesMarketDataTickerParams implements Params {

  private final BitgetFuturesProductType futuresProductType;

  public BitgetFuturesMarketDataTickerParams(BitgetFuturesProductType futuresProductType) {
    this.futuresProductType = futuresProductType;
  }
}
