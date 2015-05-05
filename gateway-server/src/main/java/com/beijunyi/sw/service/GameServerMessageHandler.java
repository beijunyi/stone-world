package com.beijunyi.sw.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.gameserver.GameServerMessage;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import org.jgroups.Address;

@Named
public class GameServerMessageHandler {

  private final GameServerManager gsManager;

  @Inject
  public GameServerMessageHandler(GameServerManager gsManager) {
    this.gsManager = gsManager;
  }

  public void handle(GameServerMessage message, Address src) {
    switch(message.getType()) {
      case GAME_SERVER_ONLINE:
        GameServerOnline gsOnline = (GameServerOnline) message;
        gsManager.addGameServer(gsOnline.getName(), gsOnline.getIp(), gsOnline.getPort(), src);
        break;
      case GAME_SERVER_OFFLINE:
        GameServerOffline gsOffline = (GameServerOffline) message;
        gsManager.removeGameServer(gsOffline.getName());
        break;
    }
  }



}
