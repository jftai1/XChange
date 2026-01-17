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
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesCancelOrderParamsDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesFillDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderDetailDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderHistoryDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderUpdateInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceOrderDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPositionDto;
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


}