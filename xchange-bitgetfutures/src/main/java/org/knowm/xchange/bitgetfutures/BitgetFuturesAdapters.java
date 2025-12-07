package org.knowm.xchange.bitgetfutures;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;


@UtilityClass
public class BitgetFuturesAdapters {

  private final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();

  public void putSymbolMapping(String symbol, CurrencyPair currencyPair) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, currencyPair);
  }

  public CurrencyPair toCurrencyPair(String symbol) {
    return SYMBOL_TO_CURRENCY_PAIR.get(symbol);
  }

  public Ticker toTicker(BitgetFuturesTickerDto bitgetFuturesTickerDto, Instrument instrument) {
    if (bitgetFuturesTickerDto == null || instrument == null) {
      return null;
    }
    Ticker.Builder builder = new Ticker.Builder();
    builder.instrument(instrument);
    builder.last(bitgetFuturesTickerDto.getLastPrice());
    builder.ask(bitgetFuturesTickerDto.getBestAskPrice());
    builder.askSize(bitgetFuturesTickerDto.getBestAskSize());
    builder.bid(bitgetFuturesTickerDto.getBestBidPrice());
    builder.bidSize(bitgetFuturesTickerDto.getBestBidSize());
    builder.high(bitgetFuturesTickerDto.getHigh24h());
    builder.low(bitgetFuturesTickerDto.getLow24h());
    builder.volume(bitgetFuturesTickerDto.getAssetVolume24h());
    builder.quoteVolume(bitgetFuturesTickerDto.getQuoteVolume24h());
    builder.timestamp(java.util.Date.from(bitgetFuturesTickerDto.getTimestamp()));
    return builder.build();
  }

  public InstrumentMetaData toInstrumentMetaData(BitgetFuturesContractDto bitgetFuturesContractDto) {
    InstrumentMetaData.Builder builder =
        new InstrumentMetaData.Builder()
            .minimumAmount(bitgetFuturesContractDto.getMinTradeNum());
    return builder.build();
  }

  public String toProductTypeString(FuturesContract future) {
    return future.getBase().getCurrencyCode() + "-FUTURES".toUpperCase();
  }

  public String toSymbolString(Instrument instrument) {
    return instrument == null
        ? null
        : (instrument.getBase().toString() + instrument.getCounter().toString()).toUpperCase();
  }


}
