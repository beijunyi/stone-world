package com.beijunyi.sw.message.gameserver;

public final class GameServerOffline extends GameMessage {

  private String name;

  public GameServerOffline(String name) {
    this();
    this.name = name;
  }

  public GameServerOffline() {
    super(GameMessageEnum.GAME_SERVER_OFFLINE);
  }

  public String getName() {
    return name;
  }

}
