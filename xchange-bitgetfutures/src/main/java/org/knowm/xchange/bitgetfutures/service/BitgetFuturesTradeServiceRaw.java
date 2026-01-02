package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderInfoDto;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class BitgetFuturesTradeServiceRaw extends BitgetFuturesBaseService {

  public BitgetFuturesTradeServiceRaw(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public List<BitgetFuturesOrderInfoDto> orderHistory(TradeHistoryParams params) throws IOException {
    // get arguments
    Instrument instrument =
        params instanceof TradeHistoryParamInstrument
            ? ((TradeHistoryParamInstrument) params).getInstrument()
            : null;
    Integer limit =
        params instanceof TradeHistoryParamLimit
            ? ((TradeHistoryParamLimit) params).getLimit()
            : null;
    String orderId =
        params instanceof TradeHistoryParamOrderId
            ? ((TradeHistoryParamOrderId) params).getOrderId()
            : null;
    String lastTradeId =
        params instanceof TradeHistoryParamsIdSpan
            ? ((TradeHistoryParamsIdSpan) params).getEndId()
            : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() : null;
    }

    String productType = null;
    if (params instanceof BitgetFuturesTradeHistoryParams){
      BitgetFuturesTradeHistoryParams paramsBitgetFutures = (BitgetFuturesTradeHistoryParams)params;
      productType = paramsBitgetFutures.getProductType().getCode();
    }
    String clientOid = null;
    String orderSource = null;

    return bitgetAuthenticated
        .orderHistory(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            orderId,
            clientOid,
            BitgetFuturesAdapters.toSymbolString(instrument),
            productType,
            lastTradeId,
            orderSource,
            from,
            to,
            limit)
        .getData();
  }

}
