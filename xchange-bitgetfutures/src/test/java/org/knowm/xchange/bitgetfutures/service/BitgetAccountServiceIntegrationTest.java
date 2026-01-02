package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.account.AccountInfo;

class BitgetAccountServiceIntegrationTest extends BitgetAuthenticatedServiceIntegrationBase {

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallets()).allSatisfy((walletId, wallet) -> {
      assertThat(walletId).containsIgnoringCase("FUTURES");
      assertThat(wallet.getBalances()).isNotEmpty();
    });
  }
}
