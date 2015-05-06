package com.beijunyi.sw.message.gatewayserver;

public final class GatewayServerOnline extends GatewayServerMessage {

  private String name;

  public GatewayServerOnline(String name) {
    this();
    this.name = name;
  }

  public GatewayServerOnline() {
    super(GatewayServerMessageEnum.GATEWAY_SERVER_ONLINE);
  }

  public String getName() {
    return name;
  }
}
