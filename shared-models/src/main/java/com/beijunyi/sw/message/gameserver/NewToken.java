package com.beijunyi.sw.message.gameserver;

public class NewToken extends GameServerMessage {

  private String key;
  private String token;

  public NewToken(String key, String token) {
    this();
    this.key = key;
    this.token = token;
  }

  public NewToken() {
    super(GameServerMessageEnum.NEW_TOKEN);
  }

  public String getKey() {
    return key;
  }

  public String getToken() {
    return token;
  }
}