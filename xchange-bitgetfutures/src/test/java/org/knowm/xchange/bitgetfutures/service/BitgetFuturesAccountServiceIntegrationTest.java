package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.account.AccountInfo;

class BitgetFuturesAccountServiceIntegrationTest extends
    BitgetFuturesAuthenticatedServiceIntegrationBase {

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallets()).allSatisfy((walletId, wallet) -> {
      assertThat(walletId).containsIgnoringCase("FUTURES");
      assertThat(wallet.getBalances()).isNotEmpty();
    });
  }
}
