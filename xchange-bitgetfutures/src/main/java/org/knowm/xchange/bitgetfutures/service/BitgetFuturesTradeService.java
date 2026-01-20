package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesErrorAdapter;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesLimitOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderUpdateInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesStopOrder;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BitgetFuturesTradeService extends BitgetFuturesTradeServiceRaw implements TradeService {

  public BitgetFuturesTradeService(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(BitgetFuturesQueryOrderHistoryParams.class, orderQueryParams[0]);
    BitgetFuturesQueryOrderHistoryParams params = (BitgetFuturesQueryOrderHistoryParams) orderQueryParams[0];

    try {
      BitgetFuturesOrderHistoryDto orderHistory = orderHistory(params);
      return orderHistory.getEntrustedList().stream()
          .map(BitgetFuturesAdapters::toOrder)
              .collect(Collectors.toList());
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Validate.isInstanceOf(BitgetFuturesTradeHistoryParams.class, params);
    BitgetFuturesTradeHistoryParams queryParams = (BitgetFuturesTradeHistoryParams) params;

    try {
      BitgetFuturesFillDto fills = fills(queryParams);
      final List<UserTrade> userTradeList = fills.getFillList().stream()
          .map(BitgetFuturesAdapters::toUserTrade)
          .collect(Collectors.toList());
      return new UserTrades(userTradeList, TradeSortType.SortByTimestamp);
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      Validate.isInstanceOf(BitgetFuturesMarketOrder.class, marketOrder);
      BitgetFuturesMarketOrder bitgetMarketOrder = (BitgetFuturesMarketOrder) marketOrder;
      return createOrder(BitgetFuturesAdapters.toBitgetPlaceOrderDto(bitgetMarketOrder)).getOrderId();
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    try{
      Validate.isInstanceOf(BitgetFuturesStopOrder.class, stopOrder);
      BitgetFuturesStopOrder bitgetFuturesStopOrder = (BitgetFuturesStopOrder) stopOrder;
      BitgetFuturesOrderUpdateInfoDto orderUpdateInfoDto = createTakeProfitStopLossOrder(
          BitgetFuturesAdapters.toBitgetPlaceOrderDto(bitgetFuturesStopOrder));
      return orderUpdateInfoDto.getOrderId();
    }
    catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      Validate.isInstanceOf(BitgetFuturesLimitOrder.class, limitOrder);
      BitgetFuturesLimitOrder bitgetLimitOrder = (BitgetFuturesLimitOrder) limitOrder;
      return createOrder(BitgetFuturesAdapters.toBitgetPlaceOrderDto(bitgetLimitOrder)).getOrderId();
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }
}
