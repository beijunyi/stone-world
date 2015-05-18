package com.beijunyi.sw.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.gameserver.*;
import org.jgroups.Address;

@Named
public class GatewayMessageHandler {

  @Inject
  private GameServerManager gsManager;
  @Inject
  private PlayerManager playerManager;

  public void handle(GameMessage message, Address src) {
    switch(message.getType()) {
      case GAME_SERVER_ONLINE:
        GameServerOnline gsOnline = (GameServerOnline) message;
        gsManager.addGameServer(gsOnline.getName(), gsOnline.getIp(), gsOnline.getPort(), src);
        break;
      case GAME_SERVER_OFFLINE:
        GameServerOffline gsOffline = (GameServerOffline) message;
        gsManager.removeGameServer(gsOffline.getName());
        break;
      case PLAYER_TOKEN:
        PlayerToken token = (PlayerToken) message;
        playerManager.updateToken(token);
        break;
    }
  }



}
