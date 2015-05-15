package com.beijunyi.sw.service;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.gameserver.PlayerToken;

@Named
public class PlayerManager {

  private final Map<String, PlayerToken> tokenMap = new HashMap<>();

  private final GameServerManager gsManager;

  @Inject
  public PlayerManager(GameServerManager gsManager) {
    this.gsManager = gsManager;
  }


}
