package com.beijunyi.sw.message.gatewayserver;

public final class GatewayServerOffline extends GatewayMessage {

  private String name;

  public GatewayServerOffline(String name) {
    this();
    this.name = name;
  }

  public GatewayServerOffline() {
    super(GatewayMessageEnum.GATEWAY_SERVER_OFFLINE);
  }

  public String getName() {
    return name;
  }
}
