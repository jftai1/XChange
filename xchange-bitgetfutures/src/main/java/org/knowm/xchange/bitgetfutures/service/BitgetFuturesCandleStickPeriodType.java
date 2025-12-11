package org.knowm.xchange.bitgetfutures.service;

import lombok.Getter;

@Getter
public enum BitgetFuturesCandleStickPeriodType {
  CANDLE_STICK_1M(1, "1m"),
  CANDLE_STICK_3M(3, "3m"),
  CANDLE_STICK_5M(5, "5m"),
  CANDLE_STICK_15M(15, "15m"),
  CANDLE_STICK_30M(30, "30m"),
  CANDLE_STICK_1H(60, "1H"),
  CANDLE_STICK_2H(2 * 60, "2H"),
  CANDLE_STICK_4H(4 * 60, "4H"),
  CANDLE_STICK_6H(6 * 60, "6H");

  /* Period in seconds  */
  private final long periodInSeconds;
  /* Exchange related value */
  private final String fieldValue;

  BitgetFuturesCandleStickPeriodType(long periodInMinutes, String fieldValue) {
    this.periodInSeconds = periodInMinutes * 60;
    this.fieldValue = fieldValue;
  }

  public static BitgetFuturesCandleStickPeriodType getPeriodTypeFromSeconds(long periodInSeconds) {
    BitgetFuturesCandleStickPeriodType result = null;
    for (BitgetFuturesCandleStickPeriodType period : BitgetFuturesCandleStickPeriodType.values()) {
      if (period.periodInSeconds == periodInSeconds) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSeconds() {
    long[] result = new long[BitgetFuturesCandleStickPeriodType.values().length];
    int index = 0;
    for (BitgetFuturesCandleStickPeriodType period : BitgetFuturesCandleStickPeriodType.values()) {
      result[index++] = period.periodInSeconds;
    }
    return result;
  }

}
