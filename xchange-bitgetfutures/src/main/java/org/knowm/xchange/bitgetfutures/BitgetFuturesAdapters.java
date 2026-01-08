package org.knowm.xchange.bitgetfutures;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitgetfutures.derivative.BitgetFuturesContract;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceInfoDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesCandleDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesLimitOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderStatus;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderTimeInForce;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderType;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceOrderDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;


@UtilityClass
public class BitgetFuturesAdapters {

  /**
   * Maintain a map of symbol to currency pair.
   */
  private final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();

  public void putSymbolMapping(String symbol, CurrencyPair currencyPair) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, currencyPair);
  }

  public CurrencyPair toCurrencyPair(String symbol) {
    return SYMBOL_TO_CURRENCY_PAIR.get(symbol);
  }

  public Ticker toTicker(BitgetFuturesTickerDto bitgetFuturesTickerDto) {
    if (bitgetFuturesTickerDto == null) {
      return null;
    }
    Ticker.Builder builder = new Ticker.Builder();
    builder.instrument(toInstrument(bitgetFuturesTickerDto));
    builder.last(bitgetFuturesTickerDto.getLastPrice());
    builder.ask(bitgetFuturesTickerDto.getBestAskPrice());
    builder.askSize(bitgetFuturesTickerDto.getBestAskSize());
    builder.bid(bitgetFuturesTickerDto.getBestBidPrice());
    builder.bidSize(bitgetFuturesTickerDto.getBestBidSize());
    builder.high(bitgetFuturesTickerDto.getHigh24h());
    builder.low(bitgetFuturesTickerDto.getLow24h());
    builder.volume(bitgetFuturesTickerDto.getAssetVolume24h());
    builder.quoteVolume(bitgetFuturesTickerDto.getQuoteVolume24h());
    builder.timestamp(java.util.Date.from(bitgetFuturesTickerDto.getTimestamp()));
    return builder.build();
  }

  public Instrument toInstrument(BitgetFuturesTickerDto bitgetFuturesTickerDto) {
    return SYMBOL_TO_CURRENCY_PAIR.get(bitgetFuturesTickerDto.getSymbol());
  }

  public InstrumentMetaData toInstrumentMetaData(BitgetFuturesContractDto bitgetFuturesContractDto) {
    InstrumentMetaData.Builder builder =
        new InstrumentMetaData.Builder()
            .minimumAmount(bitgetFuturesContractDto.getMinTradeNum());
    return builder.build();
  }

  public FuturesContract toFuturesContract(CurrencyPair currencyPair) {
      return new BitgetFuturesContract(currencyPair);
  }

  public String toProductTypeString(FuturesContract future) {
    return future.getBase().getCurrencyCode() + "-FUTURES".toUpperCase();
  }

  public String toSymbolString(Instrument instrument) {
    return instrument == null
        ? null
        : (instrument.getBase().toString() + instrument.getCounter().toString()).toUpperCase();
  }

  public CandleStickData toCandleStickData(CurrencyPair currencyPair, List<BitgetFuturesCandleDto> bitgetCandleDtos) {
    return new CandleStickData(
        currencyPair,
        bitgetCandleDtos.stream()
            .map(dto -> new CandleStick.Builder()
                .timestamp(Optional.ofNullable(dto.getTimestamp()).map(Date::from).orElse(null))
                .open(dto.getEntryPrice())
                .close(dto.getExitPrice())
                .low(dto.getLowestPrice())
                .high(dto.getHighestPrice())
                .volume(dto.getTradingVolumneBaseCurrency())
                .quotaVolume(dto.getTradingVolumneQuoteCurrency())
                .build())
            .collect(Collectors.toList()));
  }

  public Wallet toWallet(String walletId, List<BitgetFuturesAccountBalanceInfoDto> bitgetFuturesBalanceDtos) {
    List<Balance> balances = bitgetFuturesBalanceDtos.stream()
        .map(BitgetFuturesAdapters::toBalance)
        .collect(Collectors.toList());

    return Wallet.Builder
        .from(balances)
        .id(walletId)
        .features(EnumSet.of(WalletFeature.FUTURES_TRADING))
        .build();
  }

  public Balance toBalance(BitgetFuturesAccountBalanceInfoDto balance) {
    return new Balance.Builder()
        .currency(balance.getMarginCurrency())
        .available(balance.getAvailable())
        .frozen(balance.getLocked())
        .build();
  }

  public Order toOrder(BitgetFuturesOrderHistoryDto.OrderHistoryEntry orderHistoryEntry) {
    if (orderHistoryEntry == null) {
      return null;
    }

    Instrument instrument = toCurrencyPair(orderHistoryEntry.getSymbol());
    Objects.requireNonNull(instrument);
    OrderType orderType = orderHistoryEntry.getOrderSide();

    Order.Builder builder;
    switch (orderHistoryEntry.getOrderType()) {
      case MARKET:
        builder = new MarketOrder.Builder(orderType, instrument);
        break;
      case LIMIT:
        builder = new LimitOrder.Builder(orderType, instrument).limitPrice(
            orderHistoryEntry.getPrice());
        break;
      default:
        throw new IllegalArgumentException("Can't map " + orderHistoryEntry.getOrderType());
    }

    if (orderType == OrderType.BID) {
      // buy orders fill quote
      builder.cumulativeAmount(orderHistoryEntry.getQuoteVolume());
    } else if (orderType == OrderType.ASK) {
      // sell orders fill asset
      builder.cumulativeAmount(orderHistoryEntry.getBaseVolume());
    } else {
      throw new IllegalArgumentException("Can't map " + orderType);
    }

    BigDecimal fee = orderHistoryEntry.getFee();
    if (fee != null) {
      builder.fee(fee);
    }

    return builder
        .id(String.valueOf(orderHistoryEntry.getOrderId()))
        .averagePrice(orderHistoryEntry.getPriceAvg())
        .originalAmount(orderHistoryEntry.getSize())
        .userReference(orderHistoryEntry.getClientOid())
        .timestamp(toDate(orderHistoryEntry.getCreatedAt()))
        .orderStatus(toOrderStatus(orderHistoryEntry.getStatus()))
        .build();
  }

  public OrderStatus toOrderStatus(BitgetFuturesOrderStatus bitgetOrderStatus) {
    switch (bitgetOrderStatus) {
      case PENDING:
        return OrderStatus.NEW;
      case PARTIALLY_FILLED:
        return OrderStatus.PARTIALLY_FILLED;
      case FILLED:
        return OrderStatus.FILLED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      default:
        throw new IllegalArgumentException("Can't map " + bitgetOrderStatus);
    }
  }

  public UserTrade toUserTrade(BitgetFuturesFillDto.FillEntry fillEntry) {
    BigDecimal feeAmount = (fillEntry.getFeeDetail() != null ? fillEntry.getFeeTotal() : null);
    Currency feeCurrency = (fillEntry.getFeeDetail() != null ? fillEntry.getFeeCurrency() : null);
    String orderUserReference = null;
    return new UserTrade(
        fillEntry.getOrderSide(),
        fillEntry.getBaseVolume(),
        toCurrencyPair(fillEntry.getSymbol()),
        fillEntry.getPrice(),
        toDate(fillEntry.getCreatedAt()),
        fillEntry.getTradeId(),
        fillEntry.getOrderId(),
        feeAmount,
        feeCurrency,
        orderUserReference);
  }

  public Date toDate(Instant instant) {
    return Optional.ofNullable(instant).map(Date::from).orElse(null);
  }

  public BitgetFuturesPlaceOrderDto toBitgetPlaceOrderDto(BitgetFuturesMarketOrder bitgetMarketOrder) {
    return BitgetFuturesPlaceOrderDto.builder()
        .symbol(toSymbolString(bitgetMarketOrder.getInstrument()))
        .productType(bitgetMarketOrder.getProductType().getCode())
        .marginMode(bitgetMarketOrder.getMarginMode())
        .marginCurrency(bitgetMarketOrder.getInstrument().getCounter())
        .size(bitgetMarketOrder.getOriginalAmount())
        .orderSide(bitgetMarketOrder.getType())
        .orderType(BitgetFuturesOrderType.MARKET)
        .clientOid(bitgetMarketOrder.getUserReference())
        .build();
  }

  public BitgetFuturesPlaceOrderDto toBitgetPlaceOrderDto(BitgetFuturesLimitOrder bitgetLimitOrder) {
    return BitgetFuturesPlaceOrderDto.builder()
        .symbol(toSymbolString(bitgetLimitOrder.getInstrument()))
        .productType(bitgetLimitOrder.getProductType().getCode())
        .marginMode(bitgetLimitOrder.getMarginMode())
        .marginCurrency(bitgetLimitOrder.getInstrument().getCounter())
        .size(bitgetLimitOrder.getOriginalAmount())
        .price(bitgetLimitOrder.getLimitPrice())
        .orderSide(bitgetLimitOrder.getType())
        .orderType(BitgetFuturesOrderType.LIMIT)
        .timeInForce(BitgetFuturesOrderTimeInForce.GOOD_TIL_CANCELLED)
        .clientOid(bitgetLimitOrder.getUserReference())
        .presetStopSurplusPrice(bitgetLimitOrder.getPresetStopSurplusPrice())
        .presetStopLossPrice(bitgetLimitOrder.getPresetStopLossPrice())
        .build();
  }

}
