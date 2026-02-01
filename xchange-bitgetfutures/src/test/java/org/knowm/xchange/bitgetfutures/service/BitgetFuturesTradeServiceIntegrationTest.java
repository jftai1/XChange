package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesClosePositionsResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesClosePositionsParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarginMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderTriggerPriceType;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesStopOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.MarketOrderBuilder;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder.Intention;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;

class BitgetFuturesTradeServiceIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  @Test
  void get_orders_udst() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    DefaultQueryOrderParam params = new DefaultQueryOrderParam();
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    if (orders != null) {
      assertThat(orders).allSatisfy(order -> {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getInstrument()).isNotNull();
        assertThat(order.getType()).isNotNull();
        assertThat(order.getStatus()).isNotNull();
        assertThat(order.getOriginalAmount()).isNotNull();
        assertThat(order.getCumulativeAmount()).isNotNull();
        assertThat(order.getAveragePrice()).isNotNull();
        assertThat(order.getTimestamp()).isNotNull();
      });
    }
  }

  @Test
  void get_orders_udst_btc_currency_pair() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    DefaultQueryOrderParamCurrencyPair params = new DefaultQueryOrderParamCurrencyPair(
        CurrencyPair.BTC_USDT,
        null
    );
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    if (orders != null) {
      assertThat(orders).allSatisfy(order -> {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(order.getType()).isNotNull();
        assertThat(order.getStatus()).isNotNull();
        assertThat(order.getOriginalAmount()).isNotNull();
        assertThat(order.getCumulativeAmount()).isNotNull();
        assertThat(order.getAveragePrice()).isNotNull();
        assertThat(order.getTimestamp()).isNotNull();
      });
    }
  }

  @Test
  void get_orders_udst_btc_instrument() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    DefaultQueryOrderParamInstrument params = new DefaultQueryOrderParamInstrument(
        CurrencyPair.BTC_USDT,
        null
    );
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    if (orders != null) {
      assertThat(orders).allSatisfy(order -> {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(order.getType()).isNotNull();
        assertThat(order.getStatus()).isNotNull();
        assertThat(order.getOriginalAmount()).isNotNull();
        assertThat(order.getCumulativeAmount()).isNotNull();
        assertThat(order.getAveragePrice()).isNotNull();
        assertThat(order.getTimestamp()).isNotNull();
      });
    }
  }

  @Test
  void get_single_order_udst() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    DefaultQueryOrderParam params = new DefaultQueryOrderParam();
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    if (orders != null && !orders.isEmpty()) {
      Order order = orders.stream().findFirst().get();
      assertThat(order).isNotNull();
      Optional<Order> foundOrder = Optional.empty();
      DefaultQueryOrderParam paramsQuerySingleOrder = new DefaultQueryOrderParam(
          order.getId()
      );
      for (Order order1 : exchange.getTradeService().getOrder(paramsQuerySingleOrder)) {
        foundOrder = Optional.of(order1);
        break;
      }
      Order singleOrder = foundOrder.get();
      assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }
  }

  @Test
  void get_trade_history_udst() throws IOException {
    BitgetFuturesTradeHistoryParams params = BitgetFuturesTradeHistoryParams.builder()
        .productType(BitgetFuturesProductType.USDT_FUTURES)
        .build();
    UserTrades userTrades = exchange.getTradeService().getTradeHistory(params);
    if (userTrades != null) {
      assertThat(userTrades.getUserTrades()).allSatisfy(trade -> {
        assertThat(trade.getType()).isNotNull();
        assertThat(trade.getOriginalAmount()).isNotNull();
        assertThat(trade.getInstrument()).isNotNull();
        assertThat(trade.getPrice()).isNotNull();
        assertThat(trade.getTimestamp()).isNotNull();
        assertThat(trade.getId()).isNotNull();
        assertThat(trade.getOrderId()).isNotNull();
        assertThat(trade.getFeeAmount()).isNotNull();
        assertThat(trade.getFeeCurrency()).isEqualTo(Currency.USDT);
      });
    }
  }

  @Test
  void place_market_buy_and_sell_orders() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    exchange.setExchangeDefaultMarginMode(BitgetFuturesMarginMode.ISOLATED);
    String buyOrderReference = UUID.randomUUID().toString();
    String sellOrderReference = UUID.randomUUID().toString();
    double amount = 0.001;
    // BUY Order
    MarketOrder buyMarketOrder = MarketOrderBuilder.builder()
        .type(OrderType.BID)
        .originalAmount(BigDecimal.valueOf(amount))
        .instrument(CurrencyPair.BTC_USDT)
        .userReference(buyOrderReference)
        .build().toMarketOrder();
    // SELL Order
    MarketOrder sellMarketOrder = MarketOrderBuilder.builder()
        .type(OrderType.ASK)
        .originalAmount(BigDecimal.valueOf(amount))
        .instrument(CurrencyPair.BTC_USDT)
        .userReference(sellOrderReference)
        .build().toMarketOrder();

    // Place buy order
    String buyOrderId = exchange.getTradeService().placeMarketOrder(buyMarketOrder);
    assertThat(buyOrderId).isNotNull();
    // Query the BUY order
    DefaultQueryOrderParam buyOrderParams = new DefaultQueryOrderParam(
        buyOrderId
    );
    Awaitility.await()
        .atMost(20, TimeUnit.MINUTES)
        .pollInterval(2, java.util.concurrent.TimeUnit.SECONDS)
        .untilAsserted(() -> {
          Collection<Order> buyOrders = exchange.getTradeService().getOrder(buyOrderParams);
          assertThat(buyOrders).size().isEqualTo(1);
          // Log order
          logger.info("Order: {}", buyOrders.stream().findFirst().get());
          assertThat(buyOrders.stream().findFirst().get().getId()).isEqualTo(buyOrderId);
          assertThat(buyOrders.stream().findFirst().get().getOriginalAmount()).isEqualTo(
              BigDecimal.valueOf(amount));
          assertThat(buyOrders.stream().findFirst().get().getInstrument()).isEqualTo(
              CurrencyPair.BTC_USDT);
          assertThat(buyOrders.stream().findFirst().get().getUserReference()).isEqualTo(
              buyOrderReference);
          assertThat(buyOrders.stream().findFirst().get().getStatus()).isEqualTo(
              Order.OrderStatus.FILLED);
          // Place SELL order
          String sellOrderId = exchange.getTradeService().placeMarketOrder(sellMarketOrder);
          DefaultQueryOrderParam sellOrderParams = new DefaultQueryOrderParam(
              sellOrderId
          );
          Collection<Order> sellOrders = exchange.getTradeService().getOrder(sellOrderParams);
          assertThat(sellOrders).size().isEqualTo(1);
          assertThat(sellOrders.stream().findFirst().get().getId()).isEqualTo(sellOrderId);
          assertThat(sellOrders.stream().findFirst().get().getOriginalAmount()).isEqualTo(
              BigDecimal.valueOf(amount));
          assertThat(sellOrders.stream().findFirst().get().getInstrument()).isEqualTo(
              CurrencyPair.BTC_USDT);
          assertThat(sellOrders.stream().findFirst().get().getUserReference()).isEqualTo(
              sellOrderReference);
          assertThat(sellOrders.stream().findFirst().get().getStatus()).isEqualTo(
              Order.OrderStatus.FILLED);
        });
  }

  @Test
  void place_market_buy_with_stop_loss_order_close_position() throws IOException {
    String buyOrderReference = UUID.randomUUID().toString();
    double amount = 0.001;

    Date timestamp = null;
    MarketOrder buyMarketOrder = MarketOrderBuilder.builder()
        .type(OrderType.BID)
        .originalAmount(BigDecimal.valueOf(amount))
        .instrument(CurrencyPair.BTC_USDT)
        .userReference(buyOrderReference)
        .build().toMarketOrder();

    // Buy market order
    String buyOrderId = exchange.getTradeService().placeMarketOrder(buyMarketOrder);
    assertThat(buyOrderId).isNotNull();

    Awaitility.await()
        .atMost(20, TimeUnit.MINUTES)
        .pollInterval(2, java.util.concurrent.TimeUnit.SECONDS)
        .untilAsserted(() -> {
          // Query order to get
          DefaultQueryOrderParam buyOrderParams = new DefaultQueryOrderParam(
              buyOrderId
          );
          Collection<Order> orders = exchange.getTradeService().getOrder(buyOrderParams);
          assertThat(orders).size().isEqualTo(1);
          assertThat(orders.stream().findFirst().get().getStatus()).isEqualTo(
              Order.OrderStatus.FILLED);

          BigDecimal averagePrice = orders.stream().findFirst().get().getAveragePrice();
          BigDecimal stopLossPrice = averagePrice.multiply(BigDecimal.valueOf(0.98)).setScale(0,
              RoundingMode.HALF_UP);

          BitgetFuturesStopOrder stopLossOrder = BitgetFuturesStopOrder.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES)
              .instrument(CurrencyPair.BTC_USDT)
              .intention(Intention.STOP_LOSS)
              .originalAmount(BigDecimal.valueOf(amount))
              .type(OrderType.EXIT_BID)
              .triggerPriceType(BitgetFuturesOrderTriggerPriceType.MARK_PRICE)
              .stopPrice(stopLossPrice)
              .build();

          String stopLossOrderId = exchange.getTradeService().placeStopOrder(stopLossOrder);
          assertThat(stopLossOrderId).isNotNull();

          BitgetFuturesClosePositionsParamsDto closePositionParams = BitgetFuturesClosePositionsParamsDto.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES.getCode())
              .build();
          BitgetFururesClosePositionsResponseDto closePositionResponse = ((BitgetFuturesTradeServiceRaw) exchange.getTradeService()).closePositions(
              closePositionParams);
          assertThat(closePositionResponse.getSuccessList()).isNotEmpty();
        });
  }

  @Test
  void place_market_sell_with_stop_loss_order_close_position() throws IOException {
    String buyOrderReference = UUID.randomUUID().toString();
    double amount = 0.001;

    BitgetFuturesMarketOrder sellMarketOrder =
        BitgetFuturesMarketOrder.builder()
            .productType(BitgetFuturesProductType.USDT_FUTURES)
            .instrument(CurrencyPair.BTC_USDT)
            .marginMode(BitgetFuturesMarginMode.ISOLATED)
            .originalAmount(BigDecimal.valueOf(amount))
            .type(OrderType.ASK)
            .userReference(buyOrderReference)
            .build();
    // Buy market order
    String sellOrderId = exchange.getTradeService().placeMarketOrder(sellMarketOrder);
    assertThat(sellOrderId).isNotNull();

    Awaitility.await()
        .atMost(20, TimeUnit.MINUTES)
        .pollInterval(2, java.util.concurrent.TimeUnit.SECONDS)
        .untilAsserted(() -> {
          // Query order to get
          BitgetFuturesQueryOrderHistoryParams queryButOrderParams = BitgetFuturesQueryOrderHistoryParams.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES)
              .orderId(sellOrderId)
              .build();
          Collection<Order> orders = exchange.getTradeService().getOrder(queryButOrderParams);
          assertThat(orders).size().isEqualTo(1);
          assertThat(orders.stream().findFirst().get().getStatus()).isEqualTo(
              Order.OrderStatus.FILLED);

          BigDecimal averagePrice = orders.stream().findFirst().get().getAveragePrice();
          BigDecimal stopLossPrice = averagePrice.multiply(BigDecimal.valueOf(1.02)).setScale(0,
              RoundingMode.HALF_UP);

          BitgetFuturesStopOrder stopLossOrder = BitgetFuturesStopOrder.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES)
              .instrument(CurrencyPair.BTC_USDT)
              .intention(Intention.STOP_LOSS)
              .originalAmount(BigDecimal.valueOf(amount))
              .type(OrderType.EXIT_ASK)
              .triggerPriceType(BitgetFuturesOrderTriggerPriceType.MARK_PRICE)
              .stopPrice(stopLossPrice)
              .build();

          String stopLossOrderId = exchange.getTradeService().placeStopOrder(stopLossOrder);
          assertThat(stopLossOrderId).isNotNull();

          BitgetFuturesClosePositionsParamsDto closePositionParams = BitgetFuturesClosePositionsParamsDto.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES.getCode())
              .build();
          BitgetFururesClosePositionsResponseDto closePositionResponse = ((BitgetFuturesTradeServiceRaw) exchange.getTradeService()).closePositions(
              closePositionParams);
          assertThat(closePositionResponse.getSuccessList()).isNotEmpty();

        });
  }

}