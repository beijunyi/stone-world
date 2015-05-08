package com.beijunyi.sw.message.gatewayserver;

import com.beijunyi.sw.message.InternalMessage;

public abstract class GatewayMessage implements InternalMessage<GatewayMessageEnum> {

  private final GatewayMessageEnum type;

  public GatewayMessage(GatewayMessageEnum type) {
    this.type = type;
  }

  public GatewayMessageEnum getType() {
    return type;
  }

}
