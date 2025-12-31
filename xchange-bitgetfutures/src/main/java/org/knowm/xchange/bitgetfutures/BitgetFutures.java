package org.knowm.xchange.bitgetfutures;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesResponse;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesCandleDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesContractDto;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesServerTime;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetFutures {

  /**
   * Getting server time,Unix millisecond timestamp
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/public/time")
  BitgetFuturesResponse<BitgetFuturesServerTime> serverTime() throws IOException, BitgetFuturesException;

  /**
   * Interface is used to get future contract details.
   *
   * @param producType Product type (required)
   * @param symbol     Trading pair, based on the symbolName, i.e. BTCUSDT (optional)
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/contracts")
  BitgetFuturesResponse<List<BitgetFuturesContractDto>> futuresContracts(
      @QueryParam("productType") String producType,
      @QueryParam("symbol") String symbol)
      throws IOException, BitgetFuturesException;

  /**
   * Get ticker data of the given 'productType' and 'symbol'
   *
   * @param producType required
   * @param symbol     required
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/ticker")
  BitgetFuturesResponse<List<BitgetFuturesTickerDto>> ticker(
      @QueryParam("productType") String producType,
      @QueryParam("symbol") String symbol)
      throws IOException, BitgetFuturesException;

  /**
   * Get all ticker data of the given 'productType'
   * @param producType required
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/tickers")
  BitgetFuturesResponse<List<BitgetFuturesTickerDto>> tickers(
      @QueryParam("productType") String producType)
      throws IOException, BitgetFuturesException;

  /**
   * Get Candlestick data. By default, 100 records are returned. If there is no data, an empty array
   * is returned. The queryable data history varies depending on the k-line granularity.
   * <p>
   * The rules are as follows: 1m, 3m, and 5m can be checked for up to one month; 15m can be checked
   * for up to 52 days; 30m can be searched for up to 62 days; 1H can be checked for up to 83 days;
   * 2H can be checked for up to 120 days; 4H can be checked for up to 240 days; 6H can be checked
   * for up to 360 days
   *
   * @param producType  required
   * @param symbol      required
   * @param granularity required
   * @param startTime   optional
   * @param endTime     optional
   * @param kLineType   optional
   * @param limit       optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candles(
      @QueryParam("productType") String producType,
      @QueryParam("symbol") String symbol,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("kLineType") String kLineType,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Query all historical K-line data and return a maximum of 200 pieces of data.
   *
   * @param producType  required
   * @param symbol      required
   * @param granularity required
   * @param startTime   optional
   * @param endTime     optional
   * @param limit       optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistory(
      @QueryParam("productType") String producType,
      @QueryParam("symbol") String symbol,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Query the historical K-line data of contract index price, and return a maximum of 200 pieces of
   * data.
   *
   * @param producType  required
   * @param symbol      required
   * @param granularity required
   * @param startTime   optional
   * @param endTime     optional
   * @param limit       optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-index-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistoryIndex(
      @QueryParam("productType") String producType,
      @QueryParam("symbol") String symbol,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Get historical mark price candle data.
   *
   * @param producType  required
   * @param symbol      required
   * @param granularity required
   * @param startTime   optional
   * @param endTime     optional
   * @param limit       optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-mark-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistoryMark(
      @QueryParam("productType") String producType, @QueryParam("symbol") String symbol,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

}
