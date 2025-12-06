package org.knowm.xchange.bitgetfutures;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;


@UtilityClass
public class BitgetFuturesAdapters {

  public Ticker adaptTicker(BitgetFuturesTickerDto bitgetFuturesTickerDto, Instrument instrument) {
    if (bitgetFuturesTickerDto == null || instrument == null) {
      return null;
    }
    Ticker.Builder builder = new Ticker.Builder();
    builder.instrument(instrument);
    return builder.build();
  }

  public String toProductTypeString(FuturesContract future) {
    return future.getBase().getCurrencyCode() + "-FUTURES".toUpperCase();
  }

  public String toSymbolString(FuturesContract future) {
    return future.getBase().getCurrencyCode().toUpperCase();
  }


}
