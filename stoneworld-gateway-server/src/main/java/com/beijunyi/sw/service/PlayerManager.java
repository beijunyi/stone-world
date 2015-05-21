package com.beijunyi.sw.service;

import java.util.*;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageBroker;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.GameMessageEnum;
import com.beijunyi.sw.message.gameserver.PlayerToken;

@Named
public class PlayerManager extends InternalMessageHandler {

  private final Map<String, PlayerToken> tokenMap = new HashMap<>();
  private final Set<String> pending = new HashSet<>();

  private final GameServerManager gsManager;

  @Inject
  public PlayerManager(InternalMessageBroker broker, GameServerManager gsManager) {
    super(broker);
    this.gsManager = gsManager;
  }

  public PlayerToken getToken(String key) {
    PlayerToken token;
    synchronized(pending) {
      if(pending.contains(key))
        return null;
      token = tokenMap.get(key);
      if(token == null) {
        if(gsManager.requestPlayerToken(key))
          pending.add(key);
      }
    }
    return token;
  }

  private void updateToken(PlayerToken token) {
    String key = token.getKey();
    synchronized(pending) {
      if(!pending.remove(key))
        throw new IllegalStateException(key);
      tokenMap.put(key, token);
    }
  }

  @Override
  protected void handle(InternalMessage msg, Object src) throws Exception {
    if(GameMessageEnum.PLAYER_TOKEN.equals(msg.getType())) {
      PlayerToken token = (PlayerToken) msg;
      updateToken(token);
    }
  }
}
