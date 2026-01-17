package org.knowm.xchange.bitgetfutures.service.params;

import java.util.Date;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bitgetfutures.service.BitgetFuturesProductType;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamClientOid;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;

/**
 * Order history parameters. Order ID : If both orderId and clientOid are entered, orderId prevails.
 * Client OID : If both orderId and clientOid are entered, orderId prevails. Product type is
 * required. USDT-FUTURES USDT-M Futures COIN-FUTURES Coin-M Futures USDC-FUTURES USDC-M Futures
 */
@Getter
@Setter
public class BitgetFuturesQueryOrderHistoryParams extends DefaultQueryOrderParamInstrument
    implements TradeHistoryParamInstrument,
    TradeHistoryParamOrderId,
    TradeHistoryParamClientOid,
    TradeHistoryParamLimit,
    TradeHistoryParamsTimeSpan,
    TradeHistoryParamsIdSpan {

  private String clientOid;

  private BitgetFuturesProductType productType;

  private String startId;

  private String endId;

  private Date startTime;

  private Date endTime;

  private Integer limit;

  @Builder
  public BitgetFuturesQueryOrderHistoryParams(BitgetFuturesProductType productType,
      Instrument instrument,
      String orderId,
      String clientOid,
      String startId,
      String endId,
      Date startTime,
      Date endTime,
      Integer limit) {
    super(instrument, orderId);
    Objects.requireNonNull(productType, "Product type is required");
    this.productType = productType;
    this.clientOid = clientOid;
    this.startId = startId;
    this.endId = endId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.limit = limit;
  }

  public BitgetFuturesQueryOrderHistoryParams(BitgetFuturesProductType productType) {
    this(productType, null, null, null, null, null, null, null, null);
  }

  public BitgetFuturesQueryOrderHistoryParams(BitgetFuturesProductType productType,
      Instrument instrument) {
    this(productType, instrument, null, null, null, null, null, null, null);
  }

  public BitgetFuturesQueryOrderHistoryParams(BitgetFuturesProductType productType,
      Instrument instrument, String orderId) {
    this(productType, instrument, orderId, null, null, null, null, null, null);
  }

  public BitgetFuturesQueryOrderHistoryParams(BitgetFuturesProductType productType,
      Instrument instrument, Date startTime, Date endTime) {
    this(productType, instrument, null, null, null, null, startTime, endTime, null);
  }
}
