package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderTradeSidePositionMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionMode;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;

class BitgetFuturesTradeServiceRawIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  BitgetFuturesTradeServiceRaw bitgetTradeServiceRaw =
      (BitgetFuturesTradeServiceRaw) exchange.getTradeService();

  @Test
  void valid_positions() throws IOException {
    List<BitgetFuturesPositionDto> positions = bitgetTradeServiceRaw.positions(BitgetFuturesProductType.USDT_FUTURES,
        Currency.USDT);
    // validate positions
    assertThat(positions)
        .allSatisfy(
            position -> {
              assertThat(position.getMarginCurrency()).isEqualTo(Currency.USDT);
            });
  }

  @Test
  void close_all_positions_usdt() throws IOException {
    BitgetFuturesProductType productType = BitgetFuturesProductType.USDT_FUTURES;
    List<BitgetFuturesPositionDto> positions = bitgetTradeServiceRaw.positions(productType);

    for (BitgetFuturesPositionDto position : positions) {

      OrderType orderType = ("long".equalsIgnoreCase(position.getPositionSide().getValue()) ? OrderType.ASK : OrderType.BID);
      BitgetFuturesOrderTradeSidePositionMode tradeSidePositionMode = (position.getPositionMode().equals(
          BitgetFuturesPositionMode.HEDGE_MODEPOSITION) ? BitgetFuturesOrderTradeSidePositionMode.CLOSE_POSITION : null);


      BitgetFuturesMarketOrder order = BitgetFuturesMarketOrder.builder()
          .productType(productType)
          .instrument(BitgetFuturesAdapters.toInstrument(position.getSymbol()))
          .originalAmount(position.getAvailable())
          .marginMode(position.getMarginMode())
          .type(orderType)
          .tradeSidePositionMode(tradeSidePositionMode)
//          .reduceOnly(Boolean.TRUE)
          .build();

      String orderId = exchange.getTradeService().placeMarketOrder(order);
      assertThat(orderId).isNotNull();
    }

  }


}
