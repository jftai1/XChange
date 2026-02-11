package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceDetailDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountLeverageResponseDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

class BitgetFuturesAccountServiceRawIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  BitgetFuturesAccountServiceRaw bitgetFuturesAccountServiceRaw = (BitgetFuturesAccountServiceRaw)exchange.getAccountService();

  @Test
  void valid_balance_btcusdt() throws IOException {
    String symbol = BitgetFuturesAdapters.toSymbolString(CurrencyPair.BTC_USDT);
    String marginCoin = Currency.USDT.getCurrencyCode();
    BitgetFuturesAccountBalanceDetailDto bitgetFuturesAccountBalanceDetailDto =
        bitgetFuturesAccountServiceRaw.getBitgetBalance(BitgetFuturesProductType.USDT_FUTURES,symbol,marginCoin);
    assertThat(bitgetFuturesAccountBalanceDetailDto).isNotNull();
    assertThat(bitgetFuturesAccountBalanceDetailDto.getMarginCurrency()).isEqualTo(Currency.USDT);
  }

  @Test
  void valid_balance_coinbtc() throws IOException {
    String symbol = BitgetFuturesAdapters.toSymbolString(CurrencyPair.BTC_USD);
    String marginCoin = Currency.BTC.getCurrencyCode();
    BitgetFuturesAccountBalanceDetailDto bitgetFuturesAccountBalanceDetailDto =
        bitgetFuturesAccountServiceRaw.getBitgetBalance(BitgetFuturesProductType.COIN_FUTURES,symbol,marginCoin);
    assertThat(bitgetFuturesAccountBalanceDetailDto).isNotNull();
    assertThat(bitgetFuturesAccountBalanceDetailDto.getMarginCurrency()).isEqualTo(Currency.BTC);
  }

  @Test
  void set_account_leverage_5x() throws IOException {
    String symbol = BitgetFuturesAdapters.toSymbolString(CurrencyPair.BTC_USD);
    String marginCoin = Currency.BTC.getCurrencyCode();
    BigDecimal leverage = BigDecimal.valueOf(5);

    BitgetFururesSetAccountLeverageResponseDto response = bitgetFuturesAccountServiceRaw.setAccountLeverage(
        BitgetFuturesProductType.USDT_FUTURES,
        symbol,
        marginCoin,
        leverage);
    assertThat(response).isNotNull();
    assertThat(response.getLongLeverage()).isEqualTo(leverage);
    assertThat(response.getShortLeveage()).isEqualTo(leverage);
  }
}
