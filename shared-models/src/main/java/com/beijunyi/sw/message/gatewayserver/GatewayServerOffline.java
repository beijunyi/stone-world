package com.beijunyi.sw.message.gatewayserver;

public final class GatewayServerOffline extends GatewayServerMessage {

  private String name;

  public GatewayServerOffline(String name) {
    this();
    this.name = name;
  }

  public GatewayServerOffline() {
    super(GatewayServerMessageEnum.GATEWAY_SERVER_OFFLINE);
  }

  public String getName() {
    return name;
  }
}
