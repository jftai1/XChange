package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceDetailDto;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceInfoDto;

public class BitgetFuturesAccountServiceRaw extends BitgetFuturesBaseService {

  public BitgetFuturesAccountServiceRaw(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public List<BitgetFuturesAccountBalanceInfoDto> getBitgetBalances(
      BitgetFuturesProductType productType) throws IOException {
    return bitgetAuthenticated
        .balances(apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            productType.getCode()
        )
        .getData();
  }

  public BitgetFuturesAccountBalanceDetailDto getBitgetBalance(
      BitgetFuturesProductType productType,
      String symbol,
      String marginCoin) throws IOException {
    return bitgetAuthenticated
        .balance(apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            buildDemoHeaderParamValue(),
            productType.getCode(),
            symbol,
            marginCoin
        )
        .getData();
  }

}
