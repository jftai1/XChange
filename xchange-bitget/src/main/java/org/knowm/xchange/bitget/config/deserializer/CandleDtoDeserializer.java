package org.knowm.xchange.bitget.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import org.knowm.xchange.bitget.dto.marketdata.BitgetCandleDto;

/**
 * Candle data structure
 */
public class CandleDtoDeserializer extends JsonDeserializer<BitgetCandleDto> {

  private static final int TIMESTAMP_INDEX = 0;
  private static final int OPENING_PRICE_INDEX = 1;
  private static final int HIGHEST_PRICE_INDEX = 2;
  private static final int LOWEST_PRICE_INDEX = 3;
  private static final int CLOSING_PRICE_INDEX = 4;
  private static final int TRADING_VOLUME_BASE_CURRENCY = 5;
  private static final int TRADING_VOLUME_USDT = 6;
  private static final int TRADING_VOLUME_QUOTE_CURRENCY = 7;

  @Override
  public BitgetCandleDto deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    return BitgetCandleDto.builder()
        .timestamp(Instant.ofEpochMilli(node.get(TIMESTAMP_INDEX).asLong()))
        .openingPrice(new BigDecimal(node.get(OPENING_PRICE_INDEX).asText()))
        .highestPrice(new BigDecimal(node.get(HIGHEST_PRICE_INDEX).asText()))
        .lowestPrice(new BigDecimal(node.get(LOWEST_PRICE_INDEX).asText()))
        .closingPrice(new BigDecimal(node.get(CLOSING_PRICE_INDEX).asText()))
        .tradingVolumneBaseCurrency(new BigDecimal(node.get(TRADING_VOLUME_BASE_CURRENCY).asText()))
        .tradingVolumneUSDT(new BigDecimal(node.get(TRADING_VOLUME_USDT).asText()))
        .tradingVolumneQuoteCurrency(
            new BigDecimal(node.get(TRADING_VOLUME_QUOTE_CURRENCY).asText()))
        .build();
  }
}
