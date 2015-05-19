package com.beijunyi.sw.service;

import java.util.*;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.GameMessageEnum;
import com.beijunyi.sw.message.gameserver.PlayerToken;
import org.jgroups.Address;

@Named
public class PlayerManager implements InternalMessageHandler {

  private final Map<String, PlayerToken> tokenMap = new HashMap<>();
  private final Set<String> pending = new HashSet<>();

  @Inject
  private GameServerManager gsManager;

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
  public void handle(InternalMessage msg, Address src) throws Exception {
    if(GameMessageEnum.PLAYER_TOKEN.equals(msg.getType())) {
      PlayerToken token = (PlayerToken) msg;
      updateToken(token);
    }
  }
}
