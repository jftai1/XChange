package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;

class BitgetFuturesAccountServiceIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  BitgetFuturesAccountService bitgetFuturesAccountService = (BitgetFuturesAccountService) exchange.getAccountService();

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallets()).allSatisfy((walletId, wallet) -> {
      assertThat(walletId).containsIgnoringCase("FUTURES");
      assertThat(wallet.getBalances()).isNotEmpty();
    });
  }

  @Test
  void set_account_leverage_5x() throws IOException {
    exchange.setExchangeDefaultProductType(BitgetFuturesProductType.USDT_FUTURES);
    BigDecimal leverage = BigDecimal.valueOf(5);
    BigDecimal leverageSet = exchange.getAccountService()
        .setAccountLeverage(CurrencyPair.BTC_USDT, leverage);

    assertThat(leverageSet).isNotNull();
    assertThat(leverageSet).isEqualTo(leverage);
  }

}
