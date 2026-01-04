package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitgetfutures.BitgetIntegrationTestParent;
import org.knowm.xchange.bitgetfutures.derivative.BitgetFuturesContract;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.currency.CurrencyPair;

class BitgetFuturesMarketDataServiceRawIntegrationTest extends BitgetIntegrationTestParent {

  BitgetFuturesMarketDataServiceRaw bitgetMarketDataServiceRaw =
      (BitgetFuturesMarketDataServiceRaw) exchange.getMarketDataService();

  @Test
  void valid_contracts() throws IOException {
    List<BitgetFuturesContractDto> contracts = bitgetMarketDataServiceRaw.getBitgetFuturesContracts(BitgetFuturesProductType.USDT_FUTURES);

    assertThat(contracts).isNotEmpty();

    // validate contracts
    assertThat(contracts)
        .allSatisfy(
            contract -> {
              assertThat(contract.getSymbol()).isNotNull();
              assertThat(contract.getBaseCoin()).isNotNull();
              assertThat(contract.getQuoteCoin()).isNotNull();
            });
  }

  @Test
  void valid_contract_btcusdt() throws IOException {
    BitgetFuturesContract BTCUSDT = new BitgetFuturesContract(CurrencyPair.BTC_USDT);
    List<BitgetFuturesContractDto> contracts = bitgetMarketDataServiceRaw.getBitgetFuturesContracts(BitgetFuturesProductType.USDT_FUTURES,BTCUSDT);

    assertThat(contracts).isNotEmpty();

    // validate contracts
    assertThat(contracts)
        .allSatisfy(
            contract -> {
              assertThat(contract.getSymbol()).isEqualTo("BTCUSDT");
              assertThat(contract.getBaseCoin()).isEqualTo("BTC");
              assertThat(contract.getQuoteCoin()).isEqualTo("USDT");
            });
  }

}
