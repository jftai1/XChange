package org.knowm.xchange.bitgetfutures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto;
import org.knowm.xchange.bitget.service.BitgetAccountService;
import org.knowm.xchange.bitget.service.BitgetMarketDataService;
import org.knowm.xchange.bitget.service.BitgetMarketDataServiceRaw;
import org.knowm.xchange.bitget.service.BitgetTradeService;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesMarketDataServiceRaw;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesExchange extends BaseExchange {

  /**
   * Exchange product types;
   */
  private List<BitgetFuturesProductType>exchangeProductTypes = Arrays.asList(
      BitgetFuturesProductType.COIN_FUTURES,
      BitgetFuturesProductType.USDC_FUTURES,
      BitgetFuturesProductType.USDT_FUTURES);

  @Override
  protected void initServices() {
//    accountService = new BitgetAccountService(this);
      marketDataService = new BitgetMarketDataService(this);
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
    for (BitgetFuturesProductType bitgetFuturesProductType : exchangeProductTypes) {
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
  public BitgetFuturesProductType getDefaultProductType(){
    return BitgetFuturesProductType.USDT_FUTURES;
  }

}
