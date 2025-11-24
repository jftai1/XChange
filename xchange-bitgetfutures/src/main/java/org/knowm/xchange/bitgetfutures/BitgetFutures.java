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
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesTickerDto;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetFutures {

  // Market
  @GET
  @Path("api/v2/mix/market/ticker")
  BitgetFuturesResponse<BitgetFuturesTickerDto> ticker(
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String producType)
      throws IOException, BitgetFuturesException;

  @GET
  @Path("api/v2/mix/market/tickers")
  BitgetFuturesResponse<List<BitgetFuturesTickerDto>> tickers(
      @QueryParam("productType") String producType)
      throws IOException, BitgetFuturesException;


  @GET
  @Path("api/v2/mix/market/candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candles(
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String producType,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("kLineType") String kLineType,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Query all historical K-line data and return a maximum of 200 pieces of data.
   *
   * @param symbol
   * @param producType
   * @param granularity
   * @param startTime
   * @param endTime
   * @param limit
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistory(
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String producType,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Query the historical K-line data of contract index price, and return a maximum of 200 pieces of
   * data.
   *
   * @param symbol
   * @param producType
   * @param granularity
   * @param startTime
   * @param endTime
   * @param limit
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-index-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistoryIndex(
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String producType,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Get historical mark price candle data.
   *
   * @param symbol
   * @param producType
   * @param granularity
   * @param startTime
   * @param endTime
   * @param limit
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/market/history-mark-candles")
  BitgetFuturesResponse<List<BitgetFuturesCandleDto>> candlesHistoryMark(
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String producType,
      @QueryParam("granularity") String granularity,
      @QueryParam("startTime") String startTime,
      @QueryParam("endTime") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

}
