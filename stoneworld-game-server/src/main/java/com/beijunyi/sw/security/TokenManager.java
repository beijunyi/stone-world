package com.beijunyi.sw.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Named;

@Named
public class TokenManager {

  private final Map<String, String> tokens = new HashMap<>();

  public String requestToken(String key) {
    if(tokens.containsKey(key))
      throw new IllegalArgumentException();
    String token = UUID.randomUUID().toString();
    tokens.put(key, token);
    return token;
  }

  public void destroyToken(String key) {
    tokens.remove(key);
  }

  public boolean checkToken(String key, String token) {
    String expected = tokens.get(key);
    return expected != null && expected.equals(token);
  }

}
