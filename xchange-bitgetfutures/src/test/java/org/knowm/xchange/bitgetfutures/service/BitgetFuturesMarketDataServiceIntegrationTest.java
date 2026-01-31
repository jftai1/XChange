package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetIntegrationTestParent;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesMarketDataTickerParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

class BitgetFuturesMarketDataServiceIntegrationTest extends BitgetIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    Ticker ticker = exchange.getMarketDataService().getTicker((Instrument)CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_single_ticker_productType() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    Ticker ticker = exchange.getMarketDataService().getTicker((Instrument)CurrencyPair.BTC_USDT,BitgetFuturesProductType.USDT_FUTURES);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }


  @Test
  void valid_tickers() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers)
        .allSatisfy(
            ticker -> {
              assertThat(ticker.getInstrument()).isNotNull();
              assertThat(ticker.getLast()).isNotNull();

              if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
                assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
              }
            });
  }

  @Test
  void valid_tickers_productType() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    BitgetFuturesMarketDataTickerParams params = new BitgetFuturesMarketDataTickerParams(BitgetFuturesProductType.USDT_FUTURES);
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(params);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers)
        .allSatisfy(
            ticker -> {
              assertThat(ticker.getInstrument()).isNotNull();
              assertThat(ticker.getLast()).isNotNull();

              if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
                assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
              }
            });
  }

  @Test
  void valid_recent_candles_last_24hours() throws IOException {
    CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    Date startDate = Date.from(Instant.now().minus(24, ChronoUnit.HOURS));
    Date endDate = Date.from(Instant.now());
    int limit = 100;

    // Set Exchange specific params
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    exchange.setExchangeDefaultCandlePriceType(BitgetFuturesCandlePriceType.MARK);

    BitgetFuturesCandleStickPeriodType periodType = BitgetFuturesCandleStickPeriodType.CANDLE_STICK_1H;

    CandleStickDataParams params = new DefaultCandleStickParamWithLimit(startDate,
        endDate,
        periodType.getPeriodInSeconds(),
        limit);
    CandleStickData candleStickData = exchange.getMarketDataService()
        .getCandleStickData(currencyPair, params);
    assertThat(candleStickData.getInstrument().getBase()).isEqualTo(CurrencyPair.BTC_USDT.base);
    assertThat(candleStickData.getInstrument().getCounter()).isEqualTo(
        CurrencyPair.BTC_USDT.counter);
    assertThat(candleStickData.getCandleSticks().size()).isEqualTo(24);
  }

  @Test
  void valid_history_candles_from_60_days_ago() throws IOException {
    CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    Instant now = Instant.now();
    Date endDate = Date.from(now.minus(60, ChronoUnit.DAYS));
    Date startDate = Date.from(endDate.toInstant().minus(24, ChronoUnit.HOURS));
    int limit = 24;

    // Set Exchange specific params
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    exchange.setExchangeDefaultCandlePriceType(BitgetFuturesCandlePriceType.MARK);

    BitgetFuturesCandleStickPeriodType periodType = BitgetFuturesCandleStickPeriodType.CANDLE_STICK_1H;

    CandleStickDataParams params = new DefaultCandleStickParamWithLimit(startDate,
        endDate,
        periodType.getPeriodInSeconds(),
        limit);

    CandleStickData candleStickData = exchange.getMarketDataService()
        .getCandleStickData(currencyPair, params);
    assertThat(candleStickData.getInstrument().getBase()).isEqualTo(CurrencyPair.BTC_USDT.base);
    assertThat(candleStickData.getInstrument().getCounter()).isEqualTo(
        CurrencyPair.BTC_USDT.counter);
    assertThat(candleStickData.getCandleSticks().size()).isEqualTo(24);
  }


}
