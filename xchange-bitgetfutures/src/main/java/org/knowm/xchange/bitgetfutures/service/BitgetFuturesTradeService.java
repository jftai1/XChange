package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesErrorAdapter;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesLimitOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarginMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderUpdateInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesStopOrder;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BitgetFuturesTradeService extends BitgetFuturesTradeServiceRaw implements TradeService {

  public BitgetFuturesTradeService(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(DefaultQueryOrderParam.class, orderQueryParams[0]);
    DefaultQueryOrderParam params = (DefaultQueryOrderParam) orderQueryParams[0];
    Instrument instrument = null;
    if (params instanceof DefaultQueryOrderParamInstrument) {
      instrument = ((DefaultQueryOrderParamInstrument) params).getInstrument();
    } else if (params instanceof DefaultQueryOrderParamCurrencyPair) {
      instrument = ((DefaultQueryOrderParamCurrencyPair) params).getCurrencyPair();
    }

    BitgetFuturesProductType productType = exchange.getDefaultProductType();
    BitgetFuturesQueryOrderHistoryParams bitgetParams = BitgetFuturesQueryOrderHistoryParams.builder()
        .productType(productType)
        .orderId(params.getOrderId())
        .instrument(instrument)
        .build();

    try {
      BitgetFuturesOrderHistoryDto orderHistory = orderHistory(bitgetParams);
      return orderHistory.getEntrustedList().stream()
          .map(BitgetFuturesAdapters::toOrder)
              .collect(Collectors.toList());
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }

  /**
   * NotImplemented for now always returns empty list.
   *
   * @return
   * @throws IOException
   */
  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return new OpenOrders(Collections.emptyList());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    BitgetFuturesProductType productType = exchange.getDefaultProductType();
    Instrument instrument = null;
    Integer limit = null;
    String orderId = null;
    Date startTime = null;
    Date endTime = null;
    String startId = null;
    String endId = null;
    if (params instanceof TradeHistoryParamInstrument) {
      instrument = ((TradeHistoryParamInstrument) params).getInstrument();
    }
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }
    if (params instanceof TradeHistoryParamOrderId) {
      orderId = ((TradeHistoryParamOrderId) params).getOrderId();
    }
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = tradeHistoryParamsTimeSpan.getStartTime();
      endTime = tradeHistoryParamsTimeSpan.getEndTime();
    }
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan tradeHistoryParamsIdSpan = (TradeHistoryParamsIdSpan) params;
      startId = tradeHistoryParamsIdSpan.getStartId();
      endId = tradeHistoryParamsIdSpan.getEndId();
    }

    BitgetFuturesTradeHistoryParams bitgetParams = BitgetFuturesTradeHistoryParams.builder()
        .productType(productType)
        .instrument(instrument)
        .limit(limit)
        .orderId(orderId)
        .startTime(startTime)
        .endTime(endTime)
        .startId(startId)
        .endId(endId)
        .build();
    try {
      BitgetFuturesFillDto fills = fills(bitgetParams);
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
      BitgetFuturesProductType productType = exchange.getDefaultProductType();
      BitgetFuturesMarginMode marginMode = exchange.getDefaultMarginMode();
      return createOrder(BitgetFuturesAdapters.toBitgetPlaceOrderDto(marketOrder, productType,
          marginMode)).getOrderId();
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
