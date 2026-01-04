package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
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

}
