package org.knowm.xchange.bitgetfutures.service;

import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.BitgetFuturesProperties;

/**
 * Extends this class to run authenticated integration tests.
 */
class BitgetAuthenticatedServiceIntegrationBase {

  static BitgetFuturesExchange exchange;

  @BeforeAll
  public static void credentialsPresent() throws IOException {
    // skip if there are no credentials
    BitgetFuturesProperties properties = new BitgetFuturesProperties();
    assumeThat(properties.isValid()).isTrue();

    ExchangeSpecification exSpec = new ExchangeSpecification(BitgetFuturesExchange.class);
    exSpec.setApiKey(properties.getApiKey());
    exSpec.setSecretKey(properties.getSecretKey());
    exSpec.setPassword(properties.getPassphrase());
    exSpec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);
    exchange = (BitgetFuturesExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
