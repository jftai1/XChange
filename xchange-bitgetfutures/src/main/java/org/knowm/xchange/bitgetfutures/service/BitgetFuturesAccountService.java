package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesErrorAdapter;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceInfoDto;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BitgetFuturesAccountService extends BitgetFuturesAccountServiceRaw implements
    AccountService {

  public BitgetFuturesAccountService(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<Wallet> accountWallets = new ArrayList<Wallet>();
      for (BitgetFuturesProductType productType : this.exchange.getExchangeProductTypes()) {
        List<BitgetFuturesAccountBalanceInfoDto> productTypeBalances = getBitgetBalances(
            productType);
        String walletId = productType.getCode();
        Wallet wallet = BitgetFuturesAdapters.toWallet(walletId, productTypeBalances);
        accountWallets.add(wallet);
      }
      return new AccountInfo(accountWallets);
    } catch (BitgetFuturesException e) {
      throw BitgetFuturesErrorAdapter.adapt(e);
    }
  }
}
