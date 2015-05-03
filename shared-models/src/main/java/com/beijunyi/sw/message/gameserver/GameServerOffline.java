package com.beijunyi.sw.message.gameserver;

public final class GameServerOffline extends GameServerMessage {

  private String name;

  public GameServerOffline(String name) {
    this();
    this.name = name;
  }

  public GameServerOffline() {
    super(GameServerMessageEnum.GAME_SERVER_OFFLINE);
  }

  public String getName() {
    return name;
  }

}
