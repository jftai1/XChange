package org.knowm.xchange.bitgetfutures.derivative;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/**
 * Bitget futures contract.
 * Only perpetual contracts are supported.
 */
public class BitgetFuturesContract extends FuturesContract {

  public BitgetFuturesContract(CurrencyPair currencyPair) {
    super(currencyPair, "PERPETUAL");
  }

  public BitgetFuturesContract(CurrencyPair currencyPair, String prompt) {
    super(currencyPair, prompt);
    if (!prompt.matches("(?i)PERPETUAL")){
      throw new IllegalArgumentException("Only perpetual contracts are supported.");
    }
  }

  @Deprecated
  public BitgetFuturesContract(String symbol) {
    super(symbol);
    throw new NotYetImplementedForExchangeException("Use a CurrencyPair based constructor.");
  }
}
