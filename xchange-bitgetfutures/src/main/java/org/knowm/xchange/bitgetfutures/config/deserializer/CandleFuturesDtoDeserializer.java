package org.knowm.xchange.bitgetfutures.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import org.knowm.xchange.bitgetfutures.dto.marketdata.BitgetFuturesCandleDto;

/**
 * Candle data structure
 */
public class CandleFuturesDtoDeserializer extends JsonDeserializer<BitgetFuturesCandleDto> {

  private static final int TIMESTAMP_INDEX = 0;
  private static final int ENTRY_PRICE_INDEX = 1;
  private static final int HIGHEST_PRICE_INDEX = 2;
  private static final int LOWEST_PRICE_INDEX = 3;
  private static final int EXIT_PRICE_INDEX = 4;
  private static final int TRADING_VOLUME_BASE_CURRENCY = 5;
  private static final int TRADING_VOLUME_QUOTE_CURRENCY = 6;

  @Override
  public BitgetFuturesCandleDto deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    return BitgetFuturesCandleDto.builder()
        .timestamp(Instant.ofEpochMilli(node.get(TIMESTAMP_INDEX).asLong()))
        .entryPrice(new BigDecimal(node.get(ENTRY_PRICE_INDEX).asText()))
        .highestPrice(new BigDecimal(node.get(HIGHEST_PRICE_INDEX).asText()))
        .lowestPrice(new BigDecimal(node.get(LOWEST_PRICE_INDEX).asText()))
        .exitPrice(new BigDecimal(node.get(EXIT_PRICE_INDEX).asText()))
        .tradingVolumneBaseCurrency(new BigDecimal(node.get(TRADING_VOLUME_BASE_CURRENCY).asText()))
        .tradingVolumneQuoteCurrency(
            new BigDecimal(node.get(TRADING_VOLUME_QUOTE_CURRENCY).asText()))
        .build();
  }
}