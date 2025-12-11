package org.knowm.xchange.bitgetfutures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesMarketDataService;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesMarketDataServiceRaw;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesExchange extends BaseExchange {

  /**
   * Exchange product types;
   */
  private List<org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType>exchangeProductTypes = Arrays.asList(
      org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType.COIN_FUTURES,
      org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType.USDC_FUTURES,
      org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType.USDT_FUTURES);

  @Override
  protected void initServices() {
//    accountService = new BitgetAccountService(this);
    marketDataService = new BitgetFuturesMarketDataService(this);
//    tradeService = new BitgetTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://api.bitget.com");
    specification.setHost("www.bitget.com");
    specification.setExchangeName("Bitget");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
    BitgetFuturesMarketDataServiceRaw bitgetMarketDataServiceRaw =
        (BitgetFuturesMarketDataServiceRaw) marketDataService;

    List<BitgetFuturesContractDto> bitgetFuturesContractDtosAll = new ArrayList<BitgetFuturesContractDto>();

    // Get all contracts
    for (org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType bitgetFuturesProductType : exchangeProductTypes) {
      List<BitgetFuturesContractDto> bitgetFuturesContractDtos = bitgetMarketDataServiceRaw.getBitgetFuturesContracts(bitgetFuturesProductType);
      bitgetFuturesContractDtosAll.addAll(bitgetFuturesContractDtos);
    }

    // Initialize all instruments metadata
    Map<Instrument, InstrumentMetaData> instruments =
        bitgetFuturesContractDtosAll.stream()
            .collect(
                Collectors.toMap(
                    BitgetFuturesContractDto::getFuturesContract, BitgetFuturesAdapters::toInstrumentMetaData
                )
            );

  }

  public boolean usingSandbox() {
    return Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
  }

  /**
   * TODO May be part of Exchange Configuration parameters.
   * @return
   */
  public org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType getDefaultProductType(){
    return BitgetFuturesProductType.USDT_FUTURES;
  }

}
