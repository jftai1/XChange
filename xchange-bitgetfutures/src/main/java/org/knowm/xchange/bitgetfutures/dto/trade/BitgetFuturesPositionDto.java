package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitgetfutures.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitgetfutures.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitgetfutures.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;

/**
 * Information about a current position.
 */
@Data
@Builder
@Jacksonized
public class BitgetFuturesPositionDto {

    /**
     * Trading pair
     */
    @JsonProperty("symbol")
    private String symbol;

    /**
     * Margin coin
     */
    @JsonProperty("marginCoin")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency marginCurrency;

    /**
     * Position direction
     * long: long position
     * short: short position
     */
    @JsonProperty("holdSide")
    private BitgetFuturesPositionSide positionSide;

    /**
     * Amount to be filled of the current order (base coin)
     */
    @JsonProperty("openDelegateSize")
    private BigDecimal openDelegateSize;

    /**
     * Margin amount (margin coin)
     */
    @JsonProperty("marginSize")
    private BigDecimal marginSize;

    /**
     * Available amount for positions (base currency)
     */
    @JsonProperty("available")
    private BigDecimal available;

    /**
     * Frozen amount in the position (base currency)
     */
    @JsonProperty("locked")
    private BigDecimal locked;

    /**
     * Total amount of all positions (available amount + locked amount)
     */
    @JsonProperty("total")
    private BigDecimal total;

    /**
     * Leverage
     */
    @JsonProperty("leverage")
    private BigDecimal leverage;

    /**
     * Realized PnL(exclude the funding fee and transaction fee)
     */
    @JsonProperty("achievedProfits")
    private BigDecimal achievedProfitAndLoss;

    /**
     * Average entry price
     */
    @JsonProperty("openPriceAvg")
    private BigDecimal openPriceAvg;

    /**
     * Margin mode
     * isolated: isolated margin
     * crossed: cross margin
     */
    @JsonProperty("marginMode")
    private BitgetFuturesMarginMode marginMode;

    /**
     * Position mode
     * one_way_mode positions in one-way mode
     * hedge_mode positions in hedge-mode
     */
    @JsonProperty("posMode")
    private BitgetFuturesPositionMode positionMode;

    /**
     * Unrealized PnL (Profit and loss of the current position)
     */
    @JsonProperty("unrealizedPL")
    private BigDecimal unrealizedProfitAndLoss;

    /**
     * Estimated liquidation price
     * If the value <= 0, it means the position is at low risk and there is no liquidation price at this time
     */
    @JsonProperty("liquidationPrice")
    private BigDecimal liquidationPrice;

    /**
     * Tiered maintenance margin rate
     */
    @JsonProperty("keepMarginRate")
    private BigDecimal keepMarginRate;

    /**
     * Mark price
     */
    @JsonProperty("markPrice")
    private BigDecimal markPrice;

    /**
     * Maintenance margin rate (MMR), 0.1 represents 10%
     */
    @JsonProperty("marginRatio")
    private BigDecimal marginRatio;

    /**
     * Position breakeven price
     */
    @JsonProperty("breakEvenPrice")
    private BigDecimal breakEvenPrice;

    /**
     * Funding fee, the accumulated value of funding fee during the position,
     * The initial value is empty, indicating that no funding fee has been charged yet.
     */
    @JsonProperty("totalFee")
    private BigDecimal totalFee;

    /**
     * Take profit price
     */
    @JsonProperty("takeProfit")
    private BigDecimal takeProfitPrice;

    /**
     * Stop loss price
     */
    @JsonProperty("stopLoss")
    private BigDecimal stopLossPrice;

    /**
     * Take profit order ID
     */
    @JsonProperty("takeProfitId")
    private String takeProfitOrderId;

    /**
     * Stop loss order ID
     */
    @JsonProperty("stopLossId")
    private String stopLossOrderId;

    /**
     * Deducted transaction fees: transaction fees deducted during the position
     */
    @JsonProperty("deductedFee")
    private BigDecimal deductedFee;

    /**
     * Creation time
     */
    @JsonProperty("cTime")
    private Instant createdAt;

    /**
     * Last updated time
     */
    @JsonProperty("uTime")
    private Instant updatedAt;

    /**
     * single : single asset mode
     * union multi-Assets mode
     */
    @JsonProperty("assetMode")
    private BitgetFuturesAssetMode assetMode;

    /**
     * Futures Airdrop Voucher Amount
     */
    @JsonProperty("grant")
    private BigDecimal grant;

}
