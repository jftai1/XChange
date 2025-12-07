package org.knowm.xchange.bitgetfutures.service.params;

import java.util.Collection;
import lombok.Getter;
import org.knowm.xchange.bitgetfutures.BitgetFuturesProductType;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;

@Getter
public class BitgetFuturesMarketDataTickerParams implements InstrumentsParams {

  private final BitgetFuturesProductType futuresProductType;
  private final Collection<Instrument> instruments;

  public BitgetFuturesMarketDataTickerParams(BitgetFuturesProductType futuresProductType,
      Collection<Instrument> instruments) {
    this.futuresProductType = futuresProductType;
    this.instruments = instruments;
  }

  @Override
  public Collection<Instrument> getInstruments() {
    return instruments;
  }
}
