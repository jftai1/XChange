package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesClosePositionsResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountLeverageResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountMarginModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountPositionModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesClosePositionsParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarginMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarketOrder;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderTradeSidePositionMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionMode;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountLeverageParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountMarginModeParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountPositionModeParamsDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

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

    // Get all positions for a productType
    List<BitgetFuturesPositionDto> positions = bitgetTradeServiceRaw.positions(productType);

    // If any close all positions
    if (!positions.isEmpty()) {
      BitgetFuturesClosePositionsParamsDto params = BitgetFuturesClosePositionsParamsDto.builder()
          .productType(productType.getCode())
          .build();
      BitgetFururesClosePositionsResponseDto response = bitgetTradeServiceRaw.closePositions(params);
      assertThat(response).isNotNull();
    }
  }

  @Test
  void set_account_position_mode_for_usdt() throws IOException {

    BitgetFuturesProductType productType = BitgetFuturesProductType.USDT_FUTURES;
    BitgetFuturesPositionMode positionMode = BitgetFuturesPositionMode.ONE_WAY_MODE;

    // Get all positions for a productType
    List<BitgetFuturesPositionDto> positions = bitgetTradeServiceRaw.positions(productType);

    // Set position to one way
    assertThat(positions).isEmpty();

    BitgetFuturesSetAccountPositionModeParamsDto params = BitgetFuturesSetAccountPositionModeParamsDto.builder()
            .productType(productType.getCode())
                .positionMode(positionMode)
                    .build();

    BitgetFururesSetAccountPositionModeResponseDto response = bitgetTradeServiceRaw.setAccountPositionMode(params);
    assertThat(response.getPositionMode()).isEqualTo(positionMode);

  }

  @Test
  void set_account_margin_mode_for_usdt() throws IOException {

    // Values
    BitgetFuturesProductType productType = BitgetFuturesProductType.USDT_FUTURES;
    Instrument instrument = CurrencyPair.BTC_USDT;
    BitgetFuturesMarginMode marginMode = BitgetFuturesMarginMode.ISOLATED;

    // Get all positions for a productType
    List<BitgetFuturesPositionDto> positions = bitgetTradeServiceRaw.positions(productType);

    // Set position to one way
    assertThat(positions).isEmpty();

    BitgetFuturesSetAccountMarginModeParamsDto params = BitgetFuturesSetAccountMarginModeParamsDto.builder()
        .productType(productType.getCode())
        .symbol(BitgetFuturesAdapters.toSymbolString(instrument))
        .marginCoin(BitgetFuturesAdapters.toMarginCoin(instrument))
        .marginMode(marginMode)
        .build();

    BitgetFururesSetAccountMarginModeResponseDto response = bitgetTradeServiceRaw.setAccountMarginMode(params);
    assertThat(response.getMarginMode()).isEqualTo(marginMode);
    assertThat(response.getSymbol()).isEqualTo(BitgetFuturesAdapters.toSymbolString(instrument));
    assertThat(response.getMarginCoin()).isEqualTo(BitgetFuturesAdapters.toMarginCoin(instrument));

  }

  @Test
  void set_account_leverage_for_usdt() throws IOException {

    // Values
    BitgetFuturesProductType productType = BitgetFuturesProductType.USDT_FUTURES;
    Instrument instrument = CurrencyPair.BTC_USDT;
    BigDecimal leverage = BigDecimal.valueOf(2);

    BitgetFuturesSetAccountLeverageParamsDto params = BitgetFuturesSetAccountLeverageParamsDto.builder()
        .productType(productType.getCode())
        .symbol(BitgetFuturesAdapters.toSymbolString(instrument))
        .marginCoin(BitgetFuturesAdapters.toMarginCoin(instrument))
        .leverage(leverage)
        .build();

    BitgetFururesSetAccountLeverageResponseDto response = bitgetTradeServiceRaw.setAccountLeverage(params);
    assertThat(response.getSymbol()).isEqualTo(BitgetFuturesAdapters.toSymbolString(instrument));
    assertThat(response.getMarginCoin()).isEqualTo(BitgetFuturesAdapters.toMarginCoin(instrument));
    assertThat(response.getLongLeverage()).isEqualTo(leverage);
    assertThat(response.getShortLeveage()).isEqualTo(leverage);
  }

}
