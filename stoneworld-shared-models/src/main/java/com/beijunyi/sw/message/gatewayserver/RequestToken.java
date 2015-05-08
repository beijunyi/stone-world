package com.beijunyi.sw.message.gatewayserver;

public class RequestToken extends GatewayMessage {

  private String key;

  public RequestToken(String key) {
    this();
    this.key = key;
  }

  public RequestToken() {
    super(GatewayMessageEnum.REQUEST_TOKEN);
  }

  public String getKey() {
    return key;
  }
}
