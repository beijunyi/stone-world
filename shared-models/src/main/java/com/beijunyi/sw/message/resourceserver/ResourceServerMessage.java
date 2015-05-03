package com.beijunyi.sw.message.resourceserver;

import com.beijunyi.sw.message.InternalMessage;

public abstract class ResourceServerMessage implements InternalMessage<ResourceServerMessageEnum> {

  private final ResourceServerMessageEnum type;

  public ResourceServerMessage(ResourceServerMessageEnum type) {
    this.type = type;
  }

  public ResourceServerMessageEnum getType() {
    return type;
  }

}
