package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BitgetTradeService extends BitgetTradeServiceRaw implements TradeService {

  public BitgetTradeService(BitgetExchange exchange) {
    super(exchange);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    Validate.isInstanceOf(DefaultCancelOrderByInstrumentAndIdParams.class, orderParams);
    DefaultCancelOrderByInstrumentAndIdParams params = (DefaultCancelOrderByInstrumentAndIdParams) orderParams;
    try {
      String orderId = cancelOrder(
          BitgetAdapters.toBitgetCancelOrderParamsDto(params)).getOrderId();
      return (orderId != null && !orderId.isEmpty() && orderId.equalsIgnoreCase(
          params.getOrderId()));
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    String symbol = null;
    Integer limit = null;
    Long requestTime = null;
    Long startTime = null;
    Long endTime = null;
    String idLessThan = null;
    String orderId = null;
    String tpslType = null;
    Long receiveWindow = null;

    try {
      List<BitgetOrderInfoDto> results =
          bitgetAuthenticated
              .unfilledOrders(apiKey,
                  bitgetDigest,
                  passphrase,
                  exchange.getNonceFactory(),
                  symbol,
                  limit,
                  requestTime,
                  startTime,
                  endTime,
                  idLessThan,
                  orderId,
                  tpslType,
                  receiveWindow)
              .getData();
      if (results == null) {
        return new OpenOrders(Collections.emptyList());
      }
      //
      List<LimitOrder> openOrders = Collections.emptyList();
      return new OpenOrders(openOrders, results.stream()
          .filter(Objects::nonNull)
          .map(BitgetAdapters::toOrder)
          .collect(Collectors.toList()));
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }


  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(DefaultQueryOrderParam.class, orderQueryParams[0]);
    DefaultQueryOrderParam params = (DefaultQueryOrderParam) orderQueryParams[0];

    try {
      BitgetOrderInfoDto orderStatus = bitgetOrderInfoDto(params.getOrderId());
      return Collections.singletonList(BitgetAdapters.toOrder(orderStatus));
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      List<UserTrade> userTradeList =
          bitgetFills(params).stream()
              .map(BitgetAdapters::toUserTrade)
              .collect(Collectors.toList());
      return new UserTrades(userTradeList, TradeSortType.SortByID);
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      return createOrder(BitgetAdapters.toBitgetPlaceOrderDto(marketOrder)).getOrderId();
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return createOrder(BitgetAdapters.toBitgetPlaceOrderDto(limitOrder)).getOrderId();
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

}
