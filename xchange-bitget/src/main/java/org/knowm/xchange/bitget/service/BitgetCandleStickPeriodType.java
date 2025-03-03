package org.knowm.xchange.bitget.service;

import lombok.Getter;

@Getter
public enum BitgetCandleStickPeriodType {
  CANDLE_STICK_1M(1, "1min"),
  CANDLE_STICK_5M(5, "5min"),
  CANDLE_STICK_15M(15, "15min"),
  CANDLE_STICK_30M(30, "30min"),
  CANDLE_STICK_1H(60, "1h"),
  CANDLE_STICK_2H(2 * 60, "2h"),
  CANDLE_STICK_4H(4 * 60, "4h");

  /* Period in seconds  */
  private final long periodInSeconds;
  /* Exchange related value */
  private final String fieldValue;

  BitgetCandleStickPeriodType(long periodInMinutes, String fieldValue) {
    this.periodInSeconds = periodInMinutes * 60;
    this.fieldValue = fieldValue;
  }

  public static BitgetCandleStickPeriodType getPeriodTypeFromSeconds(long periodInSeconds) {
    BitgetCandleStickPeriodType result = null;
    for (BitgetCandleStickPeriodType period : BitgetCandleStickPeriodType.values()) {
      if (period.periodInSeconds == periodInSeconds) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSeconds() {
    long[] result = new long[BitgetCandleStickPeriodType.values().length];
    int index = 0;
    for (BitgetCandleStickPeriodType period : BitgetCandleStickPeriodType.values()) {
      result[index++] = period.periodInSeconds;
    }
    return result;
  }

}
