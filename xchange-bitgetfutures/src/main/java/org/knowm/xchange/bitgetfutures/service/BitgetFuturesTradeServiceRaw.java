package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;

public class BitgetFuturesTradeServiceRaw extends BitgetFuturesBaseService {

  public BitgetFuturesTradeServiceRaw(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public BitgetFuturesOrderHistoryDto orderHistory(BitgetFuturesQueryOrderHistoryParams params)
      throws IOException {
    if (params == null) {
      return null;
    }
    Long from = (params.getStartTime() != null ? params.getStartTime().getTime() : null);
    Long to = (params.getEndTime() != null ? params.getEndTime().getTime() : null);
    String productType = (params.getProductType() != null ? params.getProductType().getCode()
        : null);
    String orderSource = null;

    return bitgetAuthenticated
        .orderHistory(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            params.getOrderId(),
            params.getClientOid(),
            BitgetFuturesAdapters.toSymbolString(params.getInstrument()),
            productType,
            params.getEndId(),
            orderSource,
            from,
            to,
            params.getLimit())
        .getData();
  }

  public BitgetFuturesFillDto fills(BitgetFuturesTradeHistoryParams params) throws IOException {
    if (params == null) {
      return null;
    }
    Long from = (params.getStartTime() != null ? params.getStartTime().getTime() : null);
    Long to = (params.getEndTime() != null ? params.getEndTime().getTime() : null);
    String productType = (params.getProductType() != null ? params.getProductType().getCode()
        : null);

    return bitgetAuthenticated
        .fills(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            params.getOrderId(),
            BitgetFuturesAdapters.toSymbolString(params.getInstrument()),
            productType,
            params.getEndId(),
            from,
            to,
            params.getLimit())
        .getData();
  }

}
