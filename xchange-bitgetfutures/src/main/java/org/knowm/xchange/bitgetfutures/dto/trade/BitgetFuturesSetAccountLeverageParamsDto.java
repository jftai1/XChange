package org.knowm.xchange.bitgetfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetFuturesSetAccountLeverageParamsDto {

  /**
   * 	Product type
   * USDT-FUTURES USDT-M Futures
   * COIN-FUTURES Coin-M Futures
   * USDC-FUTURES USDC-M Futures
   */
  @JsonProperty("productType")
  private String productType;

  /**
   * Trading pair. e.g. BTCUSDT
   */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * Margin coin, must be capitalized
   */
  @JsonProperty("marginCoin")
  private String marginCoin;

  /**
   * Leverage ratio
   * - Applicable to cross-margin mode
   * - Applicable to one-way position scenarios in isolated margin mode
   * - Applicable to scenarios where the same leverage ratio is set for different
   * directions under hedge-mode in isolated margin mode
   */
  @JsonProperty("leverage")
  private BigDecimal leverage;

  /**
   * Long position leverage
   * - Only applicable to scenarios where different leverage ratios are set for different
   * directions under hedge-mode in isolated margin mode
   * - In two-way position scenarios, if both leverage and longLeverage parameters are passed,
   * longLeverage will take effect and leverage will be ignored
   */
  @JsonProperty("longLeverage")
  private BigDecimal longLeverage;

  /**
   * 	Short position leverage
   * - Only applicable to scenarios where different leverage ratios are set
   * for different directions under hedge-mode in isolated margin mode
   * - In two-way position scenarios, if both leverage and shortLeverage parameters are passed,
   * shortLeverage will take effect and leverage will be ignored
   */
  @JsonProperty("shortLeverage")
  private BigDecimal shortLeverage;

  /**
   * Position direction
   * long： Long position；
   * short：Short position
   * Cross-margin mode: The holdSide parameter does not need to be filled in
   * Isolated margin mode: For one-way positions,the holdSide parameter does not need to be filled in;
   * for hedge-mode, the holdSide parameter must be filled in.
   * And when long and short leverages are set simultaneously for hedge-mode, holdSide is not required
   */
  @JsonProperty("holdSide")
  private BitgetFuturesPositionSide positionSide;


}
