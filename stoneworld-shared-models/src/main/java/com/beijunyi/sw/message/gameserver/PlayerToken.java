package com.beijunyi.sw.message.gameserver;

public class PlayerToken extends GameMessage {

  private String key;
  private String ip;
  private int port;
  private String token;

  public PlayerToken(String key, String ip, int port, String token) {
    this();
    this.key = key;
    this.ip = ip;
    this.port = port;
    this.token = token;
  }

  public PlayerToken() {
    super(GameMessageEnum.PLAYER_TOKEN);
  }

  public String getKey() {
    return key;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public String getToken() {
    return token;
  }
}
