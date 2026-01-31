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
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto.SymbolType;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesMarginMode;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesAccountService;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesCandlePriceType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesMarketDataService;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesMarketDataServiceRaw;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesTradeService;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesExchange extends BaseExchange {

  public static final String EXCHANGE_SPECIFICATION_KEY_PRODUCT_TYPE = "bitgetfutures.productType";
  public static final String EXCHANGE_SPECIFICATION_KEY_MARGIN_MODE = "bitgetfutures.marginMode";
  public static final String EXCHANGE_SPECIFICATION_KEY_CANDLE_PRICE_TYPE = "bitgetfutures.candlePriceType";


  /**
   * Exchange product types;
   */
  private final List<org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType>exchangeProductTypes = Arrays.asList(
      BitgetFuturesProductType.COIN_FUTURES,
      BitgetFuturesProductType.USDC_FUTURES,
      BitgetFuturesProductType.USDT_FUTURES);

  @Override
  protected void initServices() {
    accountService = new BitgetFuturesAccountService(this);
    marketDataService = new BitgetFuturesMarketDataService(this);
    tradeService = new BitgetFuturesTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://api.bitget.com");
    specification.setHost("www.bitget.com");
    specification.setExchangeName("BitgetFutures");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
    BitgetFuturesMarketDataServiceRaw bitgetMarketDataServiceRaw =
        (BitgetFuturesMarketDataServiceRaw) marketDataService;

    List<BitgetFuturesContractDto> bitgetFuturesContractDtosAll = new ArrayList<BitgetFuturesContractDto>();

    // Get all perpetual contracts
    for (BitgetFuturesProductType bitgetFuturesProductType : exchangeProductTypes) {
      List<BitgetFuturesContractDto> bitgetFuturesContractDtos = bitgetMarketDataServiceRaw.getBitgetFuturesContracts(bitgetFuturesProductType);
      // Keep only perpetual contracts
      bitgetFuturesContractDtosAll.addAll(
        bitgetFuturesContractDtos.stream().filter(
            bitgetFuturesContractDto -> bitgetFuturesContractDto.getSymbolType().equals(SymbolType.PERPETUAL)
        ).collect(Collectors.toList())
      );
    }

    // Initialize symbol mappings
    bitgetFuturesContractDtosAll.forEach(
        bitgetFuturesContractDto -> {
          BitgetFuturesAdapters.putSymbolMapping(bitgetFuturesContractDto.getSymbol(),bitgetFuturesContractDto.getCurrencyPair());
        }
    );

    // Initialize instrument metadata
    Map<Instrument, InstrumentMetaData> instruments =
        bitgetFuturesContractDtosAll.stream()
            .collect(
                Collectors.toMap(
                    BitgetFuturesContractDto::getFuturesContract, BitgetFuturesAdapters::toInstrumentMetaData
                )
            );

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);

  }

  public boolean usingSandbox() {
    return Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
  }

  /**
   * Returns all supported product types.
   * @return
   */
  public List<BitgetFuturesProductType> getExchangeProductTypes() {
    return exchangeProductTypes;
  }

  /**
   * Returns the exchange default product type.
   * @return null if not set
   */
  public BitgetFuturesProductType getDefaultProductType(){
    String value = (String) this.getExchangeSpecification()
        .getExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_PRODUCT_TYPE);
    return BitgetFuturesProductType.fromCode(value);
  }

  /**
   * Sets the exchange default product type.
   *
   * @param productType
   */
  public void setExchangeDefaultProductType(BitgetFuturesProductType productType) {
    this.getExchangeSpecification()
        .setExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_PRODUCT_TYPE,
            productType.getCode());
  }

  /**
   * Returns the exchange default margin mode.
   *
   * @return null if not set
   */
  public BitgetFuturesMarginMode getDefaultMarginMode() {
    String value = (String) this.getExchangeSpecification()
        .getExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_MARGIN_MODE);
    return BitgetFuturesMarginMode.getMode(value);
  }

  /**
   * Sets the exchange default margin mode.
   *
   * @param marginMode
   */
  public void setExchangeDefaultMarginMode(BitgetFuturesMarginMode marginMode) {
    this.getExchangeSpecification()
        .setExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_MARGIN_MODE,
            marginMode.getValue());
  }

  public BitgetFuturesCandlePriceType getDefaultCandlePriceType() {
    String value = (String) this.getExchangeSpecification()
        .getExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_CANDLE_PRICE_TYPE);
    return BitgetFuturesCandlePriceType.getType(value);
  }

  /**
   * Sets the exchange default candle price type.
   *
   * @param candlePriceType
   */
  public void setExchangeDefaultCandlePriceType(BitgetFuturesCandlePriceType candlePriceType) {
    this.getExchangeSpecification()
        .setExchangeSpecificParametersItem(EXCHANGE_SPECIFICATION_KEY_CANDLE_PRICE_TYPE,
            candlePriceType.getValue());
  }

}
