package org.knowm.xchange.bitgetfutures.service;

import jakarta.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesClosePositionsResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountLeverageResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountMarginModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountPositionModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesClosePositionsParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderUpdateInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceOrderDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountLeverageParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountMarginModeParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountPositionModeParamsDto;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesQueryOrderHistoryParams;
import org.knowm.xchange.bitgetfutures.service.params.BitgetFuturesTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.instrument.Instrument;

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

  public BitgetFuturesOrderUpdateInfoDto createOrder(BitgetFuturesPlaceOrderDto bitgetPlaceOrderDto)
      throws IOException {
    return bitgetAuthenticated
        .createOrder(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            bitgetPlaceOrderDto)
        .getData();
  }

  public List<BitgetFuturesPositionDto> positions(
      BitgetFuturesProductType futuresProductType)  throws IOException{
    return positions(futuresProductType, null);
  }

  public List<BitgetFuturesPositionDto> positions(
      BitgetFuturesProductType futuresProductType,
      Currency currency)  throws IOException{
    String marginCoin = (currency == null ? null : currency.getCurrencyCode());
    return bitgetAuthenticated
        .positions(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            futuresProductType.getCode(),
            marginCoin)
        .getData();
  }

  public BitgetFururesClosePositionsResponseDto closePositions(
      BitgetFuturesClosePositionsParamsDto bitgetFuturesClosePositionsParamsDto)
      throws IOException {
    return bitgetAuthenticated
        .closePositions(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            bitgetFuturesClosePositionsParamsDto)
        .getData();
  }

  public BitgetFururesSetAccountPositionModeResponseDto setAccountPositionMode(
      BitgetFuturesSetAccountPositionModeParamsDto bitgetFuturesSetAccountPositionModeParamsDto)
      throws IOException {
    return bitgetAuthenticated
        .setAccountPositionMode(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            bitgetFuturesSetAccountPositionModeParamsDto)
        .getData();
  }

  public BitgetFururesSetAccountMarginModeResponseDto setAccountMarginMode(
      BitgetFuturesSetAccountMarginModeParamsDto bitgetFuturesSetAccountMarginModeParamsDto)
      throws IOException {
    return bitgetAuthenticated
        .setAccountMarginMode(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            bitgetFuturesSetAccountMarginModeParamsDto)
        .getData();
  }

  public BitgetFururesSetAccountLeverageResponseDto setAccountLeverage(
      BitgetFuturesSetAccountLeverageParamsDto bitgetFuturesSetAccountLeverageParamsDto)
      throws IOException {
    return bitgetAuthenticated
        .setAccountLeverage(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            bitgetFuturesSetAccountLeverageParamsDto)
        .getData();
  }

}
