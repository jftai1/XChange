// BitgetFuturesAccountServiceTest.java
package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchangeWiremock;
import org.knowm.xchange.dto.account.AccountInfo;

class BitgetFuturesAccountServiceTest extends BitgetFuturesExchangeWiremock {
  
  @Test
  void getAccountInfo() throws Exception {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo).isNotNull();
    assertThat(accountInfo.getWallets()).isNotEmpty();
    // Vérifier les propriétés spécifiques aux comptes de futures
  }
}