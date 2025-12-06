package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.config.Config;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.marketdata.BitgetCandleDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto.Status;
import org.knowm.xchange.bitget.dto.marketdata.BitgetTickerDto;
import org.knowm.xchange.bitget.service.params.BitgetCandleStickHistoryParams;
import org.knowm.xchange.bitget.service.params.BitgetCandleStickParams;
import org.knowm.xchange.bitget.service.params.BitgetCandleStickParamsFactory;
import org.knowm.xchange.bitget.service.params.BitgetCandleStickRecentParams;
import org.knowm.xchange.bitgetfutures.BitgetFutures;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;

public class BitgetFuturesMarketDataService extends BitgetFuturesMarketDataServiceRaw
    implements MarketDataService {

  public BitgetFuturesMarketDataService(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      Instant serverTime = getBitgetServerTime().getServerTime();
      Instant localTime = Instant.now(Config.getInstance().getClock());

      // timestamps shouldn't diverge by more than 10 minutes
      if (Duration.between(serverTime, localTime).toMinutes() < 10) {
        return ExchangeHealth.ONLINE;
      }
    } catch (BitgetFuturesException | IOException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.OFFLINE;
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      BitgetFuturesAdapters.adaptTicker();
    } catch (BitgetFuturesException e) {
      // TODO
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      List<Ticker>tickers = new ArrayList<Ticker>();

      if (params instanceof CurrencyPairsParam) {
        for (CurrencyPair currencyPair : ((CurrencyPairsParam) params).getCurrencyPairs()) {
          tickers.addAll(getBitgetTickerDtos(currencyPair).stream()
              .map(BitgetAdapters::toTicker)
              .filter(Objects::nonNull)
              .collect(Collectors.toList()));
        }
        return tickers;
      }

      if (params instanceof InstrumentsParams) {
        for (Instrument instrument : ((InstrumentsParams) params).getInstruments()) {
          tickers.addAll(getBitgetTickerDtos(instrument).stream()
              .map(BitgetAdapters::toTicker)
              .filter(Objects::nonNull)
              .collect(Collectors.toList()));
        }
        return tickers;
      }

      return getBitgetTickerDtos(null).stream()
          .map(BitgetAdapters::toTicker)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  /**
   * @implNote As a rule candles from more 30 days back are taken from the candle history service.
   * @see MarketDataService#getCandleStickData(CurrencyPair, CandleStickDataParams)
   */
  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {

    BitgetCandleStickParams bitgetParams = BitgetCandleStickParamsFactory.createBitgetCandleStickParams(params);

    List<BitgetCandleDto> bitgetCandleDtos = null;
    if (bitgetParams instanceof BitgetCandleStickRecentParams) {
      bitgetCandleDtos = getBitgetRecentCandleDtos(currencyPair,
          bitgetParams.getPeriodType(),
          bitgetParams.getStartDate(),
          bitgetParams.getEndDate(),
          bitgetParams.getLimit());
    }else if (bitgetParams instanceof BitgetCandleStickHistoryParams){
      bitgetCandleDtos = getBitgetCandleHistoryDtos(currencyPair,
          bitgetParams.getPeriodType(),
          bitgetParams.getEndDate(),
          bitgetParams.getLimit());
    }
    return BitgetAdapters.toCandleStickData(currencyPair, bitgetCandleDtos);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    Objects.requireNonNull(instrument);

    try {
      return BitgetAdapters.toOrderBook(getBitgetMarketDepthDtos(instrument), instrument);
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }
}
