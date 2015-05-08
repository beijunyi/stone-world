package com.beijunyi.sw.message.gameserver;

import com.beijunyi.sw.message.InternalMessage;

public abstract class GameMessage implements InternalMessage<GameMessageEnum> {

  private final GameMessageEnum type;

  public GameMessage(GameMessageEnum type) {
    this.type = type;
  }

  public GameMessageEnum getType() {
    return type;
  }

}
