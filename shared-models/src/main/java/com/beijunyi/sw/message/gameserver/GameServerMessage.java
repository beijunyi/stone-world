package com.beijunyi.sw.message.gameserver;

import com.beijunyi.sw.message.InternalMessage;

public abstract class GameServerMessage implements InternalMessage<GameServerMessageEnum> {

  private final GameServerMessageEnum type;

  public GameServerMessage(GameServerMessageEnum type) {
    this.type = type;
  }

  public GameServerMessageEnum getType() {
    return type;
  }

}
