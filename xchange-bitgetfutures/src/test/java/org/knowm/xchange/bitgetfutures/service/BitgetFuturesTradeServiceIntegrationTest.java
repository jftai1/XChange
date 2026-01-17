package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarginMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrades;

class BitgetFuturesTradeServiceIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  @Test
  void valid_query_orders_udst() throws IOException {
    BitgetFuturesQueryOrderHistoryParams params = BitgetFuturesQueryOrderHistoryParams.builder()
        .productType(BitgetFuturesProductType.USDT_FUTURES)
        .build();
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
  void valid_query_single_order_udst() throws IOException {
    BitgetFuturesQueryOrderHistoryParams params = BitgetFuturesQueryOrderHistoryParams.builder()
        .productType(BitgetFuturesProductType.USDT_FUTURES)
        .build();
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    if (orders != null && !orders.isEmpty()) {
      Order order = orders.stream().findFirst().get();
      assertThat(order).isNotNull();
      Optional<Order> foundOrder = Optional.empty();
      BitgetFuturesQueryOrderHistoryParams paramsQuerySingleOrder = BitgetFuturesQueryOrderHistoryParams.builder()
          .productType(BitgetFuturesProductType.USDT_FUTURES)
          .orderId(order.getId())
          .build();
      for (Order order1 : exchange.getTradeService().getOrder(paramsQuerySingleOrder)) {
        foundOrder = Optional.of(order1);
        break;
      }
      Order singleOrder = foundOrder.get();
      assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }
  }


  @Test
  void valid_trade_history_udst() throws IOException {
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
  void place_market_buy_and_sell_order() throws IOException {
    String buyOrderReference = UUID.randomUUID().toString();
    String sellOrderReference = UUID.randomUUID().toString();
    double amount = 0.001;
    // BUY Order
    BitgetFuturesMarketOrder buyMarketOrder =
        BitgetFuturesMarketOrder.builder()
            .productType(BitgetFuturesProductType.USDT_FUTURES)
            .instrument(CurrencyPair.BTC_USDT)
            .marginMode(BitgetFuturesMarginMode.ISOLATED)
//            .tradeSidePositionMode(BitgetFuturesOrderTradeSidePositionMode.OPEN_POSITION)
            .originalAmount(BigDecimal.valueOf(amount))
            .type(OrderType.BID)
            .userReference(buyOrderReference)
        .build();
    // SELL Order
    BitgetFuturesMarketOrder sellMarketOrder =
        BitgetFuturesMarketOrder.builder()
            .productType(BitgetFuturesProductType.USDT_FUTURES)
            .instrument(CurrencyPair.BTC_USDT)
            .marginMode(BitgetFuturesMarginMode.ISOLATED)
  //          .tradeSidePositionMode(BitgetFuturesOrderTradeSidePositionMode.CLOSE_POSITION)
            .originalAmount(BigDecimal.valueOf(amount))
            .type(OrderType.ASK)
            .userReference(sellOrderReference)
            .build();
    // Place buy order
    String buyOrderId = exchange.getTradeService().placeMarketOrder(buyMarketOrder);
    assertThat(buyOrderId).isNotNull();
    // Query the BUY order
    BitgetFuturesQueryOrderHistoryParams buyOrderParams = BitgetFuturesQueryOrderHistoryParams.builder()
        .productType(BitgetFuturesProductType.USDT_FUTURES)
        .orderId(buyOrderId)
        .build();

    Awaitility.await()
        .atMost(20, TimeUnit.MINUTES)
        .pollInterval(2, java.util.concurrent.TimeUnit.SECONDS)
        .untilAsserted(() -> {
          Collection<Order> buyOrders = exchange.getTradeService().getOrder(buyOrderParams);
          assertThat(buyOrders).size().isEqualTo(1);
          // Log order
          logger.info("Order: {}", buyOrders.stream().findFirst().get());
          assertThat(buyOrders.stream().findFirst().get().getId()).isEqualTo(buyOrderId);
          assertThat(buyOrders.stream().findFirst().get().getOriginalAmount()).isEqualTo(BigDecimal.valueOf(amount));
          assertThat(buyOrders.stream().findFirst().get().getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
          assertThat(buyOrders.stream().findFirst().get().getUserReference()).isEqualTo(buyOrderReference);
          assertThat(buyOrders.stream().findFirst().get().getStatus()).isEqualTo(Order.OrderStatus.FILLED);
          // Place SELL order
          String sellOrderId = exchange.getTradeService().placeMarketOrder(sellMarketOrder);
          BitgetFuturesQueryOrderHistoryParams sellOrderParams = BitgetFuturesQueryOrderHistoryParams.builder()
              .productType(BitgetFuturesProductType.USDT_FUTURES)
              .orderId(sellOrderId)
              .build();
          Collection<Order> sellOrders = exchange.getTradeService().getOrder(sellOrderParams);
          assertThat(sellOrders).size().isEqualTo(1);
          assertThat(sellOrders.stream().findFirst().get().getId()).isEqualTo(sellOrderId);
          assertThat(sellOrders.stream().findFirst().get().getOriginalAmount()).isEqualTo(BigDecimal.valueOf(amount));
          assertThat(sellOrders.stream().findFirst().get().getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
          assertThat(sellOrders.stream().findFirst().get().getUserReference()).isEqualTo(sellOrderReference);
          assertThat(sellOrders.stream().findFirst().get().getStatus()).isEqualTo(Order.OrderStatus.FILLED);
        });
  }



}
