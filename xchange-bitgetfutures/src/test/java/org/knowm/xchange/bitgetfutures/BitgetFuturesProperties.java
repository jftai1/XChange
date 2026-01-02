package org.knowm.xchange.bitgetfutures;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BitgetFuturesProperties {
  private String apiKey;
  private String secretKey;
  private String passphrase;

  public BitgetFuturesProperties() throws IOException {
    Properties properties = new Properties();
    // InputStream input = getClass().getResourceAsStream("integration-test.env.properties")
    try (InputStream input = new FileInputStream("integration-test.env.properties")) {
      properties.load(input);
      this.apiKey = properties.getProperty("apiKey");
      this.secretKey = properties.getProperty("secretKey");
      this.passphrase = properties.getProperty("passphrase");
    } catch (IOException ignored) {
      // Properties remain null if file is missing
    }
  }

  public boolean isValid() {
    return apiKey != null && secretKey != null && passphrase != null;
  }

  public String getApiKey() { return apiKey; }
  public String getSecretKey() { return secretKey; }
  public String getPassphrase() { return passphrase; }
}
