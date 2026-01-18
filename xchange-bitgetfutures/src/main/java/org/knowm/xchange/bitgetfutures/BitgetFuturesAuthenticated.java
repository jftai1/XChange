package org.knowm.xchange.bitgetfutures;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesResponse;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceDetailDto;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesCancelTakeProfitStopLossOrderResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesClosePositionsResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountLeverageResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountMarginModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFururesSetAccountPositionModeResponseDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesCancelOrderParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesCancelTakeProfitStopLossOrderParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesClosePositionsParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderDetailDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderUpdateInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceOrderDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountLeverageParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountMarginModeParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesSetAccountPositionModeParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceTakeProfitStopLossOrderParamsDto;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetFuturesAuthenticated {

  /**
   * Query all account information under a certain product type.
   *
   * @param apiKey required
   * @param signer required
   * @param passphrase required
   * @param timestamp required
   * @param demo optional 1 for demo
   * @param productType required
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/account/accounts")
  BitgetFuturesResponse<List<BitgetFuturesAccountBalanceInfoDto>> balances(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("productType") String productType)
      throws IOException, BitgetFuturesException;

  /**
   * Retrieves the account balance details for a specified product or symbol in the Bitget Futures platform.
   *
   * @param apiKey        The API key used for authentication.
   * @param signer        The signature created for securing the API request.
   * @param passphrase    The API passphrase associated with the API key.
   * @param timestamp     The synchronized timestamp for request validation.
   * @param demo          Indicates whether the trading is in demo or live mode. (optional 1 for demo)
   * @param productType   The type of product for which the account balance details are being requested.
   * @param symbol        The symbol of the specific trading pair (optional).
   * @param marginCoin    The margin coin associated with the account balance (optional).
   * @return A response containing the account balance details.
   * @throws IOException             If a network-related error occurs during the API request.
   * @throws BitgetFuturesException  If an error related to Bitget Futures occurs.
   */
  @GET
  @Path("api/v2/mix/account/account")
  BitgetFuturesResponse<BitgetFuturesAccountBalanceDetailDto> balance(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("productType") String productType,
      @QueryParam("symbol") String symbol,
      @QueryParam("marginCoin") String marginCoin)
      throws IOException, BitgetFuturesException;


  /**
   * Adjust the position mode between 'one way mode' and 'hedge mode'
   *
   * If you want to change the user's position mode on all symbol contracts,
   * you need to specify hedge mode positions or one-way positions.
   *
   * Note: The position mode can't be adjusted when there is an open position order under the product type.
   * Changes the user's position mode for all symbol futures: hedging mode or one-way mode.
   * When users hold positions or orders on any side of any trading pair in the specific product type,
   * the request may fail.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param bitgetFuturesSetAccountPositionModeParamsDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/account/set-position-mode")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFururesSetAccountPositionModeResponseDto> setAccountPositionMode(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesSetAccountPositionModeParamsDto bitgetFuturesSetAccountPositionModeParamsDto
  ) throws IOException, BitgetFuturesException;

  /**
   * This interface cannot be used when the users have an open position or an order.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param bitgetFuturesSetAccountMarginModeParamsDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/account/set-margin-mode")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFururesSetAccountMarginModeResponseDto> setAccountMarginMode(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesSetAccountMarginModeParamsDto bitgetFuturesSetAccountMarginModeParamsDto
  ) throws IOException, BitgetFuturesException;

  /**
   * Adjust the leverage on the given symbol and productType
   *
   * Note: When adjusting leverage in cross margin mode, please use the leverage parameter
   * instead of longLeverage or shortLeverage. Currently, there is no mandatory validation
   * for longLeverage and shortLeverage. If these two parameters are passed in cross margin mode,
   * they will still take effect, with longLeverage taking priority.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param bitgetFuturesSetAccountLeverageParamsDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/account/set-leverage")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFururesSetAccountLeverageResponseDto> setAccountLeverage(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesSetAccountLeverageParamsDto bitgetFuturesSetAccountLeverageParamsDto
  ) throws IOException, BitgetFuturesException;

  /**
   * Get order detail
   * @param apiKey required
   * @param signer required
   * @param passphrase required
   * @param timestamp required
   * @param demo optional 1 for demo
   * @param symbol required
   * @param productType required
   * @param orderId optional (orderId or clientOid)
   * @param clientOid optional (orderId or clientOid)
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/order/detail")
  BitgetFuturesResponse<List<BitgetFuturesOrderDetailDto>> orderDetail(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String productType,
      @QueryParam("orderId") String orderId,
      @QueryParam("clientOid") String clientOid)
      throws IOException, BitgetFuturesException;

  /**
   * Get order fill details
   * @param apiKey required
   * @param signer required
   * @param passphrase required
   * @param timestamp required
   * @param demo optional 1 for demo
   * @param orderId optional
   * @param symbol optional
   * @param productType required
   * @param idLessThan optional
   * @param startTime optional
   * @param endTime optional
   * @param limit optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/order/fills")
  BitgetFuturesResponse<BitgetFuturesFillDto> fills(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("orderId") String orderId,
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String productType,
      @QueryParam("idLessThan") String idLessThan,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Ignore the tradeSide parameter when position mode is in one-way-mode
   * <p>
   * In “hedge-mode”, when there is limit close order occupying the position, if the size of next
   * market close order and limit close orders exceeds the position size, it will return an
   * “insufficient position error” instead of cancelling the current limit order and executing the
   * market order
   * <p>
   * hedge position mode: Open long: "side"=buy, "tradeSide"=open; Close long: "side"=buy,
   * "tradeSide"=close; Open short: "side"=sell, "tradeSide"=open; Close short: "side"=sell,
   * "tradeSide"=close; one-way position mode: "side"=buy and sell, tradeSide: ignore
   * <p>
   * In one-way-mode position mode, if the total size of the new reduce-only order and the existing
   * reduce-only orders exceeds the position size, the system will cancel the existing reduce-only
   * orders sequentially based on their creation order until the total size of the new and existing
   * reduce-only orders is less than or equal to the position size. Additionally, the response for
   * the latest reduce-only order request will not include an orderId. You can use the clientOid set
   * in the request to query order details or retrieve the orderId from the current pending orders.
   * <p>
   * When in hedge Mode, if a limit close order is occupying a position, and a subsequent market
   * close order (its quantity plus the limit order's quantity) exceeds the total position size, it
   * will not report an insufficient position error. It also won't cancel the limit order that's
   * occupying the position. Instead, the quantity of the limit close order will be preserved, and
   * the market order will close only the quantity remaining after subtracting the limit order's
   * quantity from the total position size. For example: If you have a position of 100, a limit
   * order occupies 70, and you then place a market close order for 50, it will not report an
   * insufficient position error, nor will it cancel the occupying limit order to execute the market
   * order. Instead, it will directly close a quantity of 30.
   * <p>
   * When in hedge Mode,if the existing quantity is equal to the limit close position order of the
   * held position, a newly added close position order will automatically cancel the limit order
   * that has occupied the position.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param bitgetPlaceOrderDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/order/place-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFuturesOrderUpdateInfoDto> createOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesPlaceOrderDto bitgetPlaceOrderDto)
      throws IOException, BitgetFuturesException;

  /**
   * Cancel a pending order
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param bitgetCancelOrderParamsDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/order/cancel-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFuturesCancelOrderParamsDto> cancelOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      BitgetFuturesCancelOrderParamsDto bitgetCancelOrderParamsDto)
      throws IOException, BitgetFuturesException;

  @POST
  @Path("api/v2/mix/order/place-tpsl-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFuturesOrderUpdateInfoDto> createTakeProfitStopLossOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesPlaceTakeProfitStopLossOrderParamsDto bitgetFuturesPlaceTakeProfitStopLossOrderParamsDto)
      throws IOException, BitgetFuturesException;

  @POST
  @Path("api/v2/mix/order/cancel-plan-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFururesCancelTakeProfitStopLossOrderResponseDto> cancelTakeProfitStopLossOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesCancelTakeProfitStopLossOrderParamsDto bitgetFuturesCancelTakeProfitStopLossOrderParamsDto)
      throws IOException, BitgetFuturesException;

  @GET
  @Path("api/v2/mix/order/orders-pending")
  BitgetFuturesResponse<List<BitgetFuturesOrderUpdateInfoDto>> pendingOrders(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("orderId") String orderId,
      @QueryParam("clientOid") String clientOid,
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String productType,
      @QueryParam("status") String status,
      @QueryParam("idLessThan") String idLessThan,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Get history order(It only supports to get the data within 90days. The older data can be downloaded from web)
   *
   * @param apiKey required
   * @param signer required
   * @param passphrase required
   * @param timestamp required
   * @param demo optional 1 for demo
   * @param orderId optional (orderId or clientOid If both orderId and clientOid are entered, orderId prevails.)
   * @param clientOid optional (orderId or clientOid If both orderId and clientOid are entered, orderId prevails.)
   * @param symbol optional
   * @param productType required
   * @param idLessThan optional
   * @param orderSource optional
   * @param startTime optional
   * @param endTime optional
   * @param limit optional
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/order/orders-history")
  BitgetFuturesResponse<BitgetFuturesOrderHistoryDto> orderHistory(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("orderId") String orderId,
      @QueryParam("clientOid") String clientOid,
      @QueryParam("symbol") String symbol,
      @QueryParam("productType") String productType,
      @QueryParam("idLessThan") String idLessThan,
      @QueryParam("orderSource") String orderSource,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BitgetFuturesException;

  /**
   * Returns information about all current positions with the given productType
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param productType
   * @param marginCoin
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/position/all-position")
  BitgetFuturesResponse<List<BitgetFuturesPositionDto>> positions(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("productType") String productType,
      @QueryParam("marginCoin") String marginCoin
  ) throws IOException, BitgetFuturesException;

  /**
   * Close position at market price
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param bitgetFuturesClosePositionsParamsDto
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @POST
  @Path("api/v2/mix/order/close-positions")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFururesClosePositionsResponseDto> closePositions(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesClosePositionsParamsDto bitgetFuturesClosePositionsParamsDto
  ) throws IOException, BitgetFuturesException;

}