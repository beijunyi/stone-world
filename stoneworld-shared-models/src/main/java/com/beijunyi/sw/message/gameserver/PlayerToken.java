package com.beijunyi.sw.message.gameserver;

public class PlayerToken extends GameMessage {

  private String key;
  private String url;
  private String uid;

  public PlayerToken(String key, String url, String uid) {
    this();
    this.key = key;
    this.url = url;
    this.uid = uid;
  }

  public PlayerToken() {
    super(GameMessageEnum.PLAYER_TOKEN);
  }

  public String getKey() {
    return key;
  }

  public String getUrl() {
    return url;
  }

  public String getUid() {
    return uid;
  }
}
