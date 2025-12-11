package org.knowm.xchange.bitgetfutures.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bitgetfutures.BitgetFuturesAdapters;
import org.knowm.xchange.bitgetfutures.BitgetFuturesExchange;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesCandleDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesServerTime;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesMarketDataServiceRaw extends BitgetFuturesBaseService {

  public BitgetFuturesMarketDataServiceRaw(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public BitgetFuturesServerTime getBitgetServerTime() throws IOException {
    return bitget.serverTime().getData();
  }

  public List<BitgetFuturesContractDto> getBitgetFuturesContracts(
      BitgetFuturesProductType futuresProductType) throws IOException {
    return bitget.futuresContracts(
        null,
        futuresProductType.getCode()).getData();
  }


  public List<BitgetFuturesContractDto> getBitgetFuturesContracts(
      BitgetFuturesProductType futuresProductType, Instrument instrument) throws IOException {
    return bitget.futuresContracts(
        BitgetFuturesAdapters.toSymbolString(instrument),
        futuresProductType.getCode()).getData();
  }

  public BitgetFuturesTickerDto getBitgetTickerDto(BitgetFuturesProductType futuresProductType, Instrument instrument)
      throws IOException {
    return bitget.ticker(
        BitgetFuturesAdapters.toSymbolString(instrument),
        futuresProductType.getCode()).getData();
  }

  public List<BitgetFuturesTickerDto> getBitgetTickerDtos(BitgetFuturesProductType futuresProductType)
      throws IOException {
    return bitget.tickers(futuresProductType.getCode()).getData();
  }

  public List<BitgetFuturesCandleDto> getBitgetRecentCandleDtos(FuturesContract futuresContract,
      BitgetFuturesProductType productType,
      BitgetFuturesCandleStickPeriodType periodType,
      BitgetFuturesCandleChartType chartType,
      Date startTime,
      Date endTime,
      Integer limit
  ) throws IOException {
    limit = (limit != null && limit == 0) ? null : limit;
    return bitget.candles(
        BitgetFuturesAdapters.toSymbolString(futuresContract),
        productType.getCode(),
        periodType.getFieldValue(),
        String.valueOf(startTime.getTime()),
        String.valueOf(endTime.getTime()),
        chartType.toString(),
        limit).getData();
  }

  public List<BitgetFuturesCandleDto> getBitgetCandleHistoryDtos(FuturesContract futuresContract,
      BitgetFuturesProductType productType,
      BitgetFuturesCandleStickPeriodType periodType,
      Date startTime,
      Date endTime,
      Integer limit
  ) throws IOException {
    limit = (limit != null && limit == 0) ? null : limit;
    return bitget.candlesHistory(
        BitgetFuturesAdapters.toSymbolString(futuresContract),
        productType.getCode(),
        periodType.getFieldValue(),
        String.valueOf(startTime.getTime()),
        String.valueOf(endTime.getTime()),
        limit).getData();
  }

}
