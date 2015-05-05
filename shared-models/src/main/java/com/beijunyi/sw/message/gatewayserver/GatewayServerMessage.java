package com.beijunyi.sw.message.gatewayserver;

import com.beijunyi.sw.message.InternalMessage;

public abstract class GatewayServerMessage implements InternalMessage<GatewayServerMessageEnum> {

  private final GatewayServerMessageEnum type;

  public GatewayServerMessage(GatewayServerMessageEnum type) {
    this.type = type;
  }

  public GatewayServerMessageEnum getType() {
    return type;
  }

}
