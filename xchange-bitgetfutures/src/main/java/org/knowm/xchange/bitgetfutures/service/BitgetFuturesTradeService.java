package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitgetFuturesTradeService extends BitgetFuturesTradeServiceRaw implements TradeService {

  public BitgetFuturesTradeService(BitgetFuturesExchange exchange) {
    super(exchange);
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
}
