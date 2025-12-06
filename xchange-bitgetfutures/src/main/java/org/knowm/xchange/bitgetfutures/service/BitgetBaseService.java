package org.knowm.xchange.bitgetfutures.service;

import org.knowm.xchange.bitgetfutures.BitgetFutures;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAuthenticated;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.config.BitgetJacksonObjectMapperFactory;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitgetBaseService extends BaseExchangeService<BitgetFuturesExchange> implements BaseService {

  protected final String apiKey;
  protected final String passphrase;
  protected final BitgetFutures bitget;
  protected final BitgetFuturesAuthenticated bitgetAuthenticated;
  protected final BitgetFuturesDigest bitgetDigest;

  public BitgetBaseService(BitgetFuturesExchange exchange) {
    super(exchange);
    bitget =
        ExchangeRestProxyBuilder.forInterface(BitgetFutures.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitgetJacksonObjectMapperFactory()))
            .build();
    bitgetAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitgetFuturesAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitgetJacksonObjectMapperFactory()))
            .build();

    apiKey = exchange.getExchangeSpecification().getApiKey();
    passphrase = exchange.getExchangeSpecification().getPassword();
    bitgetDigest = BitgetFuturesDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected String buildDemoHeaderParamValue(){
    return (exchange.usingSandbox() ? "1" : null);
  }
}
