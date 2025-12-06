package org.knowm.xchange.bitgetfutures.service;


public enum BitgetFuturesCandleChartType {
  MARKET("MARKET"),
  MARK("MARK"),
  INDEX("INDEX");

  private final String value;

  BitgetFuturesCandleChartType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
