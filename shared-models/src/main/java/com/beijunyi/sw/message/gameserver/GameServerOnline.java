package com.beijunyi.sw.message.gameserver;

public final class GameServerOnline extends GameServerMessage {

  private String name;
  private String ip;
  private int port;

  public GameServerOnline(String name, String ip, int port) {
    this();
    this.name = name;
    this.ip = ip;
    this.port = port;
  }

  public GameServerOnline() {
    super(GameServerMessageEnum.GAME_SERVER_ONLINE);
  }

  public String getName() {
    return name;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

}
