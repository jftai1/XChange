package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesErrorAdapter;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.config.Config;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesCandleDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesCandleStickHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesCandleStickParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesCandleStickRecentParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesMarketDataTickerParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
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

  /**
   * Bitget Futures Product Type may be specified as an argument.
   * If not BitgetExchange.DEFAULT_PRODUCT_TYPE will be used.
   * @param instrument
   * @param args BitgetFuturesProductType may be passed as an argument
   * @return
   * @throws IOException
   */
  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      BitgetFuturesProductType futuresProductType = null;
      if (args != null && args.length > 0) {
        for (Object arg : args) {
          if (arg instanceof BitgetFuturesProductType) {
            futuresProductType = (BitgetFuturesProductType) arg;
            break;
          }
        }
      }
      if (futuresProductType == null) {
        futuresProductType = exchange.getDefaultProductType();
      }

      List<BitgetFuturesTickerDto> tickerDtos = getBitgetTickerDto(futuresProductType, instrument);
      return BitgetFuturesAdapters.toTicker(tickerDtos.get(0));
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      BitgetFuturesProductType futuresProductType = null;
      Collection<Instrument> instruments = new ArrayList<Instrument>();

      if (params instanceof BitgetFuturesMarketDataTickerParams) {
        futuresProductType = ((BitgetFuturesMarketDataTickerParams) params).getFuturesProductType();
      }else {
        futuresProductType = exchange.getDefaultProductType();
      }

      return getBitgetTickerDtos(futuresProductType).stream()
          .map(BitgetFuturesAdapters::toTicker)
              .filter(Objects::nonNull)
          .collect(Collectors.toList());

    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  /**
   * @implNote As a rule candles from more 30 days back are taken from the candle history service.
   * @see MarketDataService#getCandleStickData(CurrencyPair, CandleStickDataParams)
   */
  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params)
      throws IOException {

    BitgetFuturesCandleStickParams bitgetParams = (BitgetFuturesCandleStickParams) params;

    List<BitgetFuturesCandleDto> bitgetCandleDtos = null;
    if (bitgetParams instanceof BitgetFuturesCandleStickRecentParams) {
      bitgetCandleDtos = getBitgetRecentCandleDtos(
          BitgetFuturesAdapters.toFuturesContract(currencyPair),
          bitgetParams.getProductType(),
          bitgetParams.getPeriodType(),
          ((BitgetFuturesCandleStickRecentParams) bitgetParams).getChartType(),
          bitgetParams.getStartDate(),
          bitgetParams.getEndDate(),
          bitgetParams.getLimit());
    } else if (bitgetParams instanceof BitgetFuturesCandleStickHistoryParams) {
      bitgetCandleDtos = getBitgetCandleHistoryDtos(
          BitgetFuturesAdapters.toFuturesContract(currencyPair),
          bitgetParams.getProductType(),
          bitgetParams.getPeriodType(),
          bitgetParams.getStartDate(),
          bitgetParams.getEndDate(),
          bitgetParams.getLimit());
    }
    return BitgetFuturesAdapters.toCandleStickData(currencyPair, bitgetCandleDtos);
  }
}
