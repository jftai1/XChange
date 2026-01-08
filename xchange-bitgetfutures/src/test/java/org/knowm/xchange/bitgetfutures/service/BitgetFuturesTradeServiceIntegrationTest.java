package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
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
  void place_market_buy_order() throws IOException {
    String uuid = UUID.randomUUID().toString();
    BitgetFuturesMarketOrder marketOrder =
        BitgetFuturesMarketOrder.builder()
            .productType(BitgetFuturesProductType.USDT_FUTURES)
            .instrument(CurrencyPair.BTC_USDT)
            .originalAmount(BigDecimal.valueOf(0.001))
            .type(OrderType.BID)
            .userReference(uuid)
        .build();

    String orderId = exchange.getTradeService().placeMarketOrder(marketOrder);
    assertThat(orderId).isNotNull();
    // Query the order
    BitgetFuturesQueryOrderHistoryParams params = BitgetFuturesQueryOrderHistoryParams.builder()
        .productType(BitgetFuturesProductType.USDT_FUTURES)
        .orderId(orderId)
        .build();
    Collection<Order> orders = exchange.getTradeService().getOrder(params);
    assertThat(orders).size().isEqualTo(1);
    assertThat(orders.stream().findFirst().get().getId()).isEqualTo(orderId);
    assertThat(orders.stream().findFirst().get().getOriginalAmount()).isEqualTo(0.001);
    assertThat(orders.stream().findFirst().get().getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(orders.stream().findFirst().get().getUserReference()).isEqualTo(uuid);
  }

}
