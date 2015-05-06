package com.beijunyi.sw.message.gatewayserver;

public class RequestToken extends GatewayServerMessage {

  private String key;

  public RequestToken(String key) {
    this();
    this.key = key;
  }

  public RequestToken() {
    super(GatewayServerMessageEnum.REQUEST_TOKEN);
  }

  public String getKey() {
    return key;
  }
}
