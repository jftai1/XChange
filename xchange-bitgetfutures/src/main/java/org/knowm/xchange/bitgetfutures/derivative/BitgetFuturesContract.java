package org.knowm.xchange.bitgetfutures.derivative;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class BitgetFuturesContract extends FuturesContract {

  public BitgetFuturesContract(CurrencyPair currencyPair) {
    super(currencyPair, null);
  }

  public BitgetFuturesContract(CurrencyPair currencyPair, String prompt) {
    super(currencyPair, prompt);
  }

  @Deprecated
  public BitgetFuturesContract(String symbol) {
    super(symbol);
    throw new NotYetImplementedForExchangeException("Use a CurrencyPair based constructor.");
  }
}
