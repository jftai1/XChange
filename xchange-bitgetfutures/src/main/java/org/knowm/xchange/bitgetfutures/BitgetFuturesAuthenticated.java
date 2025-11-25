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
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.BitgetResponse;
import org.knowm.xchange.bitget.dto.account.BitgetDepositWithdrawRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetMainSubTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.trade.BitgetCancelOrderParamsDto;
import org.knowm.xchange.bitget.dto.trade.BitgetCancelOrderResponseDto;
import org.knowm.xchange.bitget.dto.trade.BitgetFillDto;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto;
import org.knowm.xchange.bitget.dto.trade.BitgetPlaceOrderDto;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesException;
import org.knowm.xchange.bitgetfutures.dto.BitgetFuturesResponse;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesAccountBalanceDto;
import org.knowm.xchange.bitgetfutures.dto.account.BitgetFuturesSubAccountBalanceDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderDetailDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesOrderInfoDto;
import org.knowm.xchange.bitgetfutures.dto.trade.BitgetFuturesPlaceOrderDto;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetFuturesAuthenticated {

  /**
   * Query all account information under a certain product type.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @param demo
   * @param productType
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/account/accounts")
  BitgetFuturesResponse<List<BitgetFuturesAccountBalanceDto>> balances(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("productType") String productType)
      throws IOException, BitgetFuturesException;

  /**
   * Query the contract asset information of all sub-accounts.
   *
   * @param apiKey
   * @param signer
   * @param passphrase
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BitgetFuturesException
   */
  @GET
  @Path("api/v2/mix/account/sub-account-assets")
  BitgetFuturesResponse<List<BitgetFuturesSubAccountBalanceDto>> subBalances(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("productType") String productType)
      throws IOException, BitgetFuturesException;

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

  @POST
  @Path("api/v2/spot/trade/place-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetFuturesResponse<BitgetFuturesOrderInfoDto> createOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      BitgetFuturesPlaceOrderDto bitgetPlaceOrderDto)
      throws IOException, BitgetFuturesException;

  @POST
  @Path("api/v2/spot/trade/cancel-order")
  @Consumes(MediaType.APPLICATION_JSON)
  BitgetResponse<BitgetCancelOrderResponseDto> cancelOrder(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      BitgetCancelOrderParamsDto bitgetCancelOrderParamsDto)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/trade/fills")
  BitgetResponse<List<BitgetFillDto>> fills(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("symbol") String symbol,
      @QueryParam("limit") Integer limit,
      @QueryParam("orderId") String orderId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/account/transferRecords")
  BitgetResponse<List<BitgetTransferRecordDto>> transferRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("clientOid") String clientOid,
      @QueryParam("fromType") String fromType,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/account/sub-main-trans-record")
  BitgetResponse<List<BitgetMainSubTransferRecordDto>> mainSubTransferRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("clientOid") String clientOid,
      @QueryParam("role") String role,
      @QueryParam("subUid") String subAccountUid,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/wallet/deposit-records")
  BitgetResponse<List<BitgetDepositWithdrawRecordDto>> depositRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("orderId") String orderId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/wallet/subaccount-deposit-records")
  BitgetResponse<List<BitgetDepositWithdrawRecordDto>> subDepositRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("subUid") String subAccountUid,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/wallet/withdrawal-records")
  BitgetResponse<List<BitgetDepositWithdrawRecordDto>> withdrawalRecords(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("coin") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("orderId") String orderId,
      @QueryParam("clientOid") String clientOid,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/trade/unfilled-orders")
  BitgetResponse<List<BitgetOrderInfoDto>> unfilledOrders(
      @HeaderParam("ACCESS-KEY") String apiKey,
      @HeaderParam("ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("paptrading") String demo,
      @QueryParam("symbol") String symbol,
      @QueryParam("limit") Integer limit,
      @QueryParam("requestTime") Long requestTime,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("idLessThan") String idLessThan,
      @QueryParam("orderId") String orderId,
      @QueryParam("tpslType") String tpslType,
      @QueryParam("receiveWindow") Long receiveWindow)
      throws IOException, BitgetException;
}