package com.beijunyi.sw.message.gatewayserver;

public final class GatewayServerOnline extends GatewayMessage {

  private String name;

  public GatewayServerOnline(String name) {
    this();
    this.name = name;
  }

  public GatewayServerOnline() {
    super(GatewayMessageEnum.GATEWAY_SERVER_ONLINE);
  }

  public String getName() {
    return name;
  }
}
