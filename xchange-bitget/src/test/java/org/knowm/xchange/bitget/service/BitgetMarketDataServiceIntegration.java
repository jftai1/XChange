package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetIntegrationTestParent;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

class BitgetMarketDataServiceIntegration extends BitgetIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_currencies() throws IOException {
    List<Currency> currencies =
        ((BitgetMarketDataService) exchange.getMarketDataService()).getCurrencies();

    assertThat(currencies).isNotEmpty();
    assertThat(currencies.stream().distinct().count()).isEqualTo(currencies.size());
  }

  @Test
  void valid_instruments() throws IOException {
    List<Instrument> instruments =
        ((BitgetMarketDataService) exchange.getMarketDataService()).getInstruments();

    assertThat(instruments).isNotEmpty();
    assertThat(instruments.stream().distinct().count()).isEqualTo(instruments.size());
  }

  @Test
  void check_exceptions() {
    assertThatExceptionOfType(InstrumentNotValidException.class)
        .isThrownBy(
            () ->
                exchange
                    .getMarketDataService()
                    .getTicker(new CurrencyPair("NONEXISTING/NONEXISTING")));
  }

  @Test
  void valid_tickers() throws IOException {
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
  void valid_candles() throws IOException {
    CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    Date start = Date.from(Instant.now().minus(24, ChronoUnit.HOURS));
    Date end = Date.from(Instant.now());
    long periodInSeconds = BitgetCandleStickPeriodType.CANDLE_STICK_1H.getPeriodInSecs();
    int limit = 100;
    CandleStickDataParams candleStickDataParams = new DefaultCandleStickParamWithLimit(start,
        end,
        periodInSeconds,
        limit);
    CandleStickData candleStickData = exchange.getMarketDataService()
        .getCandleStickData(currencyPair, candleStickDataParams);
    assertThat(candleStickData.getInstrument().getBase()).isEqualTo(CurrencyPair.BTC_USDT.base);
    assertThat(candleStickData.getInstrument().getCounter()).isEqualTo(
        CurrencyPair.BTC_USDT.counter);
    assertThat(candleStickData.getCandleSticks()).isNotEmpty();
  }

  @Test
  void valid_candles_from_60_days_ago() throws IOException {
    CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    Date start = Date.from(Instant.now().minus(60, ChronoUnit.DAYS));
    Date end = Date.from(Instant.now().minus(60, ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS));
    long periodInSeconds = BitgetCandleStickPeriodType.CANDLE_STICK_1H.getPeriodInSecs();
    int limit = 100;
    CandleStickDataParams candleStickDataParams = new DefaultCandleStickParamWithLimit(start,
        end,
        periodInSeconds,
        limit);
    CandleStickData candleStickData = exchange.getMarketDataService()
        .getCandleStickData(currencyPair, candleStickDataParams);
    assertThat(candleStickData.getInstrument().getBase()).isEqualTo(CurrencyPair.BTC_USDT.base);
    assertThat(candleStickData.getInstrument().getCounter()).isEqualTo(
        CurrencyPair.BTC_USDT.counter);
    assertThat(candleStickData.getCandleSticks()).isNotEmpty();
  }

  @Test
  void valid_orderbook() throws IOException {
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.BTC_USDT);

    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isGreaterThan(orderBook.getBids().get(0).getLimitPrice());

    assertThat(orderBook.getBids())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.BID);
            });

    assertThat(orderBook.getAsks())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.ASK);
            });
  }
}
